package com.example.superhero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class female_fragment extends Fragment   {
    private RecyclerView recyclerView;
    private ArrayList<basic> recyclerDataArrayList;
    private RecyclerViewAdapterFemale recyclerViewAdapterFemale;
    private ProgressBar progressBar;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.heroes_list_female);
        progressBar = view.findViewById(R.id.progress_bar_female);
        recyclerDataArrayList = new ArrayList<>();

        getAllCourses();

    }



    private void getAllCourses() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://akabab.github.io/superhero-api/api/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        Api retrofitAPI = retrofit.create(Api.class);


        Call<ArrayList<basic>> call = retrofitAPI.getAllCourses();


        call.enqueue(new Callback<ArrayList<basic>>() {
            @Override
            public void onResponse(Call<ArrayList<basic>> call, Response<ArrayList<basic>> response) {

                if (response.isSuccessful()) {


                    progressBar.setVisibility(View.GONE);


                    recyclerDataArrayList = response.body();


                    for (int i = 0; i < Objects.requireNonNull(recyclerDataArrayList).size(); i++) {
                        recyclerViewAdapterFemale = new RecyclerViewAdapterFemale   (recyclerDataArrayList,getContext());


                        LinearLayoutManager manager = new LinearLayoutManager(getContext());


                        recyclerView.setLayoutManager(manager);


                        recyclerView.setAdapter(recyclerViewAdapterFemale);


                    }


                }

            }



            @Override
            public void onFailure(Call<ArrayList<basic>> call, Throwable t) {

                Toast.makeText(getContext(), "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }









    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_male_fragment_female,container,false);
    }



}
