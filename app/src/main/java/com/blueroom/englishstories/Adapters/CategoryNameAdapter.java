package com.blueroom.englishstories.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blueroom.englishstories.R;
import com.blueroom.englishstories.Read_Story_Activity;
import com.blueroom.englishstories.databinding.CategotyNamesRvBinding;
import com.blueroom.englishstories.models.CategoryNames;

import java.util.ArrayList;

public class CategoryNameAdapter extends RecyclerView.Adapter<CategoryNameAdapter.ViewHolder>{

    ArrayList<CategoryNames> namesList;
    Context context;

    public CategoryNameAdapter(ArrayList<CategoryNames> namesList, Context context) {
        this.namesList = namesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoty_names_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryNameAdapter.ViewHolder holder, int position) {
        CategoryNames model = namesList.get(position);
        holder.binding.categoryTitle.setText(model.getName());


        holder.binding.like.setOnClickListener(view -> {
            holder.binding.like.setImageResource(R.drawable.heart_filled);
        });

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, Read_Story_Activity.class);
            intent.putExtra("name",model.getName());
            context.startActivity(intent);
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        });

    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CategotyNamesRvBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategotyNamesRvBinding.bind(itemView);
        }
    }


}
