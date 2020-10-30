package com.github.monalisali.zhihu.getkeywords;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class WriteFile {
    private static String _filePath = "./hotWords.txt";

    public WriteFile (String param){
        this.searchParam = param;
    }

    public String getSearchParam() {
        return searchParam;
    }

    private String searchParam;

    public void writeHotWordsToFile(List<String> hotWords) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append("***********************************************************\r\n");
        LocalDateTime localDateTime = LocalDateTime.now();
        stringBuilder.append(localDateTime.toLocalDate() + " " + localDateTime.toLocalTime() + "\r\n");
        stringBuilder.append("搜索关键词：" + searchParam + "\r\n");
        for (String k: hotWords
        ) {
            hotWords.set(hotWords.indexOf(k), k + "\r\n");
        }
        hotWords.forEach(x->stringBuilder.append(x));
        stringBuilder.append("***********************************************************\r\n");
        FileWriter writer = null;
        try {
            writer = new FileWriter(_filePath, true);
            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
