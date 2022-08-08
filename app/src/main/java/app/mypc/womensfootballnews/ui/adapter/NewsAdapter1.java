package app.mypc.womensfootballnews.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import java.util.List;
import app.mypc.womensfootballnews.R;
import app.mypc.womensfootballnews.databinding.NewsItemBinding;
import app.mypc.womensfootballnews.model.News;

public class NewsAdapter1 extends RecyclerView.Adapter<NewsAdapter1.ViewHolder> {
    private List<News> newsList;
    private SetListFav setListFav;

    public NewsAdapter1(List<News> news, SetListFav setListFav) {
        this.newsList = news;
        this.setListFav = setListFav;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final NewsItemBinding binding;
        public ViewHolder(@NonNull NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public NewsAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(lInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter1.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        News news1 = newsList.get(position);

            holder.binding.txwTitle.setText(news1.title);
            holder.binding.txwSecText.setText(news1.getSecText());
            Picasso.get().load(news1.image).into(holder.binding.mediaImg);

            holder.binding.openLink.setOnClickListener(l -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(news1.link));
                context.startActivity(i);
            });

            holder.binding.imgLike.setOnClickListener(l -> {
                news1.setFavorite(!news1.getFavorite());
                this.setListFav.onFavorite(news1);
                notifyItemChanged(position);

                String message = news1.getFavorite() ? "Notícia adiciona aos favoritos" : "Notícia removida dos favorítos";
                Snackbar.make(holder.itemView, message, Snackbar.LENGTH_SHORT).show();

            });

            int favIcon = news1.getFavorite() ? R.drawable.ic_favorite_true : R.drawable.ic_favorites;
            holder.binding.imgLike.setImageResource(favIcon);

            holder.binding.imgShare.setOnClickListener(l -> {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, newsList.get(position).link);
                context.startActivity(Intent.createChooser(i, "share"));
            });
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public interface SetListFav{
        void onFavorite(News news);
    }
}
