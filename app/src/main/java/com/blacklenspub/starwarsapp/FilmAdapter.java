package com.blacklenspub.starwarsapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {

    private List<Film> films;
    private OnFilmClickListener onFilmClickListener;

    public FilmAdapter(List<Film> films, OnFilmClickListener onFilmClickListener) {
        this.films = films;
        this.onFilmClickListener = onFilmClickListener;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
        notifyDataSetChanged();
    }

    @Override
    public FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FilmHolder(view, onFilmClickListener);
    }

    @Override
    public void onBindViewHolder(FilmHolder holder, int position) {
        Film item = films.get(position);
        holder.film = item;
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        if (films == null) {
            return 0;
        } else {
            return films.size();
        }
    }

    static class FilmHolder extends RecyclerView.ViewHolder {

        Film film;
        TextView title;

        FilmHolder(View itemView, final OnFilmClickListener onFilmClickListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFilmClickListener.onFilmClick(film);
                }
            });
        }
    }

    interface OnFilmClickListener {
        void onFilmClick(Film film);
    }
}
