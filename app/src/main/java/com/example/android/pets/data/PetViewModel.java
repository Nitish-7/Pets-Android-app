package com.example.android.pets.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PetViewModel extends AndroidViewModel {

    PetsRepository petsRepository;
    LiveData<List<Pets>> mAllPets;


    public PetViewModel(@NonNull Application application) {
        super(application);
        petsRepository=new PetsRepository(application);
        mAllPets=petsRepository.getAllPets();

    }
    public void insert(Pets pet)
    {
        petsRepository.insert(pet);
    }
    public void delete(Pets pet)
    {
        petsRepository.delete(pet);
    }
    public void deleteAllPets()
    {
        petsRepository.deleteAll();
    }
    public void update(Pets before,Pets after)
    {
        petsRepository.delete(before);
        petsRepository.insert(after);
    }

    public LiveData<List<Pets>> getAllPets() {
        return mAllPets;
    }
}
