package com.provid15.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;




public interface IApiMethods {

    @GET("/get/curators.json")
    UsernameChannel getCurators(
            @Query("api_key") String key
    );



    @GET("/youtube/v3")
    Call<YTrailerModel> getHomePageList(
            @Query("source") String source,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );




    @GET("/youtube/v3/search")
    Call<YTrailerModel> getSearchVideo(
                    @Query("part") String part,
                    @Query("q") String q,
                    @Query("type") String type,
                    @Query("key") String key,
                    @Query("maxResults") String maxResults
            );


    @GET("/youtube/v3/search")
    Call<YTrailerModel> getSeachVideosTrailerNextPage
            (
                    @Query("part") String part,
                    @Query("q") String q,
                    @Query("type") String type,
                    @Query("key") String key,
                    @Query("pageToken") String pageToken,
                    @Query("maxResults") String maxResults
            );

    @GET("/youtube/v3/search")
    Call<YTrailerModel> getSeachVideosTrailer
            (
                    @Query("part") String part,
                    @Query("q") String q,
                    @Query("type") String type,
                    @Query("key") String key,

                    @Query("maxResults") String maxResults
            );

}