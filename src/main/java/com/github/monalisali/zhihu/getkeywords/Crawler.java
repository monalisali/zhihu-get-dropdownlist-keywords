package com.github.monalisali.zhihu.getkeywords;

import com.github.monalisali.dao.Dao;
import java.io.*;


public class Crawler {
    private static Dao dao;
    public static void main(String[] args) throws IOException {
        System.out.println("**************************开始*********************");
        System.out.println("请输入需要查找热词的关键字，按回车键结束");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String keyword = br.readLine();
        Search search = new Search(keyword);
        search.getDropDownListHotwords();
        System.out.println("**************************结束*********************");

    }
}
