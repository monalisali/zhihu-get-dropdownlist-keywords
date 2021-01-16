package com.github.monalisali.zhihu.getkeywords;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.monalisali.dao.Dao;
import com.github.monalisali.entity.HotWord;
import com.github.monalisali.entity.TopCategory;
import com.github.monalisali.utils.DatabaseHelp;
import com.github.monalisali.utils.FileHelper;
import com.github.monalisali.utils.QueryDto;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Search {
    private static final String _apiPrefix = "https://www.zhihu.com/api/v4/search/suggest?q=";
    private static Dao dao = new Dao(DatabaseHelp.getSqlSessionFactory());
    public Search(String param) {
        searchParam = param;
        parameterAtoZ = addAtoZ();
    }

    private String searchParam;

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        searchParam = searchParam;
    }


    //把searchParam末尾依次加上字母（A-Z），形成一个新的参数列表
    private List<String> parameterAtoZ;

    public List<String> getParameterAtoZ() {
        return parameterAtoZ;
    }

    public void getDropDownListHotwords() {
        TopCategory getTopCategory = dao.selectTopCategoryByName(this.getSearchParam().trim());
        System.out.println("关键词参数:" + this.getSearchParam());
        if(getTopCategory != null){
            System.out.println("关键字已经存在，不会再获取热词！请到数据库和hotWords.txt中查找热词");
            return;
        }else
        {
            TopCategory topCategory = new TopCategory();
            topCategory.setId(UUID.randomUUID().toString());
            topCategory.setName(this.getSearchParam());
            topCategory.setActive(true);
            TopCategory insertTopCategory = dao.insertTopCategory(topCategory);
            List<String> hotwords = getHotwordsFromZhihuDropdown();
            //把手动补录在baiduKeywordsManage.txt中的热词添加尽量
            hotwords.addAll(FileHelper.readBiaduManageHotWords());
            //去重一下
            hotwords = hotwords.stream().distinct().collect(Collectors.toList());
            List<HotWord> hotWordList = createHotwordList(insertTopCategory,hotwords);
            dao.batchInsertUsers(hotWordList);
            WriteFile writeFile = new WriteFile(this.getSearchParam());
            try {
                writeFile.writeHotWordsToFile(hotwords);
                System.out.println("结果：");
                hotwords.forEach(System.out::println);
                System.out.println("结果已插入数据库，已写入hotWords.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private List<String> addAtoZ() {
        List<String> keys = new ArrayList<String>();
        for (int i = 97; i <= 122; i++) {
            char c = (char) i;
            String key = searchParam + c;
            keys.add(key);
        }
        return keys;
    }

    private CloseableHttpResponse sendHttpGet(String urlPrefix, String key) {
        String searchParam = null;
        CloseableHttpResponse httpResponse = null;
        try {
            searchParam = URLEncoder.encode(key, "UTF-8");
            String url = urlPrefix + searchParam;
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            httpResponse = httpClient.execute(httpGet);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    private List<String> parseHtml(CloseableHttpResponse httpResponse) {
        List<String> keys = new ArrayList<String>();
        try {
            HttpEntity entity = httpResponse.getEntity();
            InputStream is = entity.getContent();
            String html = IOUtils.toString(is, "UTF-8");
            Document document = Jsoup.parse(html);
            Elements body = document.getElementsByTag("body");
            JSONObject suggestionHtml = JSON.parseObject(body.html());
            List<QueryDto> query = JSON.parseArray(suggestionHtml.getJSONArray("suggest").toJSONString(), QueryDto.class);
            for (QueryDto q : query
            ) {
                keys.add(q.getQuery());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return keys;
    }

    private List<String> getHotwordsFromZhihuDropdown(){
        List<String> hotWords = new ArrayList<String>();
        for (String key : parameterAtoZ
        ) {
            CloseableHttpResponse response = sendHttpGet(_apiPrefix, key);
            List<String> crtHotwords = parseHtml(response);
            hotWords.addAll(crtHotwords);
        }
        return hotWords;
    }

    private List<HotWord> createHotwordList(TopCategory top, List<String> hotNames){
        List<HotWord> result = new ArrayList<>();
        for (String h:hotNames
             ) {
            HotWord hotWord = new HotWord();
            hotWord.setId(UUID.randomUUID().toString());
            hotWord.setTopCategoryID(top.getId());
            hotWord.setName(h);
            hotWord.setDoneBaidu(false);
            hotWord.setDoneZhihu(false);
            result.add(hotWord);
        }
        return result;
    }
}
