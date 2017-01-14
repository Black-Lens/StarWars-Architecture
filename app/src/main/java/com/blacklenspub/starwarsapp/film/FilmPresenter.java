package com.blacklenspub.starwarsapp.film;

import com.blacklenspub.starwarsapp.model.Apis;
import com.blacklenspub.starwarsapp.model.Film;
import com.blacklenspub.starwarsapp.model.StarWarsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmPresenter implements FilmContract.FilmPresenter {
    private StarWarsApi starWarsApi;
    private FilmContract.FilmView view;
    private long id;

    public FilmPresenter(FilmContract.FilmView view, long id) {
        this.view = view;
        this.id = id;
        starWarsApi = Apis.getSwarWarsApi();
    }

    @Override
    public void getFilm() {
        view.showLoading();
        starWarsApi.getFilm(id).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                Film film = response.body();
                view.showTitle(film.title);
                view.showReleaseDate(film.releaseDate);
                view.showDirector(film.director);
                view.showCrawl(film.openingCrawl);
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                view.showMessage(t.getMessage());
                view.hideLoading();
            }
        });
    }
}
