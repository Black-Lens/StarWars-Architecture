package com.blacklenspub.starwarsapp.film;

import com.blacklenspub.starwarsapp.R;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FilmActivity extends MvpActivity<FilmContract.FilmView, FilmPresenter> implements FilmContract.FilmView {

    private static final String KEY_FILM_ID = "FILM_ID";

    public static void start(Context context, long episodeId) {
        Intent starter = new Intent(context, FilmActivity.class);
        starter.putExtra(KEY_FILM_ID, episodeId);
        context.startActivity(starter);
    }

    TextView tvReleaseDate;
    TextView tvDirector;
    TextView tvCrawl;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        initLayoutWidgets();
        long filmId = getIntent().getLongExtra(KEY_FILM_ID, 0);
        presenter.getFilm(filmId);
    }

    private void initLayoutWidgets() {
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        tvDirector = (TextView) findViewById(R.id.tvDirector);
        tvCrawl = (TextView) findViewById(R.id.tvCrawl);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @NonNull @Override public FilmPresenter createPresenter() {
        return new FilmPresenter();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFilmTitle(String title) {
        setTitle(title);
    }

    @Override
    public void showReleaseDate(String dateString) {
        tvReleaseDate.setText(dateString);
    }

    @Override
    public void showDirector(String director) {
        tvDirector.setText(director);
    }

    @Override
    public void showCrawl(String crawl) {
        tvCrawl.setText(crawl);
    }
}
