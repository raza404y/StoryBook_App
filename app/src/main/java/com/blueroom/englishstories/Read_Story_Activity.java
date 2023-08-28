package com.blueroom.englishstories;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.Locale;

public class Read_Story_Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int TTS_CHECK_CODE = 111;
    ActivityReadStroyBinding binding;
    private TextView textView;
    private ImageView speakButton;
    private TextToSpeech textToSpeech;
    private int pausedPosition = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadStroyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        textView = findViewById(R.id.storytxt);
        speakButton = findViewById(R.id.speakButton);
        String storyName = getIntent().getStringExtra("name");

        binding.storyTitleTv.setText(storyName);

        binding.storyTitleTv.setOnClickListener(view -> {

            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

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
            textToSpeech.setSpeechRate(0.7f);
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "speak");
        }else {
            Toast.makeText(this, "Engine is getting ready..", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
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
}