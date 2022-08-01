package com.sheikh.retrofitgsonrxjava.screens.employees;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sheikh.retrofitgsonrxjava.api.ApiFactory;
import com.sheikh.retrofitgsonrxjava.api.ApiService;
import com.sheikh.retrofitgsonrxjava.db.Movies_DB;
import com.sheikh.retrofitgsonrxjava.pojos.Movie;
import com.sheikh.retrofitgsonrxjava.pojos.MoviesResult;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel extends AndroidViewModel {

    private Movies_DB database;
    private LiveData<List<Movie>> movies;
    private MutableLiveData<Throwable> errors;
    private CompositeDisposable compositeDisposable;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        database = Movies_DB.getInstance(application);
        movies = database.getDao().getAllMovies();
        errors = new MutableLiveData<>();
    }

    public void clearErrors() {
        errors.setValue(null);
    }

    private void setMovies(List<Movie> movies) {
        new SetMoviesTask().execute(movies);
    }

    private void deleteAllMovies() {
        new DeleteAllMovies().execute();
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    class SetMoviesTask extends AsyncTask<List<Movie>,Void,Void> {

        @Override
        protected Void doInBackground(List<Movie>... lists) {
            if (lists != null || lists.length > 0 ) {
                database.getDao().addMovies(lists[0]);
            }
            return null;
        }
    }

    class DeleteAllMovies extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.getDao().deleteAllMovies();
            return null;
        }
    }

    public void loadData(String page) {
        compositeDisposable = new CompositeDisposable();
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        Disposable disposable = apiService.getMoviesService(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesResult>() {
                    @Override
                    public void accept(MoviesResult moviesResult) throws Exception {
                        deleteAllMovies();
                        setMovies(moviesResult.getMovies());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
