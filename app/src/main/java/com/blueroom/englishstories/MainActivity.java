package com.blueroom.englishstories;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.blueroom.englishstories.Adapters.CategoriesAdapter;
import com.blueroom.englishstories.databinding.ActivityMainBinding;
import com.blueroom.englishstories.models.CategoriesModel;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase database;
    AppUpdateManager appUpdateManager;
    static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 124;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        checkUpdate();

        ToolbarItemsListeners();

        database = FirebaseDatabase.getInstance();

        ArrayList<CategoriesModel> categoryList = new ArrayList<>();
        CategoriesAdapter adapter = new CategoriesAdapter(categoryList,MainActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.categoriesRecyclerView.setAdapter(adapter);
        binding.categoriesRecyclerView.setLayoutManager(layoutManager);


        database.getReference().child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    categoryList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CategoriesModel model = dataSnapshot.getValue(CategoriesModel.class);
                        assert model != null;
                        model.setCategoryId(dataSnapshot.getKey());
                        categoryList.add(model);
                    }
                    adapter.notifyDataSetChanged();
                    binding.progressBar.setVisibility(View.GONE);
                }
                Collections.shuffle(categoryList);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



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
    // ## in app update method
    private void checkUpdate() {

        try {
            Task<AppUpdateInfo> appUpdateInfoTask = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
            }

            assert appUpdateInfoTask != null;
            appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    startUpdateFlow(appUpdateInfo);
                } else if  (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                    startUpdateFlow(appUpdateInfo);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, IMMEDIATE_APP_UPDATE_REQ_CODE);
            }
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMMEDIATE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Kindly update now to use latest features.", Toast.LENGTH_LONG).show();
                //  finish();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update success!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed!", Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }
    }
}