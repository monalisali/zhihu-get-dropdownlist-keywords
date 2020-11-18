package com.github.monalisali.zhihu.getkeywords;

import com.github.monalisali.dao.Dao;
import com.github.monalisali.entity.HotWord;
import com.github.monalisali.entity.TopCategory;
import com.github.monalisali.utils.DatabaseHelp;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crawler {
    private static Dao dao;
    public static void main(String[] args) throws IOException {
//        try {
//            dao = new Dao(DatabaseHelp.getSqlSessionFactory());
//            TopCategory topCategory = dao.selectTopCategoryID("6AC1705C-1BCB-49F6-AB19-46667E13A1CB");
//            List<HotWord> words = dao.selectAllHotWords();
//            String ss = "";
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        System.out.println("**************************开始*********************");
        System.out.println("请输入关键字，按回车键结束");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String keyword = br.readLine();
        Search search = new Search(keyword);
        System.out.println("关键词参数:" + search.getSearchParam());
        List<String> hotWords = search.getDropDownListHotwords();
        WriteFile writeFile = new WriteFile(search.getSearchParam());
        writeFile.writeHotWordsToFile(hotWords);
        System.out.println("结果：");
        hotWords.forEach(System.out::println);
        System.out.println("**************************结束*********************");

    }
}
