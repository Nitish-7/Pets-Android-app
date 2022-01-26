
package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class PetContracts {
    private PetContracts(){}

    public static final class PetEntry implements BaseColumns{

        public static final String PET_PATH = "pets";
        public static final String PET_CONTENT_AUTHORITY = "com.example.android.pets";
        public static final Uri BASE_URI = Uri.parse("content://com.example.android.pets/"+PET_PATH);

        public static final String TABLE_NAME = "pets";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME= "name";
        public static final String COLUMN_PET_BREED ="breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }

}
