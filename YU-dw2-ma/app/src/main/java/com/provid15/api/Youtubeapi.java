package com.provid15.api;



import com.provid15.api.POJO.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Youtubeapi {


    @GET("/youtube/v3/search")
    Call<Example> getVideos(
            @Query("type") String type,
            @Query("part") String part,
            @Query("order") String order,
            @Query("q") String key_word,
            @Query("maxResults") int maxResults,
            @Query("key") String api_key
    );

}
