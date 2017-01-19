package com.blacklenspub.starwarsapp.film;

import com.blacklenspub.starwarsapp.R;
import com.blacklenspub.starwarsapp.model.Film;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FilmActivity extends MvpLceActivity<LinearLayout, Film, FilmContract.FilmView, FilmPresenter> implements FilmContract
        .FilmView {

    private static final String KEY_FILM_ID = "FILM_ID";

    public static void start(Context context, long episodeId) {
        Intent starter = new Intent(context, FilmActivity.class);
        starter.putExtra(KEY_FILM_ID, episodeId);
        context.startActivity(starter);
    }

    TextView tvReleaseDate;
    TextView tvDirector;
    TextView tvCrawl;

    private long filmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        initLayoutWidgets();
        filmId = getIntent().getLongExtra(KEY_FILM_ID, 0);
        loadData(false);
    }

    private void initLayoutWidgets() {
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        tvDirector = (TextView) findViewById(R.id.tvDirector);
        tvCrawl = (TextView) findViewById(R.id.tvCrawl);
    }

    @NonNull @Override public FilmPresenter createPresenter() {
        return new FilmPresenter();
    }

    @Override protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override public void setData(Film film) {
        setTitle(film.title);
        tvReleaseDate.setText(film.releaseDate);
        tvDirector.setText(film.director);
        tvCrawl.setText(film.openingCrawl);
    }

    @Override public void loadData(boolean pullToRefresh) {
        presenter.getFilm(filmId);
    }

}
