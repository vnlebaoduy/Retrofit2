package com.duylb.retrofit2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.duylb.retrofit2.R;
import com.duylb.retrofit2.adapter.MovieAdapter;
import com.duylb.retrofit2.model.Movie;
import com.duylb.retrofit2.model.MovieRes;
import com.duylb.retrofit2.rest.APIClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "677c0ea2dba8ab930c73c4baa5e6b064"; // thay API KEY cua ban vao day !!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvMovie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        APIClient apiClient=APIClient.Factory.getIstance(getApplicationContext());
        apiClient.getTopRatedMovies(API_KEY).enqueue(new Callback<MovieRes>() {
            @Override
            public void onResponse(Call<MovieRes> call, Response<MovieRes> response) {
                List<Movie> movieList=response.body().getResults();
                Log.d(TAG,"Count Movie:"+ movieList.size());
                recyclerView.setAdapter(new MovieAdapter(movieList, R.layout.list_item, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieRes> call, Throwable t) {
                Log.e(TAG,"Error:"+ t.getMessage());
            }
        });
    }
}
