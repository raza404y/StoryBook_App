package com.blueroom.englishstories;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blueroom.englishstories.Adapters.CategoriesAdapter;
import com.blueroom.englishstories.databinding.ActivityMainBinding;
import com.blueroom.englishstories.models.CategoriesModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

          getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setSupportActionBar(binding.toolbar);

        ArrayList<CategoriesModel> categoryList = new ArrayList<>();

        categoryList.add(new CategoriesModel("Horror", null, R.drawable.img));
        categoryList.add(new CategoriesModel("Funny", null, R.drawable.img_1));
        categoryList.add(new CategoriesModel("Motivational", null, R.drawable.img_2));
        categoryList.add(new CategoriesModel("Inspirational", null, R.drawable.img_3));
        categoryList.add(new CategoriesModel("Horror", null, R.drawable.img));
        categoryList.add(new CategoriesModel("Funny", null, R.drawable.img_1));
        categoryList.add(new CategoriesModel("Motivational", null, R.drawable.img_2));
        categoryList.add(new CategoriesModel("Inspirational", null, R.drawable.img_3));
        categoryList.add(new CategoriesModel("Horror", null, R.drawable.img));
        categoryList.add(new CategoriesModel("Funny", null, R.drawable.img_1));
        categoryList.add(new CategoriesModel("Motivational", null, R.drawable.img_2));
        categoryList.add(new CategoriesModel("Inspirational", null, R.drawable.img_3));
        categoryList.add(new CategoriesModel("Horror", null, R.drawable.img));
        categoryList.add(new CategoriesModel("Funny", null, R.drawable.img_1));
        categoryList.add(new CategoriesModel("Motivational", null, R.drawable.img_2));
        categoryList.add(new CategoriesModel("Inspirational", null, R.drawable.img_3));
        categoryList.add(new CategoriesModel("Horror", null, R.drawable.img));
        categoryList.add(new CategoriesModel("Funny", null, R.drawable.img_1));
        categoryList.add(new CategoriesModel("Motivational", null, R.drawable.img_2));
        categoryList.add(new CategoriesModel("Inspirational", null, R.drawable.img_3));



        CategoriesAdapter adapter = new CategoriesAdapter(categoryList, MainActivity.this);
        binding.categoriesRecyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share){
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Rate us", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}