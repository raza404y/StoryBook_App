package com.blueroom.englishstories;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blueroom.englishstories.databinding.ActivityReadStroyBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Read_Story_Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int TTS_CHECK_CODE = 111;
    ActivityReadStroyBinding binding;
    private TextView textView;
    private ImageView speakButton;
    private TextToSpeech textToSpeech;
    private int pausedPosition = 0;
    private AdView adView2;
    int counter = 0;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadStroyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        textView = findViewById(R.id.storytxt);
        speakButton = findViewById(R.id.speakButton);
        adView2 = findViewById(R.id.adView2);

        String storyName = getIntent().getStringExtra("name");
        String story = getIntent().getStringExtra("stxt");

        binding.storyTitleTv.setText(storyName);
        binding.storytxt.setText(story);

        binding.storyTitleTv.setOnClickListener(view -> {

            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        });

        final SharedPreferences countPref = getSharedPreferences("counter", Context.MODE_PRIVATE);
        counter = countPref.getInt("count", 0);

        SharedPreferences.Editor editorAdsControl = countPref.edit();
        if (countPref.getLong("ExpireDate", -1) > System.currentTimeMillis()) {

        } else {
            editorAdsControl.clear();
            editorAdsControl.apply();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("banner_Ad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    boolean showAds = Boolean.TRUE.equals(snapshot.getValue(boolean.class));
                    if (showAds) {

                        if (counter < 2) {
                            loadAD();
                        }

                    } else {
                        adView2.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled: " + error.getMessage());
            }
        });


        binding.storytxt.setGravity(Gravity.CENTER);

        binding.zoomInButton.setOnClickListener(view -> {
            float count = binding.storytxt.getTextSize();
            binding.storytxt.setGravity(Gravity.CENTER);
            float textSize = count + 1;

            // Limit the maximum text size to 50sp
            if (textSize <= 53) {
                binding.storytxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                Log.d("Log", "Size: " + textSize);
            } else {
                Toast.makeText(this, "Maximum text size reached", Toast.LENGTH_SHORT).show();
            }
        });

        binding.zoomOutButton.setOnClickListener(view -> {
            float count2 = binding.storytxt.getTextSize();
            binding.storytxt.setGravity(Gravity.CENTER);
            float textSize2 = count2 - 1;

            // Limit the minimum text size to 12sp
            if (textSize2 >= 20) {
                binding.storytxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize2);
                Log.d("Log", "Size: " + textSize2);
            } else {
                Toast.makeText(this, "Minimum text size reached", Toast.LENGTH_SHORT).show();
            }
        });


        // Initialize TextToSpeech
            initializeTextToSpeech();
        // Disable the button until the TextToSpeech engine is initialized
        speakButton.setEnabled(false);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Read_Story_Activity.this, "Speech engine is getting ready..", Toast.LENGTH_SHORT).show();
                if (textToSpeech.isSpeaking()) {
                    textToSpeech.stop();
                    binding.speakButton.setImageDrawable(getDrawable(R.drawable.volume_outline));
                } else {
                    binding.speakButton.setImageDrawable(getDrawable(R.drawable.volume_filled));
                    speakText();
                }
            }
        });


    }

    ////////////////// Methods ////////////////////////

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
            } else {
                // Enable the button when the TextToSpeech engine is initialized
                speakButton.setEnabled(true);
            }
        }
    }

    private void speakText() {
        String text = textView.getText().toString();

        if (!text.isEmpty()) {
            // Split the text into chunks of 4000 characters
            int chunkSize = 4000;
            for (int i = 0; i < text.length(); i += chunkSize) {
                int end = Math.min(i + chunkSize, text.length());
                String chunk = text.substring(i, end);

                textToSpeech.setSpeechRate(0.6f);
                textToSpeech.speak(chunk, TextToSpeech.QUEUE_ADD, null, "speak");

                // Add a delay between chunks to ensure proper processing
                try {
                    Thread.sleep(1000); // You can adjust this delay as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(this, "Engine is getting ready..", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initializeTextToSpeech() {
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, TTS_CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TTS_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // TTS engine is available, proceed with initialization
                textToSpeech = new TextToSpeech(this, this);
            } else {
                Toast.makeText(this, "Engine is getting ready..", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void loadAD() {

        MobileAds.initialize(this, initializationStatus -> {
        });

        AdRequest adRequest = new AdRequest.Builder().build();


        if (counter < 2) {
            adView2.loadAd(adRequest);
            adView2.setVisibility(View.VISIBLE);
        } else {
            // disable the ad if the user has clicked on it more than twice
            adView2.destroy();
            return; // stop executing the rest of the code
        }

        adView2.setAdListener(new AdListener() {
            private boolean adClicked = false;

            @Override
            public void onAdOpened() {
                super.onAdOpened();

                if (!adClicked) {
                    adClicked = true;
                    counter++;
                    SharedPreferences countPref = getSharedPreferences("counter", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorAdsControl = countPref.edit();
                    editorAdsControl.putInt("count", counter);
                    // Expire the shared preferences after some 1 minute set here according to requirement
                    editorAdsControl.putLong("ExpireDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(400));
                    editorAdsControl.apply();
                }

                if (counter >= 2) {
                    // disable the ad if the user has clicked on it more than twice
                    adView2.destroy();
                }
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                // finish the activity when the user leaves the app through the ad
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        // Check if the binding object is not null before attempting to destroy the ad view
        if (binding != null) {
            binding.adView2.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        // pause the ad view when the fragment is paused
        adView2.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // resume the ad view when the fragment is resumed
        adView2.resume();
    }

}