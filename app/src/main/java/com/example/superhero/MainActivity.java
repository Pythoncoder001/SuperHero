package com.example.superhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.SearchManager;
import androidx.appcompat.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewAdapter.OnItemClickListener {
    private DrawerLayout drawer;

    private RecyclerView recyclerView;
    private ArrayList<basic> recyclerDataArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.heroes_list);
        progressBar = findViewById(R.id.progress_bar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerDataArrayList = new ArrayList<>();


        getAllCourses();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);


        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
        switch (item.getItemId()){

            case R.id.nav_all:


                break;
            case R.id.nav_Male:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new male_fragment()).commit();

                break;


            case R.id.nav_Female:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new female_fragment()).commit();

                break;
            case R.id.nav_Fav:

                break;






        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
             drawer.closeDrawer(GravityCompat.START);


        }else {
            super.onBackPressed();
        }

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
                        recyclerViewAdapter = new RecyclerViewAdapter   (recyclerDataArrayList,MainActivity.this);


                        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);


                        recyclerView.setLayoutManager(manager);


                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerViewAdapter.setOnItemClickListener(MainActivity.this);

                    }


                }

            }



            @Override
            public void onFailure(Call<ArrayList<basic>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_search,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }




    @Override
    public void onItemClick(int position) {

        Intent detailIntent = new Intent(this,DetailActivity.class);

        detailIntent.putExtra("selected_note",recyclerDataArrayList.get(position));
        startActivity(detailIntent);



    }
}


