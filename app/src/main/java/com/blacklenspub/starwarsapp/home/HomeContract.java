package com.blacklenspub.starwarsapp.home;

import com.blacklenspub.starwarsapp.model.Film;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface HomeContract {

    interface HomeView extends MvpView {

        void showLoading();

        void hideLoading();

        void showErrorMessage(String message);

        void showAllFilms(List<Film> films);

        void navigateToFilmPage(Film film);
    }

    interface HomePresenter {

        void getAllFilms();

        void onFilmItemClicked(Film film);
    }
}
