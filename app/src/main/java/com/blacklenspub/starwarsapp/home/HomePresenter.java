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
    public void getAllFilms(boolean pullToRefrech) {
        showLoadingView(pullToRefrech);
        requestAllFilmesFromApi(pullToRefrech);
    }

    private void requestAllFilmesFromApi(final boolean pullToRefresh) {
        getAllFilmsNetworkCall = starWarsApi.getAllFilms();
        getAllFilmsNetworkCall.enqueue(new Callback<FilmResponse>() {

            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                    showAllFilmsView(response.body().results);
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                showErrorView(t, pullToRefresh);
            }
        });
    }

    private void showLoadingView(boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }
    }

    private void showAllFilmsView(List<Film> films) {
        if (isViewAttached()) {
            getView().setData(films);
            getView().showContent();
        }
    }

    private void showErrorView(Throwable t, boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showError(t, pullToRefresh);
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