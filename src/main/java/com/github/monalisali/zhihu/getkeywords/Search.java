package com.github.monalisali.zhihu.getkeywords;


import java.util.List;

public class Search {
    public Search(String param){
        searchParam = param;
        parameterAtoZ = addAtoZ();
    }

    private String searchParam;
    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        searchParam = searchParam;
    }



    //把searchParam末尾依次加上字母（A-Z），形成一个新的参数列表
    private List<String> parameterAtoZ;

    public List<String> getParameterAtoZ() {
        return parameterAtoZ;
    }

    private List<String> addAtoZ(){
        return null;
    }


    public List<String> GetKeywords(List<String> params)
    {
        return null;
    }

}
