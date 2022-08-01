package com.sheikh.retrofitgsonrxjava.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sheikh.retrofitgsonrxjava.pojos.Movie;

@Database(entities = {Movie.class},version = 1, exportSchema = false)
public abstract class Movies_DB extends RoomDatabase {
    public static final String MOVIES_DB = "MOVIES_DB";
    private static Movies_DB movies_db;
    private static final Object LOCK = new Object();

    public static Movies_DB getInstance(Context context) {
        synchronized (LOCK) {
            if (movies_db == null) {
                movies_db = Room.databaseBuilder(context, Movies_DB.class, MOVIES_DB).build();
            }
        }
        return movies_db;
    }

    public abstract MoviesDao getDao();


}