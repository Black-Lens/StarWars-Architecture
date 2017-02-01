package com.blacklenspub.starwarsapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var starWarsApi: StarWarsApi
    lateinit var filmAdapter: FilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpWidgets()
        setUpTitle()
        setUpApi()
        loadData()
    }

    private fun setUpWidgets() {
        setUpRecyclerView()
        setUpSwipeRefreshLayout()
    }

    private fun setUpRecyclerView() {
        filmAdapter = FilmAdapter(listOf(), object : FilmAdapter.OnFilmClickListener {

            override fun onFilmClicked(film: Film) {
                FilmActivity.start(this@MainActivity, film.episodeId)
            }
        })

        rvFilms.layoutManager = LinearLayoutManager(this)
        rvFilms.adapter = filmAdapter
    }

    private fun setUpSwipeRefreshLayout() {
        srl.setOnRefreshListener {
            loadData()
        }
    }

    private fun setUpTitle() {
        title = getString(R.string.default_app_title)
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
        showLoading()
        getAllFilmsDataFromApi()
    }

    private fun getAllFilmsDataFromApi() {
        starWarsApi.getAllFilms().enqueue(object : Callback<FilmResponse> {

            override fun onResponse(call: Call<FilmResponse>?, response: Response<FilmResponse>?) {
                hideLoading()
                response?.body()?.results?.let { handleSuccessResponse(it) }
            }

            override fun onFailure(call: Call<FilmResponse>?, t: Throwable?) {
                hideLoading()
                t?.message?.let { handleFailureResponse(it) }
            }
        })

    }

    private fun handleSuccessResponse(films: List<Film>) {
        filmAdapter.films = films
        filmAdapter.notifyDataSetChanged()
    }

    private fun handleFailureResponse(errorMessage: String) {
        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        srl.isRefreshing = true
    }

    private fun hideLoading() {
        srl.isRefreshing = false
    }
}
