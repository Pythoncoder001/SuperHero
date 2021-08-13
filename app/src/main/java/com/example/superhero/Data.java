package com.example.superhero;

import java.util.List;

public class Data {

    public  String queryName;
    public  String queryValue;


    public  String getQueryName(){
        return queryName;
    }

    public String getQueryValue(){
        return queryValue;

    }
    public Data(String a, String b){
        queryName=a;
        queryValue=b;

    }

}
