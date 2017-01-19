package com.blacklenspub.starwarsapp.home;

import com.blacklenspub.starwarsapp.model.Film;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

public interface HomeContract {

    interface HomeView extends MvpLceView<List<Film>> {

        void navigateToFilmPage(Film film);
    }

    interface HomePresenter {

        void getAllFilms(boolean pullToRefresh);

        void onFilmItemClicked(Film film);
    }
}
