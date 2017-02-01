package com.blacklenspub.starwarsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_film.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmActivity : AppCompatActivity() {

    companion object {

        val KEY_FILM_ID = "FILM_ID"

        fun start(context: Context, episodeId: Long) {
            val starter = Intent(context, FilmActivity::class.java)
            starter.putExtra(KEY_FILM_ID, episodeId)
            context.startActivity(starter)
        }
    }

    lateinit var starWarsApi: StarWarsApi

    var episodeId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)
        retrieveIntentData()
        setUpApi()
        loadData()
    }

    private fun retrieveIntentData() {
        episodeId = intent.getLongExtra(KEY_FILM_ID, 0L)
    }

    private fun setUpApi() {
        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://swapi.co/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        starWarsApi = retrofit.create(StarWarsApi::class.java)
    }

    private fun loadData() {
        showProgressBar()
        getFilmDataFromApi()
    }

    private fun getFilmDataFromApi() {
        starWarsApi.getFilm(episodeId).enqueue(object : Callback<Film> {

            override fun onResponse(call: Call<Film>?, response: Response<Film>?) {
                hideProgressBar()
                response?.body()?.let { handleSuccessResponse(it) }
            }

            override fun onFailure(call: Call<Film>?, t: Throwable?) {
                hideProgressBar()
                t?.message?.let { handleFailureResponse(it) }
            }
        })
    }

    private fun handleSuccessResponse(film: Film) {
        title = film.title
        tvReleaseDate.text = film.releaseDate
        tvDirector.text = film.director
        tvCrawl.text = film.openingCrawl
    }

    private fun handleFailureResponse(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

}
