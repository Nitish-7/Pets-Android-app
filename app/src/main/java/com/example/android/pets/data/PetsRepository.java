package com.example.android.pets.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PetsRepository {

    private PetsDao mPetsDao;
    private LiveData<List<Pets>> mAllPets;

    public PetsRepository(Application application) {
        PetRoomDatabase db = PetRoomDatabase.getDatabase(application);
        mPetsDao=db.PetsDao();
        mAllPets=mPetsDao.getAlphabetizedWords();
    }

    void insert(Pets pet) {
        PetRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPetsDao.insert(pet);
        });
    }
    void delete(Pets pet) {
        PetRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPetsDao.delete(pet);
        });
    }
    void deleteAll() {
        PetRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPetsDao.deleteAll();
        });
    }
    LiveData<List<Pets>> getAllPets(){
        return mAllPets;
    }

}
