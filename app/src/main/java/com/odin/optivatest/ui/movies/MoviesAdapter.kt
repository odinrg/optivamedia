package com.odin.optivatest.ui.movies


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odin.optivatest.data.entities.MovieFav
import com.odin.optivatest.data.entities.MovieModel
import com.odin.optivatest.databinding.ItemMovieBinding
import java.util.*
import kotlin.collections.ArrayList

class MoviesAdapter(private val listener: MovieItemListener) : RecyclerView.Adapter<MoviesViewHolder>(), Filterable {

    var movieFilterList = ArrayList<MovieModel>()
    var movieList = ArrayList<MovieModel>()
    var movieListFav = ArrayList<MovieFav>()
    private val items = ArrayList<MovieModel>()

    interface MovieItemListener {
        fun onClickedMovie(movieId: String)
        fun onClickedFav(movie: MovieModel, add : Boolean)
    }

    fun setItems(items: ArrayList<MovieModel>) {
        this.items.clear()
        this.items.addAll(items)
        this.movieList = items
        notifyDataSetChanged()
    }

    fun setFavs(items: ArrayList<MovieFav>){
        movieListFav = items
        this.items.forEach({
            it.isFav = false
        })
        for(movie in movieListFav){
            this.items.find { it.externalId == movie.externalId }?.isFav = true
        }
        notifyDataSetChanged()
    }

    fun showFavs(){
        var movieFilterFavList = ArrayList<MovieModel>()
        for(movie in movieListFav){
            movieFilterFavList.add(this.items.find { it.externalId == movie.externalId }!!)
        }
        setSearchItems(movieFilterFavList)
    }

    fun showAll(){
        setItems(movieList)
    }


    fun setSearchItems(items: ArrayList<MovieModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(items[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val filterResults = FilterResults()
                if (charSearch.isEmpty()) {
                    filterResults.values = movieList
                } else {
                    val resultList = ArrayList<MovieModel>()
                    for (row in movieList) {
                        if (row.name.toLowerCase().contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    movieFilterList = resultList
                    filterResults.values = movieFilterList
                }
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movieFilterList = results?.values as ArrayList<MovieModel>
                setSearchItems(movieFilterList)
            }

        }
    }
}

class MoviesViewHolder(
    private val itemBinding: ItemMovieBinding,
    private val listener: MoviesAdapter.MovieItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movieModel: MovieModel

    init {
        itemBinding.image.setOnClickListener(this)
        itemBinding.name.setOnClickListener(this)
        itemBinding.speciesAndStatus.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: MovieModel) {
        this.movieModel = item
        itemBinding.name.text = item.name
        itemBinding.speciesAndStatus.text = """${item.id} - ${item.year}"""
        for (atta in item.attachments){
            if(atta.name.equals("VERTICAL")){
                Glide.with(itemBinding.root)
                    .load("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images/" + atta.value)
                    .into(itemBinding.image)
            }
        }
        itemBinding.fav.setOnClickListener {
            val backgroundImageName: String = itemBinding.fav.getTag().toString()
            if(backgroundImageName == "off"){
                itemBinding.fav.setTag("on")
                itemBinding.fav.setImageDrawable(itemBinding.fav.context.resources.getDrawable(android.R.drawable.btn_star_big_on))
                listener.onClickedFav(movieModel,true)
            }else{
                itemBinding.fav.setTag("off")
                itemBinding.fav.setImageDrawable(itemBinding.fav.context.resources.getDrawable(android.R.drawable.btn_star_big_off))
                listener.onClickedFav(movieModel, false)
            }

        }
        if(this.movieModel.isFav){
            itemBinding.fav.setTag("on")
            itemBinding.fav.setImageDrawable(itemBinding.fav.context.resources.getDrawable(android.R.drawable.btn_star_big_on))
        }
    }

    override fun onClick(v: View?) {
        listener.onClickedMovie(movieModel.externalId)
    }

}

