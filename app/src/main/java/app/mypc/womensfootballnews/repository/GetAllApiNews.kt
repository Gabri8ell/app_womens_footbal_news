package app.mypc.womensfootballnews.repository

import app.mypc.womensfootballnews.api.ApiNews
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetAllApiNews {

    companion object setupHttpClient{
        var retrofit = Retrofit.Builder()
            .baseUrl("https://gabri8ell.github.io/Api-news_utebolFeminino/")
            .addConverterFactory(GsonConverterFactory.create()).build()

         var news = retrofit.create(ApiNews::class.java)

    }

}