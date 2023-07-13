package io.buildwithnd.demotmdb.ui.details

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import io.buildwithnd.demotmdb.Config
import io.buildwithnd.demotmdb.R
import io.buildwithnd.demotmdb.databinding.ActivityDetailsBinding
import io.buildwithnd.demotmdb.model.MovieDesc
import kotlinx.android.synthetic.main.activity_details.*

/**
 * Shows detailed information about movie/show
 */
@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val viewModel by viewModels<DetailsViewModel>()

    private val genreNames = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent?.getIntExtra(EXTRAS_MOVIE_ID, 0)?.let {
            viewModel.fetchDetails(it)
            setUpObservers()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setUpObservers() {
        viewModel.loadingLiveData.observe(this, Observer {
            binding.loading.isVisible = it
        })
        viewModel.movieDetailLiveData.observe(this, Observer {
            updateUi(it)
        })
        viewModel.showErrorLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateUi(movie: MovieDesc) {
        title = movie.title
        tvTitle.text = movie.title
        tvDescription.text = movie.overview
        movie.genres.map { genreNames.add(it.name) }
        tvGenre.text = genreNames.joinToString(separator = ", ")

        // Set Image
        Glide
            .with(this)
            .load(
                Config
                    .IMAGE_URL + movie
                    .poster_path
            )
            .apply(
                RequestOptions()
                    .override(400, 400)
                    .centerInside()
                    .placeholder(R.drawable.placehoder)
            )
            .into(ivCover)
    }

    companion object {
        const val EXTRAS_MOVIE_ID = "movie_id"
    }
}