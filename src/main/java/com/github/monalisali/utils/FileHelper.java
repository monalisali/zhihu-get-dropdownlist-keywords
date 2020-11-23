package com.github.monalisali.utils;

import org.apache.commons.codec.Charsets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    //读取百度规划关键字文件中的内容
    public static List<String> readBiaduManageHotWords() {
        List<String> hotWords = new ArrayList<>();
        String filePath = "./baiduKeywordsManage.txt";
        Path path = Paths.get(filePath);
        try {
            hotWords = Files.readAllLines(path, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hotWords;
    }
}
