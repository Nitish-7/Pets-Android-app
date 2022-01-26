package com.example.android.pets.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

    @Database(entities = {Pets.class}, version = 1, exportSchema = false)
    public abstract class PetRoomDatabase extends RoomDatabase {

        public abstract PetsDao PetsDao();

        private static volatile PetRoomDatabase INSTANCE;
        private static final int NUMBER_OF_THREADS = 4;
        static final ExecutorService databaseWriteExecutor =
                Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        static PetRoomDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (PetRoomDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                PetRoomDatabase.class, "Pets_database")
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }

