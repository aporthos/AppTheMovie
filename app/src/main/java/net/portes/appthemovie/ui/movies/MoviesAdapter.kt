package net.portes.appthemovie.ui.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.portes.appthemovie.R
import net.portes.appthemovie.databinding.ItemMovieBinding
import net.portes.shared.extensions.binding
import net.portes.movies.domain.models.MovieDto

/**
 * @author amadeus.portes
 */
class MovieAdapter : ListAdapter<MovieDto, MovieAdapter.ViewHolder>(MovieAdapterDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.binding<ItemMovieBinding>(R.layout.item_movie)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.executePendingBindings()
    }

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieDto) {
            binding.movie = item
        }
    }

}

object MovieAdapterDiff : DiffUtil.ItemCallback<MovieDto>() {
    override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
        return oldItem.id == newItem.id
    }
}