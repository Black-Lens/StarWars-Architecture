package com.blacklenspub.starwarsapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_film.view.*

class FilmAdapter(var films: List<Film>, val onFilmClickListener: OnFilmClickListener) : RecyclerView.Adapter<FilmAdapter.FilmHolder>() {

    override fun getItemCount() = films.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FilmAdapter.FilmHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_film, parent, false)
        return FilmHolder(view, onFilmClickListener)
    }

    override fun onBindViewHolder(holder: FilmAdapter.FilmHolder, position: Int) {
        val item = films[position]
        holder.film = item
        holder.title.text = item.title
    }

    interface OnFilmClickListener {

        fun onFilmClicked(film: Film)
    }

    class FilmHolder(itemView: View, onFilmClickListener: OnFilmClickListener) : RecyclerView.ViewHolder(itemView) {

        lateinit var film: Film

        val title: TextView = itemView.tvTitle

        init {
            itemView.setOnClickListener {
                onFilmClickListener.onFilmClicked(film)
            }
        }
    }
}
