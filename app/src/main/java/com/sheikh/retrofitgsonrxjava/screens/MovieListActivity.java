package com.sheikh.retrofitgsonrxjava.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.sheikh.retrofitgsonrxjava.R;
import com.sheikh.retrofitgsonrxjava.adapters.MoviesAdapter;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMovies;

    private MoviesAdapter adapter;
    private MoviesViewModel viewModel;

    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewMovies = findViewById(R.id.rv_movies);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this,getWindowWidth()));
        adapter = new MoviesAdapter();
        recyclerViewMovies.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        viewModel.getMovies().observe(this, movies -> adapter.addAllMovies(movies));

        viewModel.getErrors().observe(this, throwable -> {
            if (throwable != null) {
                Toast.makeText(MovieListActivity.this, "Error", Toast.LENGTH_SHORT).show();
                viewModel.clearErrors();
            }
        });

        viewModel.loadData(String.valueOf(page));
        whenReachEnd();
    }

    private void whenReachEnd() {
        adapter.setReachEnd(() -> {
            page++;
            viewModel.loadData(String.valueOf(page));
        });
    }

    private int getWindowWidth() {
        DisplayMetrics displayManager = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayManager);
        int windowWidth = (int) (displayManager.widthPixels / displayManager.density);
        return windowWidth / 185 > 2 ? windowWidth / 185 : 2;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}