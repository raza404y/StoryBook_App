package com.blueroom.englishstories;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blueroom.englishstories.Adapters.StoriesAdapter;
import com.blueroom.englishstories.databinding.ActivityStoriesBinding;
import com.blueroom.englishstories.models.CategoriesModel;
import com.blueroom.englishstories.models.StoriesModel;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class StoriesActivity extends AppCompatActivity {

    String truncatedText;
    ActivityStoriesBinding binding;
    FirebaseDatabase database;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        database = FirebaseDatabase.getInstance();

        String name = getIntent().getStringExtra("name");
        String categoryId = getIntent().getStringExtra("id");
        String imageUrl = getIntent().getStringExtra("img");

        Glide.with(StoriesActivity.this)
                        .load(imageUrl)
                                .placeholder(R.drawable.img)
                                        .into(binding.categoryImage);

        binding.categoryNameTv.setText(name);


        binding.categoryNameTv.setOnClickListener(view -> {

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            onBackPressed();

        });



        ArrayList<StoriesModel> nameList = new ArrayList<>();
        StoriesAdapter adapter = new StoriesAdapter(nameList, StoriesActivity.this);
        binding.storiesNameRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(StoriesActivity.this);
        binding.storiesNameRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("stories").child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    nameList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        StoriesModel model = dataSnapshot.getValue(StoriesModel.class);
                        assert model != null;
                        model.setStoryId(dataSnapshot.getKey());
                        truncatedText = truncateText(model.getStoryText(), 70); // Adjust the length as needed
                        model.setShortenedStoryText(truncatedText);
                        nameList.add(model);
                    }

                    adapter.notifyDataSetChanged();

                    // Check if nameList is empty after the loop
                    if (nameList.isEmpty()) {
                        binding.progressBar2.setVisibility(View.GONE);
                        binding.noStoryYet.setVisibility(View.VISIBLE);
                    } else {
                        binding.noStoryYet.setVisibility(View.GONE);
                        binding.progressBar2.setVisibility(View.GONE);
                    }
                } else {
                    // Handle the case when there is no data
                    binding.progressBar2.setVisibility(View.GONE);
                    binding.noStoryYet.setVisibility(View.VISIBLE);
                }
                Collections.shuffle(nameList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StoriesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private String truncateText(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength);
        } else {
            return text;
        }
    }
}