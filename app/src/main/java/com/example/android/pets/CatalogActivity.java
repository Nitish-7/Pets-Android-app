package com.example.android.pets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.PetContracts.PetEntry;
import com.example.android.pets.data.PetViewModel;
import com.example.android.pets.data.Pets;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.os.Build.VERSION_CODES.O;

public class CatalogActivity extends AppCompatActivity implements MyClickListener {

    PetViewModel petViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        displayPetsOnCatalog();

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        displayPetsOnCatalog();

    }

    private void displayPetsOnCatalog() {
//        Cursor cursor=getContentResolver().query(PetEntry.BASE_URI, projection, null, null, null, null);
//        PetCursorAdapter adapter = new PetCursorAdapter(this,cursor);
//
//        ListView lv = findViewById(R.id.pet_list);
//        lv.setEmptyView(findViewById(R.id.imageView));
//
//        lv.setAdapter(adapter);

        petViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(PetViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.petsRv);
        PetsRvAdapter adapter = new PetsRvAdapter(this, petViewModel,this);
        recyclerView.setAdapter(adapter);

        petViewModel.getAllPets().observe(this, new Observer<List<Pets>>() {
            @Override
            public void onChanged(List<Pets> pets) {
                adapter.updatePets(pets);
                if (adapter.getItemCount() == 0)

                    findViewById(R.id.imageView).setVisibility(View.VISIBLE);

                else
                    findViewById(R.id.imageView).setVisibility(View.GONE);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                petViewModel.insert(new Pets("Dodo","Pomoranian","40",1));
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                petViewModel.deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnItemClick(Pets pet) {
        Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
        intent.putExtra("petName",pet.getmPetName());
        intent.putExtra("petBreed",pet.getmPetBreed());
        intent.putExtra("petWeight",pet.getmPetWeight());
        intent.putExtra("petGender",String.valueOf(pet.getmPetGender()));
       startActivity(intent);
    }
}