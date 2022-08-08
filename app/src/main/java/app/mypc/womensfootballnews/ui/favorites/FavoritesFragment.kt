package app.mypc.womensfootballnews.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.mypc.womensfootballnews.api.local.AppDatabase
import app.mypc.womensfootballnews.databinding.FragmentFavoritesBinding
import app.mypc.womensfootballnews.service.Service
import app.mypc.womensfootballnews.ui.adapter.NewsAdapter1
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val service by lazy {
        Service()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        val favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        service.openBD(binding.root.context)!!
        lifecycleScope.launch{

            loadLocalBD()
        }

        return root
    }

    suspend fun loadLocalBD() {
        var news = service.getOnlyFav()

        binding.rcvNews.layoutManager = LinearLayoutManager(context)
        binding.rcvNews.adapter = NewsAdapter1(news, {
            lifecycleScope.launch {

           service.save(it)
            loadLocalBD()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}