package com.blacklenspub.starwarsapp.home;

import com.blacklenspub.starwarsapp.R;
import com.blacklenspub.starwarsapp.film.FilmActivity;
import com.blacklenspub.starwarsapp.model.Film;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

// TODO : rename this to HomeActivity
public class MainActivity extends MvpLceActivity<SwipeRefreshLayout, List<Film>, HomeContract.HomeView, HomePresenter> implements
        HomeContract.HomeView {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FilmAdapter filmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("All Star Wars Films");
        initLayoutWidgets();
        loadData(false);
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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.contentView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(true);
            }
        });
    }

    @NonNull @Override public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override public void loadData(boolean pullToRefresh) {
        presenter.getAllFilms(pullToRefresh);
    }

    @Override public void setData(List<Film> data) {
        filmAdapter.setFilms(data);
    }

    @Override public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override public void showError(Throwable t, boolean pullToRefresh) {
        super.showError(t, pullToRefresh);
        contentView.setRefreshing(false);
    }

    @Override
    public void navigateToFilmPage(Film film) {
        FilmActivity.start(this, film.episodeId);
    }
}