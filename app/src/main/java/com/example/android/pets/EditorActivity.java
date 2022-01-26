package com.example.android.pets;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.data.PetContracts.PetEntry;
import com.example.android.pets.data.PetViewModel;
import com.example.android.pets.data.Pets;

public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the pet's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mBreedEditText;

    /**
     * EditText field to enter the pet's weight
     */
    private EditText mWeightEditText;

    /**
     * EditText field to enter the pet's gender
     */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;

    //private ContentValues petRowValues = new ContentValues();

    //PetDbHelper mDbHelper=new PetDbHelper(this);

    PetViewModel petViewModel;
    Pets clickedPet;

    private String petGenderChecker = "-1";

    private boolean isEnteredAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        petViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(PetViewModel.class);

        Intent intent = getIntent();
        petGenderChecker = intent.getStringExtra("petGender");
        if (intent.hasExtra("petName")) {
            clickedPet = new Pets(intent.getStringExtra("petName"),
                    intent.getStringExtra("petBreed"), intent.getStringExtra("petWeight"), Integer.valueOf(petGenderChecker));
        }

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);

        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);

        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);

        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();

        if (clickedPet != null) {
            setTitle("Edit Pet " + intent.getStringExtra("petName"));
            editPetActivity();
        }


    }

    private void editPetActivity() {

        mNameEditText.setText((clickedPet).getmPetName());
        mBreedEditText.setText((clickedPet).getmPetBreed());
        mWeightEditText.setText((clickedPet).getmPetWeight());
        int petGender = Integer.valueOf(petGenderChecker);
        switch (petGender) {
            case PetEntry.GENDER_MALE:
                mGenderSpinner.setSelection(petGender);
                break;
            case PetEntry.GENDER_FEMALE:
                mGenderSpinner.setSelection(petGender);
                break;
            case PetEntry.GENDER_UNKNOWN:
                mGenderSpinner.setSelection(petGender);
                break;
        }
    }

    public void insertPet() {

        String petName = mNameEditText.getText().toString().trim();
        String petBreed = mBreedEditText.getText().toString().trim();
        String petWeight = mWeightEditText.getText().toString().trim();
        int petGender = mGender;

        if (petName.isEmpty() || petGender == 0 || petWeight.isEmpty()) {
            isEnteredAll = false;
        } else
            isEnteredAll = true;

        if (clickedPet != null && isEnteredAll) {
            petViewModel.delete((clickedPet));
            petViewModel.insert(new Pets(petName, petBreed, petWeight, petGender));
            Toast.makeText(getApplicationContext(), "Pet Updated", Toast.LENGTH_SHORT).show();

        } else if (clickedPet == null && isEnteredAll) {
            petViewModel.insert(new Pets(petName, petBreed, petWeight, petGender));
            Toast.makeText(getApplicationContext(), "Pet added", Toast.LENGTH_SHORT).show();
        }


//        petRowValues.put(PetEntry.COLUMN_PET_NAME,petName);
//        petRowValues.put(PetEntry.COLUMN_PET_BREED,petBreed);
//        petRowValues.put(PetEntry.COLUMN_PET_WEIGHT,petWeight);
//        petRowValues.put(PetEntry.COLUMN_PET_GENDER,petGender);

        //SQLiteDatabase db=mDbHelper.getWritableDatabase();
//        long insertResult=db.insert(PetEntry.TABLE_NAME,null,petRowValues);
//        if(insertResult==-1)
//            Toast.makeText(this,"pet not inseted ",Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this,"pet inseted successfully ",Toast.LENGTH_SHORT).show();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0;// Unknown
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        if (clickedPet == null)
            menu.findItem(R.id.action_delete).setVisible(false);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // insert new row for new pet
                insertPet();
                if (isEnteredAll)
                    finish();
                else
                    Toast.makeText(getApplicationContext(), "Enter all remaining fields", Toast.LENGTH_SHORT).show();
                return true;

            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                if (clickedPet != null)
                    petViewModel.delete((clickedPet));
                finish();
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}