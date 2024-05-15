package com.techverse.controller;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {
    @FormUrlEncoded
    @POST
    Call<PostModel> getPosts(
            @Url String url,
            @Field("q") String q,
            @Field("t") String t,
            @Field("lang") String lang,
            @Header("Accept") String accept,
            @Header("Accept-Encoding") String acceptEncoding,
            @Header("Accept-Language") String acceptLanguage,
            @Header("Content-Type") String contentType,
            @Header("Origin") String origin,
            @Header("Priority") String priority,
            @Header("Referer") String referer,
            @Header("Sec-Ch-Ua") String secChUa,
            @Header("Sec-Ch-Ua-Mobile") String secChUaMobile,
            @Header("Sec-Ch-Ua-Platform") String secChUaPlatform,
            @Header("Sec-Fetch-Dest") String secFetchDest,
            @Header("Sec-Fetch-Mode") String secFetchMode,
            @Header("Sec-Fetch-Site") String secFetchSite,
            @Header("User-Agent") String userAgent
    );
}
