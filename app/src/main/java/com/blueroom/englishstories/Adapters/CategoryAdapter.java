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
import com.blueroom.englishstories.StoriesTitle_Activity;
import com.blueroom.englishstories.databinding.CategoriesRvLayoutBinding;
import com.blueroom.englishstories.models.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    ArrayList<CategoryModel> categoryList;
    Context context;

    public CategoryAdapter(ArrayList<CategoryModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_rv_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel model =  categoryList.get(position);
        holder.binding.categoryName.setText(model.getCategoryName());
        holder.binding.categoryImage.setImageResource(model.getCategoryImage());


        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, StoriesTitle_Activity.class);
            intent.putExtra("name",model.getCategoryName());
            intent.putExtra("uri",model.getCategoryImage());
            context.startActivity(intent);
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


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
