package com.blacklenspub.starwarsapp.film;

import com.blacklenspub.starwarsapp.model.Apis;
import com.blacklenspub.starwarsapp.model.Film;
import com.blacklenspub.starwarsapp.model.StarWarsApi;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmPresenter extends MvpBasePresenter<FilmContract.FilmView> implements FilmContract.FilmPresenter {

    private StarWarsApi starWarsApi;
    private Call getFilmNetworkCall;

    public FilmPresenter() {
        starWarsApi = Apis.getSwarWarsApi();
    }

    @Override
    public void getFilm(long filmId) {
        showLoadingView();
        requestFilmFromApi(filmId);
    }

    private void requestFilmFromApi(long filmId) {
        getFilmNetworkCall = starWarsApi.getFilm(filmId);
        getFilmNetworkCall.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                showFilmDetailsView(response.body());
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                showErrorView(t);
            }
        });
    }

    private void showLoadingView() {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
    }

    private void showFilmDetailsView(Film film) {
        if (isViewAttached()) {
            getView().setData(film);
            getView().showContent();
        }
    }

    private void showErrorView(Throwable t) {
        if (isViewAttached()) {
            getView().showError(t, false);
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance && !getFilmNetworkCall.isCanceled()) {
            getFilmNetworkCall.cancel();
        }
    }
}
