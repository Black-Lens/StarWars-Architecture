package com.blacklenspub.starwarsapp.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.blacklenspub.starwarsapp.R;
import com.blacklenspub.starwarsapp.film.FilmActivity;
import com.blacklenspub.starwarsapp.model.Film;

import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeContract.HomeView {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FilmAdapter filmAdapter;
    HomeContract.HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rvFilms);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);

        filmAdapter = new FilmAdapter(null, new FilmAdapter.OnFilmClickListener() {
            @Override
            public void onFilmClick(Film film) {
                presenter.onFilmItemClicked(film);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filmAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getAllFilms();
            }
        });

        presenter = new HomePresenter(this);
        presenter.getAllFilms();
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
    public void showTitle(String title) {
        setTitle(title);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAllFilms(List<Film> films) {
        filmAdapter.setFilms(films);
    }

    @Override
    public void navigateToFilmPage(Film film) {
        FilmActivity.start(MainActivity.this, film.episodeId);
    }
}
