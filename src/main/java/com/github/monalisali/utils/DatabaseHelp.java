package com.github.monalisali.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.tinylog.Logger;

import java.io.IOException;
import java.io.InputStream;


public class DatabaseHelp {
    public static SqlSessionFactory getSqlSessionFactory() {
        String resource = "db/mybatis/config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            Logger.error("SqlSessionFactory error",e);
            e.printStackTrace();
        }
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
