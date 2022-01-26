package com.example.android.pets.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.pets.data.PetContracts.PetEntry;

import java.util.List;

@Dao
public interface PetsDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Pets pet);

    @Delete
    void delete(Pets pet);

    @Query("DELETE FROM " + PetEntry.TABLE_NAME)
    void deleteAll();

    @Query("SELECT * FROM " + PetEntry.TABLE_NAME + " ORDER BY " + PetEntry.COLUMN_PET_NAME + " ASC")
    LiveData<List<Pets>> getAlphabetizedWords();
}
