package com.blueroom.englishstories;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blueroom.englishstories.Adapters.StoriesAdapter;
import com.blueroom.englishstories.databinding.ActivityStoriesBinding;
import com.blueroom.englishstories.models.StoriesModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import android.os.Build;
import android.view.View;


import java.util.ArrayList;

public class StoriesActivity extends AppCompatActivity {


    ActivityStoriesBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        String name = getIntent().getStringExtra("name");

        binding.categoryNameTv.setText(name+" Stories");

        binding.categoryNameTv.setOnClickListener(view -> {

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            onBackPressed();

        });







        ArrayList<StoriesModel> nameList = new ArrayList<>();
        nameList.add(new StoriesModel("J.K Rowling","Once there was a girl named Lia. She was very lonely. She had no friends or sisters.",R.drawable.img));
        nameList.add(new StoriesModel("Mark Zukerberg","In a town of Shimla, there was a very passionate and high dreamer teen, Prakhar Negi. He was very fine at artistic works. At a very early",R.drawable.img_1));
        nameList.add(new StoriesModel("Bill Gates","Mother Teresa was born (Anjezë Gonxhe Bojaxhiu) in 1910 in what is now part of modern Macedonia",R.drawable.img_2));
        nameList.add(new StoriesModel("Jeff Bezoz","I rushed to the hospital after being informed by my school peon about the sudden decline of my grandmother's health. ",R.drawable.img));
        nameList.add(new StoriesModel("J.K Rowling","Once there was a girl named Lia. She was very lonely. She had no friends or sisters.",R.drawable.img_3));
        nameList.add(new StoriesModel("Mark Zukerberg","In a town of Shimla, there was a very passionate and high dreamer teen, Prakhar Negi. He was very fine at artistic works. At a very early",R.drawable.img_3));
        nameList.add(new StoriesModel("Bill Gates","Mother Teresa was born (Anjezë Gonxhe Bojaxhiu) in 1910 in what is now part of modern Macedonia",R.drawable.img));
        nameList.add(new StoriesModel("Jeff Bezoz","I rushed to the hospital after being informed by my school peon about the sudden decline of my grandmother's health. ",R.drawable.img_2));
        nameList.add(new StoriesModel("J.K Rowling","Once there was a girl named Lia. She was very lonely. She had no friends or sisters.",R.drawable.img));
        nameList.add(new StoriesModel("Mark Zukerberg","In a town of Shimla, there was a very passionate and high dreamer teen, Prakhar Negi. He was very fine at artistic works. At a very early",R.drawable.img_3));
        nameList.add(new StoriesModel("Bill Gates","Mother Teresa was born (Anjezë Gonxhe Bojaxhiu) in 1910 in what is now part of modern Macedonia",R.drawable.img_2));
        nameList.add(new StoriesModel("Jeff Bezoz","I rushed to the hospital after being informed by my school peon about the sudden decline of my grandmother's health. ",R.drawable.img));
        nameList.add(new StoriesModel("J.K Rowling","Once there was a girl named Lia. She was very lonely. She had no friends or sisters.",R.drawable.img_2));
        nameList.add(new StoriesModel("Mark Zukerberg","In a town of Shimla, there was a very passionate and high dreamer teen, Prakhar Negi. He was very fine at artistic works. At a very early",R.drawable.img_1));
        nameList.add(new StoriesModel("Bill Gates","Mother Teresa was born (Anjezë Gonxhe Bojaxhiu) in 1910 in what is now part of modern Macedonia",R.drawable.img));
        nameList.add(new StoriesModel("Jeff Bezoz","I rushed to the hospital after being informed by my school peon about the sudden decline of my grandmother's health. ",R.drawable.img_3));
        nameList.add(new StoriesModel("J.K Rowling","Once there was a girl named Lia. She was very lonely. She had no friends or sisters.",R.drawable.img_1));
        nameList.add(new StoriesModel("Mark Zukerberg","In a town of Shimla, there was a very passionate and high dreamer teen, Prakhar Negi. He was very fine at artistic works. At a very early",R.drawable.img));
        nameList.add(new StoriesModel("Bill Gates","Mother Teresa was born (Anjezë Gonxhe Bojaxhiu) in 1910 in what is now part of modern Macedonia",R.drawable.img_1));
        nameList.add(new StoriesModel("Jeff Bezoz","I rushed to the hospital after being informed by my school peon about the sudden decline of my grandmother's health. ",R.drawable.img));


        StoriesAdapter adapter = new StoriesAdapter(nameList, StoriesActivity.this);
        binding.storiesNameRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(StoriesActivity.this);
        binding.storiesNameRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}