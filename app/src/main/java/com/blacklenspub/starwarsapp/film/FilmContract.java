package com.blacklenspub.starwarsapp.film;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface FilmContract {

    interface FilmView extends MvpView {

        void showLoading();

        void hideLoading();

        void showErrorMessage(String message);

        void showFilmTitle(String title);

        void showReleaseDate(String dateString);

        void showDirector(String director);

        void showCrawl(String crawl);
    }

    interface FilmPresenter {

        void getFilm(long filmId);
    }
}
