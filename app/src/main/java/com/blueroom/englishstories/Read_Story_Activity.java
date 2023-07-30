package com.blueroom.englishstories;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.blueroom.englishstories.databinding.ActivityReadStroyBinding;

public class Read_Story_Activity extends AppCompatActivity {

    ActivityReadStroyBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadStroyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        String storyName = getIntent().getStringExtra("name");

        setSupportActionBar(binding.readStoryToolbar);
        getSupportActionBar().setTitle(storyName);

        binding.collapsingToolbarLayout.setTitle(storyName);
        binding.collapsingToolbarLayout.setExpandedTitleTextSize(50);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.readStoryToolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });



    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}