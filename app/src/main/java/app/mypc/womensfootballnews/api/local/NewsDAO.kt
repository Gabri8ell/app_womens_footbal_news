package app.mypc.womensfootballnews.api.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.mypc.womensfootballnews.model.News

@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(news: News)

    @Query("SELECT * FROM news ORDER BY id DESC")
    suspend fun getAll(): MutableList<News>

    @Query("SELECT * FROM news WHERE favorite = :favorite")
    suspend fun getOnlyFav(favorite: Boolean): List<News>

    @Query("SELECT * FROM news WHERE UPPER(title) LIKE  '%' || UPPER(:palavra) ||'%'")
    suspend fun searchForWord(palavra: String):List<News>

    @Query("SELECT * FROM news WHERE id > :id")
    suspend fun ifExist(id: Int):MutableList<News>

    @Query("DELETE FROM news WHERE id > 0 ")
    suspend fun deleteAll()
}