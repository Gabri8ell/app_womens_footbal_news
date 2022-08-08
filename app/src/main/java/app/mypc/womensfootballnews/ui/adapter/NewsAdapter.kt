package app.mypc.womensfootballnews.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.RecyclerView
import app.mypc.womensfootballnews.R
import app.mypc.womensfootballnews.databinding.NewsItemBinding
import app.mypc.womensfootballnews.model.News
import com.google.android.material.snackbar.Snackbar
//import com.bumptech.glide.Glide se quiser usar
import com.squareup.picasso.Picasso

class NewsAdapter (private val news: List<News>, private val favoritedNews: NewsListFav): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var listNews = news;
    private var listFavNews = favoritedNews
    private var listNewsFav = mutableListOf<News>()

    //Cria o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layouInflate = LayoutInflater.from(parent.context)
        var binding = NewsItemBinding.inflate(layouInflate, parent, false)

        return ViewHolder(binding)
    }

     //Providencia referencia para o tipo de view que você está usando
    class ViewHolder (biding: NewsItemBinding): RecyclerView.ViewHolder(biding.root){
        var binding = biding
    }


    //Associar o ViewHolder aos dados - acessar as views que estão no layout e passar os conteúdos para elas
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.txwTitle.text = listNews[position].title;
        holder.binding.txwSecText.text = listNews[position].secText;
        Picasso.get().load(listNews[position].image).into(holder.binding.mediaImg)
//        Glide.with(holder.itemView.context).load(listNews[position].image).into(holder.binding.mediaImg)

        //Testar o link do botão
        holder.binding.openLink.setOnClickListener {
                var i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse(listNews[position].link))
                holder.itemView.context.startActivity(i)
        }

    //Adicionar/remover news aos favoritos mudar com do icon
    //enviar listNewsFav para favorites
        holder.binding.imgLike.setOnClickListener { l ->
            listFavNews.onClick(listNews[position])

            //Inicializar a lista de favoritos
            if (listNews[position].favorite){
                listNews[position].favorite = false;
                holder.binding.imgLike.setImageResource(R.drawable.ic_favorites)

                notifyItemChanged(position)
                Toast.makeText( holder.itemView.context,"Notícia removido dos favoritos.", Toast.LENGTH_SHORT).show()
            }else{
                listNews[position].favorite = true;
                holder.binding.imgLike.setImageResource(R.drawable.ic_favorite_true)
                notifyItemChanged(position)
                Snackbar.make( holder.itemView,"Notícia adicionado aos favoritos.", Snackbar.LENGTH_SHORT).show()
            }
        }

        holder.binding.imgShare.setOnClickListener {  }
    }

    //Para ver o tamanho do conjunto de dados
    override fun getItemCount(): Int {
        return listNews.size
    }

    interface NewsListFav{
        fun onClick(news: News)
    }
}