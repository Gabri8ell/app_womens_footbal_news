package app.mypc.womensfootballnews.api

import app.mypc.womensfootballnews.model.News
import retrofit2.Call
import retrofit2.http.GET

interface ApiNews {

    @GET("apiNews.json")
    fun getNews(): Call<List<News>>
}