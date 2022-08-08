package app.mypc.womensfootballnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class News() {

    @PrimaryKey
    var id: Int = 0
    lateinit var title : String
    lateinit var secText : String
    lateinit var image : String
    lateinit var link: String
    var favorite: Boolean = false



}