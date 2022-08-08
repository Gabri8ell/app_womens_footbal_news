package app.mypc.womensfootballnews.api.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.mypc.womensfootballnews.model.News

@Database( version = 1, entities = [News::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun novoDao(): NewsDAO

    companion object{
        private  var INSTANCE: AppDatabase? = null;

        fun getInstance(context: Context): AppDatabase?{
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "soccer-news")
                        .allowMainThreadQueries().build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}