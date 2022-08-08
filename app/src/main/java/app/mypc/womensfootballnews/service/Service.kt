package app.mypc.womensfootballnews.service

import android.content.Context
import android.media.CamcorderProfile.getAll
import android.text.BoringLayout
import androidx.lifecycle.MutableLiveData
import app.mypc.womensfootballnews.api.ApiNews
import app.mypc.womensfootballnews.api.local.AppDatabase
import app.mypc.womensfootballnews.model.News
import app.mypc.womensfootballnews.repository.GetAllApiNews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Service {
    private lateinit var db : AppDatabase
    private lateinit var apiNews : ApiNews

    fun openBD(context: Context){
        db = AppDatabase.getInstance(context)!!
    }

    suspend fun save(news : News){
        db.novoDao().save(news)
    }

    suspend fun getAll(): MutableList<News>{
       return db.novoDao().getAll()
    }

    suspend fun getOnlyFav() : List<News>{
        return db.novoDao().getOnlyFav(true)
    }

    suspend fun searchForWord(word: String): List<News>{
        return db.novoDao().searchForWord(word)
    }

    suspend fun ifExist(id: Int):MutableList<News>{
        return db.novoDao().ifExist(id)
    }

    suspend fun deleteAll(){
        db.novoDao().deleteAll()
    }

    suspend fun addToLocalDB(){
        //Jogo no BD local as news que não estão nele
        var newsToSave : List<News>? = null

        apiNews = GetAllApiNews.news
        apiNews.getNews().enqueue(object : Callback<List<News>>{
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                newsToSave = response.body()
            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        var news = getAll()

        newsToSave?.forEach {
//            se a bd local não estiver vazio
            if (!news.isEmpty()){
                if (news[news.lastIndex].id < it.id){
                    save(it)
                }
            }else{
                save(it)
            }
        }
    }
}