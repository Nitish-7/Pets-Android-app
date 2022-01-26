package com.example.android.pets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.pets.data.PetContracts;
import com.example.android.pets.data.PetViewModel;
import com.example.android.pets.data.Pets;

import java.util.ArrayList;
import java.util.List;

public class PetsRvAdapter extends RecyclerView.Adapter<PetsRvAdapter.PetViewHolder> {

    Context ctx;
    PetViewModel mPetViewModel;
    ArrayList<Pets> allPets = new ArrayList<>();
    MyClickListener mMyClickListener;

    public PetsRvAdapter(Context context, PetViewModel petViewModel,MyClickListener myClickListener) {
        ctx = context;
        mPetViewModel = petViewModel;
        mMyClickListener=myClickListener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PetViewHolder petViewHolder= new PetViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_pet_view, parent, false));

        return petViewHolder;
    }

    @Override
    public int getItemCount() {
        return allPets.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.getNameTv().setText(allPets.get(position).getmPetName());
        holder.getBreedTv().setText(allPets.get(position).getmPetBreed());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyClickListener.OnItemClick(allPets.get(position));
            }
        });
    }

    public void updatePets(List<Pets> pets) {
        allPets.clear();
        allPets.addAll(pets);

        notifyDataSetChanged();
    }

    class PetViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv;
        private TextView breedTv;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.findViewById(R.id.delete_Iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPetViewModel.delete(allPets.get(getAdapterPosition()));
                }
            });
            nameTv = itemView.findViewById(R.id.tv_pet_name);
            breedTv = itemView.findViewById(R.id.tv_pet_breed);

        }

        public TextView getBreedTv() {
            return breedTv;
        }

        public TextView getNameTv() {
            return nameTv;
        }
    }
}
interface MyClickListener{
    void OnItemClick(Pets pet);
}
