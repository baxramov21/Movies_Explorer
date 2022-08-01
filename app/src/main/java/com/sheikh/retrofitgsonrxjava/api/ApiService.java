package com.sheikh.retrofitgsonrxjava.api;

import com.sheikh.retrofitgsonrxjava.pojos.MoviesResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // https://api.themoviedb.org/3/discover/movie?api_key=c04e9d2cd0bd48b727a09adffca7e5ef&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&vote_count.gte=1000&vote_average.gte=7&with_watch_monetization_types=flatrate
    @GET("movie?api_key=c04e9d2cd0bd48b727a09adffca7e5ef&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page&vote_count.gte=1000&vote_average.gte=7&with_watch_monetization_types=flatrate")
    Observable<MoviesResult> getMoviesService(@Query("page") String page);
}
