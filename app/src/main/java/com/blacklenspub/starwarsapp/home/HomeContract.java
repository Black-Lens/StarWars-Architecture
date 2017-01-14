package com.blacklenspub.starwarsapp.home;

import com.blacklenspub.starwarsapp.model.Film;

import java.util.List;

public interface HomeContract {
    interface HomeView {
        void showLoading();

        void hideLoading();

        void showTitle(String title);

        void showMessage(String message);

        void showAllFilms(List<Film> films);

        void navigateToFilmPage(Film film);
    }

    interface HomePresenter {
        void getAllFilms();

        void onFilmItemClicked(Film film);
    }
}
