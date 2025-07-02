package com.example.firstui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.widget.*;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Handler handler = new Handler();
    int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText editTextName = findViewById(R.id.nameEditText);
        Button buttonGreet = findViewById(R.id.greetButton);
        TextView textViewGreeting = findViewById(R.id.greetingTextView);
//
        CheckBox checkBoxSubscribe = findViewById(R.id.subscribeCheckBox);
        RadioGroup radioGroupColors = findViewById(R.id.colorRadioGroup);
        Switch switchNotifications = findViewById(R.id.notificationSwitch);
//
        SeekBar seekBarVolume = findViewById(R.id.volumeSeekBar);
        TextView textViewVolume = findViewById(R.id.seekBarLabel);
//
        Spinner spinnerCountry = findViewById(R.id.countrySpinner);
        Button buttonSubmit = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.loadingProgressBar);

        // Setup Spinner (Countries)
        String[] countries = {"USA", "Canada", "Germany", "India", "UK"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);

        // Greet Button
        buttonGreet.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String greeting = "Hello, " + name + "!";
            textViewGreeting.setText(greeting);
        });

        // SeekBar Listener
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewVolume.setText("Volume: " + progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Simulate loading on button click
        buttonSubmit.setOnClickListener(v -> simulateLoading(switchNotifications.isChecked(),checkBoxSubscribe.isChecked()));
    }

    private void simulateLoading(boolean notifications,boolean subscribed) {
        progressBar.setVisibility(View.VISIBLE);
        progressStatus = 0;
        progressBar.setProgress(0);

        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 5;
                handler.post(() -> progressBar.setProgress(progressStatus));
                try {
                    Thread.sleep(100); // 100ms * 20 = 2 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            handler.post(() -> progressBar.setVisibility(View.GONE));
        }).start();
        if(notifications){
          if(subscribed){
              Toast.makeText(this,
                      "Subscribed ✅",
                      Toast.LENGTH_LONG).show();
          }
          else {
              Toast.makeText(this,
                      "Not Subscribed ❌",
                      Toast.LENGTH_LONG).show();
          }
        }
    }
}