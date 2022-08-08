package app.mypc.womensfootballnews.ui.news

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.mypc.womensfootballnews.databinding.FragmentNewsBinding
import app.mypc.womensfootballnews.model.News
import app.mypc.womensfootballnews.service.Service
import app.mypc.womensfootballnews.ui.adapter.NewsAdapter1
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

//@AndroidEntryPoint permite a injeção de membros
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val service by lazy { Service() }
    lateinit var news: MutableList<News>
    var isLoading = false
    lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rcvNews.layoutManager = LinearLayoutManager(context)
        service.openBD(binding.root.context)!!;

        lifecycleScope.launch{
            service.addToLocalDB()//Jogo as news da Api remota no bd Local
            news = service.getAll()//Pego todas as news do BD Local

//          pego as news do BD local e jogo no adapter
            binding.rcvNews.adapter = NewsAdapter1(news, {
                lifecycleScope.launch {

                    service.save(it)//Salvo a news no BD local
                }
            })
        }

        initSwipeRefresh()

        return root
    }

    fun initSwipeRefresh(){
        binding.swRefresh.setOnRefreshListener {
//          pego as news da API remota e jogo no BD loca
            newsViewModel.text.observe(viewLifecycleOwner, {
                lifecycleScope.launch{
                    service.addToLocalDB()//Jogo as news da Api remota no bd Local

                    news = service.getAll()//Pego todas as news do BD Local

//                  pego as news do BD local e jogo no adapter
                    binding.rcvNews.adapter = NewsAdapter1(news, {
                        lifecycleScope.launch {

                            service.save(it)//Salvo a news no BD local
                        }
                    })
                }
            })
//          tirar o icone do refresh da tela
            if (binding.swRefresh.isRefreshing){
                binding.swRefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}