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
import com.blueroom.englishstories.databinding.StoriesRvLayoutBinding;
import com.blueroom.englishstories.models.StoriesModel;
import java.util.ArrayList;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder>{

    ArrayList<StoriesModel> namesList;
    Context context;
    boolean isLiked;

    public StoriesAdapter(ArrayList<StoriesModel> namesList, Context context) {
        this.namesList = namesList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stories__rv_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesAdapter.ViewHolder holder, int position) {
        StoriesModel model = namesList.get(position);
        holder.binding.storyTitle.setText(model.getStoryTitle());
        holder.binding.storyText.setText(model.getStoryText());


        holder.binding.like.setOnClickListener(view -> {

            isLiked = !isLiked;
            if (isLiked) {
                holder.binding.like.setImageResource(R.drawable.heart_filled);
            }else {
                holder.binding.like.setImageResource(R.drawable.heart_outline);

            }
            });

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, Read_Story_Activity.class);
            intent.putExtra("name",model.getStoryTitle());
            context.startActivity(intent);
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        });

    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        StoriesRvLayoutBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StoriesRvLayoutBinding.bind(itemView);
        }
    }


}
