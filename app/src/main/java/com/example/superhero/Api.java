package com.example.superhero;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

     @GET("all.json")

     Call<ArrayList<basic>> getAllCourses();

}
