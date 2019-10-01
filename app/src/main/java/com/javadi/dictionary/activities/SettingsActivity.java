package com.javadi.dictionary.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.javadi.dictionary.R;
import com.javadi.dictionary.utils.App;

public class SettingsActivity extends AppCompatActivity {

    RadioButton rbETP,rbPTE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        rbETP=(RadioButton)findViewById(R.id.radioButton_english_to_persian);
        rbPTE=(RadioButton)findViewById(R.id.radioButton_persian_to_english);


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
                App.sharedPreferences.edit().putInt("translate_mode",0).commit();
            }
        });

        rbPTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbPTE.setChecked(true);
                rbETP.setChecked(false);
                App.sharedPreferences.edit().putInt("translate_mode",1).commit();
            }
        });
    }
}
