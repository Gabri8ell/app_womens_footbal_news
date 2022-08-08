package app.mypc.womensfootballnews.ui.news

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.mypc.womensfootballnews.api.ApiNews
import app.mypc.womensfootballnews.model.News
import app.mypc.womensfootballnews.repository.GetAllApiNews
import app.mypc.womensfootballnews.service.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel() : ViewModel() {

    private val _text = MutableLiveData<List<News>>().apply {
            var apiNews: ApiNews = GetAllApiNews.news

            apiNews.getNews().enqueue(object : Callback<List<News>>{
                override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                    if (response.isSuccessful){
                        value = response.body()
                    }else{
                        TODO("Not yet implemented")
                    }
                }

                override fun onFailure(call: Call<List<News>>, t: Throwable) {
                    Log.e("E R R O", "E r r o ", t)
                }

            })
        }

    val text = _text
}