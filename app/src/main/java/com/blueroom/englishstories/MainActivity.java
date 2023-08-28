package com.blueroom.englishstories;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

        // getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        ToolbarItemsListeners();



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

    /////////////////////  Methods /////////////////////////

    private void ToolbarItemsListeners(){

        binding.rateUsBtn.setOnClickListener(view -> {

            Uri uri;
            uri = Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName());
            Intent i = new Intent(Intent.ACTION_VIEW,uri);
            try {
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });


        binding.contactBtn.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + getString(R.string.email_feedback)));
                intent.setPackage("com.google.android.gm");
                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
                startActivity(intent);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        });



        binding.shareBtn.setOnClickListener(view -> {

            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Stories");
                intent.putExtra(Intent.EXTRA_TEXT,"100+ stories for every mood *Stories*\nDownload Now from Google Play Store"+"\n\n"+"https://play.google.com/store/apps/details?id="+getPackageName());
                startActivity(Intent.createChooser(intent,"Share with"));
            }
            catch (Exception e){
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });


    }
}