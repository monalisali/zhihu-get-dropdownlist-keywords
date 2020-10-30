package com.github.monalisali.zhihu.getkeywords;

import java.io.IOException;
import java.util.List;

public class Crawler {

    public static void main(String[] args) throws IOException {
        System.out.println("**************************开始*********************");
        Search search = new Search("保温饭盒");
        System.out.println("关键词参数:" + search.getSearchParam());
        List<String> hotWords = search.getDropDownListHotwords();
        System.out.println("结果：");
        hotWords.forEach(System.out::println);

        System.out.println("**************************结束*********************");

    }
}
