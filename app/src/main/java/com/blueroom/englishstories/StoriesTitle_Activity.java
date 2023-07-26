package com.blueroom.englishstories;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.blueroom.englishstories.databinding.ActivityStoriesNameBinding;

public class StoriesTitle_Activity extends AppCompatActivity {


    ActivityStoriesNameBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoriesNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        final Toolbar toolbar = findViewById(R.id.toolbar);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        String name = getIntent().getStringExtra("name");
        int uri = getIntent().getIntExtra("uri",0);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(name+" Stories");
        binding.imageViewHeader.setImageResource(uri);


        // Set the initial title color (white when expanded)
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
         collapsingToolbarLayout.setExpandedTitleTextSize(50.5f);
        // Set the title color when collapsed
        final int titleColorCollapsed = Color.BLACK;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isTitleColorWhite = true;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int offsetWithoutRange = Math.abs(verticalOffset) - totalScrollRange;

                // Calculate the animation progress (0 to 1)
                float animationProgress = (float) offsetWithoutRange / toolbar.getHeight();

                // Interpolate the title color from white to black
                int newTitleColor = (int) new ArgbEvaluator().evaluate(animationProgress, Color.WHITE, titleColorCollapsed);

                // Only update the title color if it's different to avoid unnecessary UI updates
                if (isTitleColorWhite && newTitleColor != Color.WHITE) {
                    isTitleColorWhite = false;
                    collapsingToolbarLayout.setExpandedTitleColor(newTitleColor);
                } else if (!isTitleColorWhite && newTitleColor != titleColorCollapsed) {
                    isTitleColorWhite = true;
                    collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
                }
            }
        });


    }
}