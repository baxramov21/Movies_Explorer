package com.sheikh.retrofitgsonrxjava.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sheikh.retrofitgsonrxjava.R;
import com.sheikh.retrofitgsonrxjava.pojos.Movie;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_IMAGE_SIZE = "w185";
    private List<Movie> movieList;
    private ReachEnd reachEnd;

    public MoviesAdapter() {
        movieList = new ArrayList<>();
    }

    public void setReachEnd(ReachEnd reachEnd) {
        this.reachEnd = reachEnd;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void addAllMovies(List<Movie> movies) {
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public interface ReachEnd {
        void onReachEnd();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View resultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MoviesViewHolder(resultView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie currentMovie = movieList.get(position);
        holder.tv_movieName.setText(currentMovie.getTitle());
        Picasso.get().load(BASE_IMAGE_URL + SMALL_IMAGE_SIZE + currentMovie.getPosterPath()).placeholder(R.drawable.hacker).into(holder.imgv_movie_img);
        Log.e("path",currentMovie.getPosterPath());
        if (position == movieList.size() - 4 && movieList.size() > 20) {
            reachEnd.onReachEnd();
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {  // <---

        private final TextView tv_movieName; // <---
        private final ImageView imgv_movie_img;  // <---

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_movieName = itemView.findViewById(R.id.textViewMovieName);
            imgv_movie_img = itemView.findViewById(R.id.imageViewMovieImage);

        }
    }
}
