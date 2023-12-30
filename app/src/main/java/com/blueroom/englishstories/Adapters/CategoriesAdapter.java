package com.blueroom.englishstories.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blueroom.englishstories.R;
import com.blueroom.englishstories.StoriesActivity;
import com.blueroom.englishstories.databinding.CategoriesRvLayoutBinding;
import com.blueroom.englishstories.models.CategoriesModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    ArrayList<CategoriesModel> categoryList;
    Context context;

    public CategoriesAdapter(ArrayList<CategoriesModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesModel model = categoryList.get(position);
        holder.binding.categoryName.setText(model.getCategoryName());

        Glide.with(context)
                .load(model.getImageUrl())
                .placeholder(R.drawable.img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.categoryImage);


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, StoriesActivity.class);
            intent.putExtra("name", model.getCategoryName());
            intent.putExtra("id", model.getCategoryId());
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });


    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CategoriesRvLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CategoriesRvLayoutBinding.bind(itemView);
        }
    }

}
