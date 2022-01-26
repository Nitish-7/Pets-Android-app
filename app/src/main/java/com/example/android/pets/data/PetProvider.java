package com.example.android.pets.data;

import com.example.android.pets.EditorActivity;
import com.example.android.pets.data.PetContracts.PetEntry;
import com.example.android.pets.data.PetDbHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.CursorAdapter;

import java.util.jar.JarEntry;

/**
 * {@link ContentProvider} for Pets app.
 */

public class PetProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = PetProvider.class.getSimpleName();
    private static final int PETS = 100;
    private static final int PETS_ID = 101;

    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    //Initialize the provider and the database helper object.
    static {
        sUriMatcher.addURI(PetEntry.PET_CONTENT_AUTHORITY,PetEntry.PET_PATH,PETS);
        sUriMatcher.addURI(PetEntry.PET_CONTENT_AUTHORITY,PetEntry.PET_PATH+"/#",PETS_ID);
    }
    private PetDbHelper mDbHelper;
    @Override
    public boolean onCreate() {
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        mDbHelper = new PetDbHelper(getContext());

        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        switch (sUriMatcher.match(uri))
        {
            case PETS:
                cursor = db.query(PetEntry.TABLE_NAME,projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            case  PETS_ID:
                selection = PetEntry._ID+"=?";
                selectionArgs= new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(PetEntry.TABLE_NAME,projection,selection,selectionArgs,
                        null,null,null);
                break;
            default :
                throw new IllegalArgumentException("invalid query"+uri);

        }
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}