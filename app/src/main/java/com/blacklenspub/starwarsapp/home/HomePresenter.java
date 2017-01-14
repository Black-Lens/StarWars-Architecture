package com.blacklenspub.starwarsapp.home;

import com.blacklenspub.starwarsapp.model.Apis;
import com.blacklenspub.starwarsapp.model.Film;
import com.blacklenspub.starwarsapp.model.FilmResponse;
import com.blacklenspub.starwarsapp.model.StarWarsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.HomePresenter {
    private HomeContract.HomeView view;
    private StarWarsApi starWarsApi;

    public HomePresenter(HomeContract.HomeView view) {
        this.view = view;
        starWarsApi = Apis.getSwarWarsApi();
        view.showTitle("All Star Wars Films");
    }

    @Override
    public void getAllFilms() {
        view.showLoading();
        starWarsApi.getAllFilms().enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                view.showAllFilms(response.body().results);
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                view.showMessage(t.getMessage());
                view.hideLoading();
            }
        });
    }

    @Override
    public void onFilmItemClicked(Film film) {
        view.navigateToFilmPage(film);
    }
}
