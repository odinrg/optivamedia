package com.odin.optivatest.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.odin.optivatest.R
import com.odin.optivatest.data.entities.MovieFav
import com.odin.optivatest.data.entities.MovieModel
import com.odin.optivatest.databinding.ListFragmentBinding
import com.odin.optivatest.utils.Resource
import com.odin.optivatest.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesFragment : Fragment(), MoviesAdapter.MovieItemListener {

    private var binding: ListFragmentBinding by autoCleared()
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter
    var textSearch : String = ""
    private var favMovies : List<MovieFav> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupSearch()
        setupFavBtn()
    }

    private fun setupRecyclerView() {
        adapter = MoviesAdapter(this)
        binding.moviesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRv.adapter = adapter
    }

    private fun setupFavBtn(){
        binding.favBtn.setOnClickListener {
            adapter.showFavs()
            //TODO dejar de mostrar solo favoritos al volver a pulsar el botón
            /*if(){
                adapter.showFavs()
            }else{
                adapter.showAll()
            }*/
        }
    }

    private fun setupSearch(){
        binding.movieSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                textSearch = newText.toString()
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun setupObserverFav() {
        viewModel.moviesFav.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    favMovies = it.data!!
                    adapter.setFavs(ArrayList(it.data))
                }
            }
        })
    }

    private fun setupObservers(){
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                    setupObserverFav()
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }



    override fun onClickedMovie(movieId: String) {
        findNavController().navigate(
            R.id.action_moviesFragment_to_movieDetailFragment,
            bundleOf("id" to movieId)
        )
    }

    override fun onClickedFav(movie: MovieModel, add: Boolean) {
        if(add){
            viewModel.insert(movie)
        }else{
            viewModel.delete(movie)
        }
    }


    override fun onResume() {
        super.onResume()
        adapter.filter.filter(textSearch)
    }

}
