package com.github.monalisali.zhihu.getkeywords;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.print.Doc;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Search {
    private static final String _apiPrefix = "https://www.zhihu.com/api/v4/search/suggest?q=";

    public Search(String param){
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

    public List<String> getDropDownListKeys() {
        String key = parameterAtoZ.get(0);
        CloseableHttpResponse resp = sendHttpGet(_apiPrefix, key);
        Document document = parseHtml(resp);
        return null;
    }

    private List<String> addAtoZ(){
        List<String> keys = new ArrayList<String>();
        for(int i = 97; i<=122; i++){
            char c = (char)i;
            String key = searchParam + c;
            keys.add(key);
        }
        return keys;
    }

    private CloseableHttpResponse sendHttpGet(String urlPrefix, String key){
        String searchParam = null;
        CloseableHttpResponse httpResponse = null;
        try {
            searchParam = URLEncoder.encode(key,"UTF-8");
            String url = urlPrefix + searchParam;
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            httpResponse = httpClient.execute(httpGet);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    private Document parseHtml(CloseableHttpResponse httpResponse){
        Document document = null;
        try {
            HttpEntity entity = httpResponse.getEntity();
            InputStream is = entity.getContent();
            String html = IOUtils.toString(is,"UTF-8");
            document = Jsoup.parse(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

}
