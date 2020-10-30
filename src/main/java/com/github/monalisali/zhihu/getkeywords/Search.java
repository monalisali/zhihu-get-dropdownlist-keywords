package com.github.monalisali.zhihu.getkeywords;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.monalisali.utils.QueryDto;
import org.apache.commons.io.IOUtils;
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

public class Search {
    private static final String _apiPrefix = "https://www.zhihu.com/api/v4/search/suggest?q=";

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

    public List<String> getDropDownListHotwords() {
        List<String> hotWords = new ArrayList<String>();
        for (String key : parameterAtoZ
        ) {
            CloseableHttpResponse response = sendHttpGet(_apiPrefix, key);
            List<String> crtHotwords = parseHtml(response);
            hotWords.addAll(crtHotwords);
        }
        return hotWords;
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

}
