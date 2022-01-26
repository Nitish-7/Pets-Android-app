package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContracts.PetEntry;

public class PetDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shelter.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_SCHEMA_QUERY="CREATE TABLE "+PetEntry.TABLE_NAME +
            " (" + PetEntry._ID +" INTEGER PRIMARY KEY," + PetEntry.COLUMN_PET_NAME +
            " TEXT," + PetEntry.COLUMN_PET_BREED + " TEXT," + PetEntry.COLUMN_PET_WEIGHT +
            " TEXT," + PetEntry.COLUMN_PET_GENDER + " INTEGER);";

    public PetDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_SCHEMA_QUERY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {

    }


}
