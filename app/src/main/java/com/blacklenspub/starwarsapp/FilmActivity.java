package com.blacklenspub.starwarsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmActivity extends AppCompatActivity {

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

    StarWarsApi starWarsApi;
    long episodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        tvDirector = (TextView) findViewById(R.id.tvDirector);
        tvCrawl = (TextView) findViewById(R.id.tvCrawl);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        episodeId = getIntent().getLongExtra(KEY_FILM_ID, 0);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://swapi.co/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        starWarsApi = retrofit.create(StarWarsApi.class);

        progressBar.setVisibility(View.VISIBLE);
        starWarsApi.getFilm(episodeId).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                Film film = response.body();
                setTitle(film.title);
                tvReleaseDate.setText(film.releaseDate);
                tvDirector.setText(film.director);
                tvCrawl.setText(film.openingCrawl);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Toast.makeText(FilmActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
