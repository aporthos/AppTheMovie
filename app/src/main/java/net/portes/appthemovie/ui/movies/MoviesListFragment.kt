package net.portes.appthemovie.ui.movies

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.portes.appthemovie.R
import net.portes.appthemovie.databinding.FragmentMoviesListBinding
import net.portes.appthemovie.ui.base.BaseFragment
import net.portes.shared.extensions.observe
import net.portes.shared.ui.base.ViewState
import net.portes.movies.domain.models.MovieDto

/**
 * @author amadeus.portes
 */
@AndroidEntryPoint
class MoviesListFragment : BaseFragment<FragmentMoviesListBinding>() {

    private val viewModel: MoviesListViewModel by viewModels()

    private lateinit var moviesAdapter: MovieAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_movies_list

    override fun initializeView() {
        moviesAdapter = MovieAdapter()

        dataBinding().moviesRecyclerView.adapter = moviesAdapter

        dataBinding().moviesSwipeRefreshLayout.setOnRefreshListener {
            dataBinding().moviesSwipeRefreshLayout.isRefreshing = false
            // TODO: 23/10/21 Try to use dinamic query
            viewModel.getMoviesList("marvel")
        }
        // TODO: 23/10/21 Try to use dinamic query
        viewModel.getMoviesList("marvel")
    }

    override fun initObservers() {
        observe(viewModel.getMoviesList, ::resultMovieList)
    }

    private fun resultMovieList(result: ViewState<List<MovieDto>>) {
        when (result) {
            is ViewState.Loading -> showLoader()
            is ViewState.Success -> {
                hideLoader()
                moviesAdapter.submitList(result.data)
            }
            is ViewState.Error -> {
                hideLoader()
            }
        }
    }
}