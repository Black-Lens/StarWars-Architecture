package com.blacklenspub.starwarsapp.home;

import com.blacklenspub.starwarsapp.model.Apis;
import com.blacklenspub.starwarsapp.model.Film;
import com.blacklenspub.starwarsapp.model.FilmResponse;
import com.blacklenspub.starwarsapp.model.StarWarsApi;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter extends MvpBasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter {

    private StarWarsApi starWarsApi;
    private Call getAllFilmsNetworkCall;

    public HomePresenter() {
        starWarsApi = Apis.getSwarWarsApi();
    }

    @Override
    public void getAllFilms() {
        showLoadingView();
        requestAllFilmesFromApi();
    }

    private void requestAllFilmesFromApi() {
        getAllFilmsNetworkCall = starWarsApi.getAllFilms();
        getAllFilmsNetworkCall.enqueue(new Callback<FilmResponse>() {

            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                hideLoadingView();
                showAllFilmsView(response.body().results);
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                hideLoadingView();
                showErrorView(t.getMessage());
            }
        });
    }

    private void showLoadingView() {
        if (isViewAttached()) {
            getView().showLoading();
        }
    }

    private void hideLoadingView() {
        if (isViewAttached()) {
            getView().hideLoading();
        }
    }

    private void showAllFilmsView(List<Film> films) {
        if (isViewAttached()) {
            getView().showAllFilms(films);
        }
    }

    private void showErrorView(String errorMessage) {
        if (isViewAttached()) {
            getView().showErrorMessage(errorMessage);
        }
    }

    @Override
    public void onFilmItemClicked(Film film) {
        if (isViewAttached()) {
            getView().navigateToFilmPage(film);
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance && !getAllFilmsNetworkCall.isCanceled()) {
            getAllFilmsNetworkCall.cancel();
        }
    }
}