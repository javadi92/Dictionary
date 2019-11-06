package com.javadi92.dictionary.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

import com.javadi92.dictionary.R;
import com.javadi92.dictionary.utils.App;

public class SettingsActivity extends AppCompatActivity {

    RadioButton rbETP,rbPTE;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        rbETP=(RadioButton)findViewById(R.id.radioButton_english_to_persian);
        rbPTE=(RadioButton)findViewById(R.id.radioButton_persian_to_english);
        toolbar=(Toolbar)findViewById(R.id.settings_toolbar);

        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if(App.sharedPreferences.getInt("translate_mode",0)==0){
            rbETP.setChecked(true);
            rbPTE.setChecked(false);
        }
        else {
            rbPTE.setChecked(true);
            rbETP.setChecked(false);
        }

        rbETP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbPTE.setChecked(false);
                rbETP.setChecked(true);
                MainActivity.actvMainPage.setText("");
                MainActivity.actvMainPage.setHint("English");
                MainActivity.tvMean.setText("");
                MainActivity.tvMean.setHint("فارسی");
                App.sharedPreferences.edit().putInt("translate_mode",0).commit();
            }
        });

        rbPTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbPTE.setChecked(true);
                rbETP.setChecked(false);
                MainActivity.actvMainPage.setText("");
                MainActivity.actvMainPage.setHint("فارسی");
                MainActivity.tvMean.setText("");
                MainActivity.tvMean.setHint("English");
                App.sharedPreferences.edit().putInt("translate_mode",1).commit();
            }
        });
    }
}
