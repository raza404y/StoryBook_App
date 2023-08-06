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
import com.blueroom.englishstories.databinding.StoriesNamesRvBinding;
import com.blueroom.englishstories.models.StoriesName;
import java.util.ArrayList;

public class StoriesNameAdapter extends RecyclerView.Adapter<StoriesNameAdapter.ViewHolder>{

    ArrayList<StoriesName> namesList;
    Context context;

    public StoriesNameAdapter(ArrayList<StoriesName> namesList, Context context) {
        this.namesList = namesList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoriesNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stories_names_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesNameAdapter.ViewHolder holder, int position) {
        StoriesName model = namesList.get(position);
        holder.binding.storyTitle.setText(model.getStoryTitle());
        holder.binding.storyText.setText(model.getStoryText());


        holder.binding.like.setOnClickListener(view -> {
            holder.binding.like.setImageResource(R.drawable.heart_filled);
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
        StoriesNamesRvBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StoriesNamesRvBinding.bind(itemView);
        }
    }


}
