package com.github.monalisali.entity;

import java.math.BigInteger;

public class HotWord {
    private String id;
    private String topCategoryID;
    private String Name;
    private boolean isDoneBaidu;
    private boolean isDoneZhihu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopCategoryID() {
        return topCategoryID;
    }

    public void setTopCategoryID(String topCategoryID) {
        this.topCategoryID = topCategoryID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isDoneBaidu() {
        return isDoneBaidu;
    }

    public void setDoneBaidu(boolean doneBaidu) {
        isDoneBaidu = doneBaidu;
    }

    public boolean isDoneZhihu() {
        return isDoneZhihu;
    }

    public void setDoneZhihu(boolean doneZhihu) {
        isDoneZhihu = doneZhihu;
    }
}
