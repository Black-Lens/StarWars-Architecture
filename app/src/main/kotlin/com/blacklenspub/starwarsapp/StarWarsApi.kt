package com.blacklenspub.starwarsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StarWarsApi {

    @GET("films/")
    fun getAllFilms(): Call<FilmResponse>

    @GET("films/{id}/")
    fun getFilm(@Path("id") id: Long): Call<Film>
}
