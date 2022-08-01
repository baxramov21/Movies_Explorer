package com.sheikh.retrofitgsonrxjava.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sheikh.retrofitgsonrxjava.pojos.Movie;

import java.util.List;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movies_table")
    LiveData<List<Movie>> getAllMovies();

    @Insert
    void addMovies(List<Movie> movies);

    @Query("DELETE FROM movies_table")
    void deleteAllMovies();
}