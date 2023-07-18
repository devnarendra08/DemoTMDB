package io.buildwithnd.demotmdb.ui.listing

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.buildwithnd.demotmdb.databinding.ActivityMainBinding
import io.buildwithnd.demotmdb.model.Movie
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Shows list of movie/show
 */

@AndroidEntryPoint
class ListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Movie>()
    private val viewModel by viewModels<ListingViewModel>()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setUpObservers()
    }

    private fun init() {
        title = "Movies"
        val layoutManager = LinearLayoutManager(this)
        rvMovies.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            rvMovies.context,
            layoutManager.orientation
        )
        rvMovies.addItemDecoration(dividerItemDecoration)
        moviesAdapter = MoviesAdapter(this, list)
        rvMovies.adapter = moviesAdapter

        binding.searchHome.doOnTextChanged { query, _, _, _ ->
            viewModel.searchMovies(query.toString())
        }
    }

    private fun setUpObservers() {
        viewModel.loadingIsShowing.observe(
            this,
            Observer {
                binding.loading.isVisible = it
            }
        )
        viewModel.showError.observe(
            this,
            Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        )
        viewModel.movieList.observe(
            this,
            Observer {
                moviesAdapter.updateData(it)
            }
        )
    }
}
