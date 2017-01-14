package com.blacklenspub.starwarsapp.film;

public interface FilmContract {
    interface FilmView {
        void showLoading();

        void hideLoading();

        void showMessage(String message);

        void showTitle(String title);

        void showReleaseDate(String dateString);

        void showDirector(String director);

        void showCrawl(String crawl);
    }

    interface FilmPresenter {
        void getFilm();
    }
}
