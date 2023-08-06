package com.blueroom.englishstories;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

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


        binding.zoomInButton.setOnClickListener(view -> {
            float count = binding.storytxt.getTextSize();
            float textSize = count + 1;

            // Limit the maximum text size to 50sp
            if (textSize <= 50) {
                binding.storytxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                binding.storytxt.setGravity(Gravity.CENTER);
                Log.d("Log", "Size: "+textSize);
            } else {
                Toast.makeText(this, "Maximum text size reached", Toast.LENGTH_SHORT).show();
            }
        });

        binding.zoomOutButton.setOnClickListener(view -> {
            float count2 = binding.storytxt.getTextSize();
            float textSize2 = count2 - 1;

            // Limit the minimum text size to 12sp
            if (textSize2 >= 20) {
                binding.storytxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize2);
                binding.storytxt.setGravity(Gravity.CENTER);
                Log.d("Log", "Size: "+textSize2);
            } else {
                Toast.makeText(this, "Minimum text size reached", Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}