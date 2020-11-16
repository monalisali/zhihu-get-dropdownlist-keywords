package com.github.monalisali.zhihu.getkeywords;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crawler {

    public static void main(String[] args) throws IOException {
        System.out.println("**************************开始*********************");
        Search search = new Search("保温壶");
        System.out.println("关键词参数:" + search.getSearchParam());
        List<String> hotWords = search.getDropDownListHotwords();
        WriteFile writeFile = new WriteFile(search.getSearchParam());
        writeFile.writeHotWordsToFile(hotWords);
        System.out.println("结果：");
        hotWords.forEach(System.out::println);
        System.out.println("**************************结束*********************");

    }
}
