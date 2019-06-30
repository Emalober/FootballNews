package com.ar.maloba.footballnews.remote

import com.ar.maloba.footballnews.model.RssResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface RssAPI {

    @Headers("Accept:application/xml;charset=utf-8")
    @GET("/football/rss.xml")
    fun getNews(): Call<RssResponse>

}