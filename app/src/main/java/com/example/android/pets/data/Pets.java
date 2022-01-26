package com.example.android.pets.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.android.pets.data.PetContracts.PetEntry;

@Entity(tableName = PetEntry.TABLE_NAME)
public class Pets {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = PetEntry.COLUMN_PET_NAME)
     String mPetName;

    @ColumnInfo(name = PetEntry.COLUMN_PET_BREED)
     String mPetBreed;

    @NonNull
    @ColumnInfo(name = PetEntry.COLUMN_PET_WEIGHT)
     String mPetWeight;

    @NonNull
    @ColumnInfo(name = PetEntry.COLUMN_PET_GENDER)
     int mPetGender;

    public Pets(@NonNull String petName, String petBreed, @NonNull String petWeight, @NonNull int petGender) {
        this.mPetName = petName;
        this.mPetBreed = petBreed;
        this.mPetWeight = petWeight;
        this.mPetGender = petGender;
        //this.mId=0;
    }

    @NonNull
    public String getmPetName() {
        return this.mPetName;
    }

    public String getmPetBreed() {
        return this.mPetBreed;
    }

    @NonNull
    public String getmPetWeight() {
        return this.mPetWeight;
    }

    public int getmPetGender() {
        return this.mPetGender;
    }
}
