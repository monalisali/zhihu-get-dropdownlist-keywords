package com.github.monalisali.dao;

import com.github.monalisali.entity.HotWord;
import com.github.monalisali.entity.TopCategory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao {
    private final SqlSessionFactory sqlSessionFactory;

    public Dao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public TopCategory selectTopCategoryByName(String name) {
        try(SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            return session.selectOne("com.hcsp.Mapper.selectTopCategoryByName", map);
        }
    }

    public TopCategory insertTopCategory(TopCategory topCategory){
        try (SqlSession session = sqlSessionFactory.openSession()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", topCategory.getId());
            map.put("name",topCategory.getName());
            map.put("isActive",topCategory.isActive());
            session.insert("com.hcsp.Mapper.insertTopCategory",map);
            session.commit();
        }

        return topCategory;
    }

    public void batchInsertUsers(List<HotWord> hotwords){
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            Map<String, Object> map = new HashMap<>();
            map.put("hotwords", hotwords);
            session.insert("com.hcsp.Mapper.insertHotWords", map);
            session.commit();
        }
    }

    public List<HotWord> selectAllHotWords(){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("com.hcsp.Mapper.selectAllHotWord");
        }
    }
}
