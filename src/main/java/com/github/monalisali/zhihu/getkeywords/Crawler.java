package com.github.monalisali.zhihu.getkeywords;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class Crawler {
    private static final String _apiPrefix = "https://www.zhihu.com/api/v4/search/suggest?q=";
    public static void main(String[] args) throws IOException {
        System.out.println("**************************开始*********************");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String searchParam = URLEncoder.encode("保温饭盒","UTF-8");
        //HttpGet httpGet = new HttpGet("https://www.zhihu.com/api/v4/search/suggest?q=%E4%BF%9D%E6%B8%A9%E9%A5%AD%E7%9B%92");
        HttpGet httpGet = new HttpGet(_apiPrefix + searchParam);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        //测试
        Search search = new Search("保温饭盒");
        search.getDropDownListKeys();
        //测试
        try{
            System.out.println(httpResponse.getStatusLine());
            HttpEntity entity1 = httpResponse.getEntity();
            InputStream is = entity1.getContent();
            String html = IOUtils.toString(is,"UTF-8");
            Document document = Jsoup.parse(html);
            System.out.println(document);


            System.out.println("**************************结束*********************");

        }finally {

        }

    }
}
