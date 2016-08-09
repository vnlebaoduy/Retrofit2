package com.duylb.retrofit2.rest;

import android.content.Context;

import com.duylb.retrofit2.BuildConfig;
import com.duylb.retrofit2.model.Movie;
import com.duylb.retrofit2.model.MovieRes;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vnleb on 08/02/16.
 */
public interface APIClient {
    String BASE_URL ="http://api.themoviedb.org/3/";
    @GET("movie/top_rated")
    Call<MovieRes> getTopRatedMovies(@Query("api_key") String apiKey);

    class Factory {
        private static APIClient service;

        public static APIClient getIstance(Context context) {
            if (service == null) {

                OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
                builder.readTimeout(15, TimeUnit.SECONDS);
                builder.connectTimeout(10, TimeUnit.SECONDS);
                builder.writeTimeout(10, TimeUnit.SECONDS);


                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder.addInterceptor(interceptor);
                }

                int cacheSize = 50 * 1024 * 1024; // 10 MiB
                Cache cache = new Cache(context.getCacheDir(), cacheSize);
                builder.cache(cache);

                Retrofit retrofit = new Retrofit.Builder()
                        .client(builder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(APIClient.class);
                return service;
            } else {
                return service;
            }
        }
    }
}
