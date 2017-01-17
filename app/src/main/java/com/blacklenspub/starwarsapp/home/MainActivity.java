package com.blacklenspub.starwarsapp.home;

import com.blacklenspub.starwarsapp.R;
import com.blacklenspub.starwarsapp.film.FilmActivity;
import com.blacklenspub.starwarsapp.model.Film;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

// TODO : rename this to HomeActivity
public class MainActivity extends MvpActivity<HomeContract.HomeView, HomePresenter> implements HomeContract.HomeView {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FilmAdapter filmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("All Star Wars Films");
        initLayoutWidgets();
        presenter.getAllFilms();
    }

    private void initLayoutWidgets() {
        recyclerView = (RecyclerView) findViewById(R.id.rvFilms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filmAdapter = new FilmAdapter(null, new FilmAdapter.OnFilmClickListener() {
            @Override
            public void onFilmClick(Film film) {
                presenter.onFilmItemClicked(film);
            }
        });
        recyclerView.setAdapter(filmAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getAllFilms();
            }
        });
    }

    @NonNull @Override public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAllFilms(List<Film> films) {
        filmAdapter.setFilms(films);
    }

    @Override
    public void navigateToFilmPage(Film film) {
        FilmActivity.start(this, film.episodeId);
    }
}