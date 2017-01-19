package com.blacklenspub.starwarsapp.film;

import com.blacklenspub.starwarsapp.model.Film;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

public interface FilmContract {

    // TODO : Do you need model for View layer?
    interface FilmView extends MvpLceView<Film> {
    }

    interface FilmPresenter {

        void getFilm(long filmId);
    }
}
