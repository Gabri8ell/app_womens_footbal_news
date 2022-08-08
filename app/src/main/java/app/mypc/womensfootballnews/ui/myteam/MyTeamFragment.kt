package app.mypc.womensfootballnews.ui.myteam

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.mypc.womensfootballnews.R
import app.mypc.womensfootballnews.api.local.AppDatabase
import app.mypc.womensfootballnews.databinding.FragmentMyteamBinding
import app.mypc.womensfootballnews.model.News
import app.mypc.womensfootballnews.repository.GetAllApiNews.setupHttpClient.news
import app.mypc.womensfootballnews.service.Service
import app.mypc.womensfootballnews.ui.adapter.NewsAdapter1
import kotlinx.coroutines.launch

class MyTeamFragment : Fragment() {

    private var _binding: FragmentMyteamBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val service by lazy { Service() }
    private lateinit var news : List<News>

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val myTeamViewModel = ViewModelProvider(this).get(MyTeamViewModel::class.java)

        _binding = FragmentMyteamBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rcvNews.layoutManager = LinearLayoutManager(context)

        binding.btnSearch.setOnClickListener {
            var search = binding.edtSearch.text.toString()
            loadLocalDb(search)
        }

        return root
    }

    private fun loadLocalDb(search: String) {
        service.openBD(binding.root.context)!!
        lifecycleScope.launch {
            news = service.searchForWord(search)
            binding.rcvNews.adapter = NewsAdapter1(news, {

            lifecycleScope.launch{
                service.save(it)
               }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}