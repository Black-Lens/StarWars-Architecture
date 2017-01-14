package com.blacklenspub.starwarsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StarWarsApi {
    @GET("films/")
    Call<FilmResponse> getAllFilms();

    @GET("films/{id}/")
    Call<Film> getFilm(@Path("id") long id);
}
