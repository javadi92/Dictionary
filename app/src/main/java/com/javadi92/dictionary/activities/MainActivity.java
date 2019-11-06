package com.javadi92.dictionary.activities;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.javadi92.dictionary.utils.App;
import com.javadi92.dictionary.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    List<String> englishWords =new ArrayList<>();
    List<String> persianWords =new ArrayList<>();
    List<String> historyWords=new ArrayList<>();
    List<String> favoriteWords=new ArrayList<>();
    Toolbar toolbar;
    ImageView imgPronounce,imgMenu,imgFavorite,imgLeftFlag,imgRightFlag;
    DrawerLayout drawerLayout;
    static AutoCompleteTextView actvMainPage;
    ConstraintLayout clExitMenu,clHistoryMenu,clFavorite,clMainMenu,clContact,clSettings;
    static TextView tvMean;
    TextToSpeech t1;
    static ArrayAdapter arrayAdapterEnglish;
    static ArrayAdapter arrayAdapterPersian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        toolbar=(Toolbar)findViewById(R.id.toobar_history);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        imgMenu=(ImageView)findViewById(R.id.img_menu_history_page);
        imgPronounce=(ImageView)findViewById(R.id.img_pronounce_history_page);
        actvMainPage=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView_main_page);
        imgFavorite=(ImageView)findViewById(R.id.img_favorite);
        imgLeftFlag=(ImageView)findViewById(R.id.img_left_flag);
        imgRightFlag=(ImageView)findViewById(R.id.img_right_flag);
        clExitMenu=(ConstraintLayout)findViewById(R.id.menu_exit);
        clFavorite=(ConstraintLayout)findViewById(R.id.menu_favorite);

        clContact=(ConstraintLayout)findViewById(R.id.menu_contact);
        clSettings=(ConstraintLayout)findViewById(R.id.menu_settings);
        tvMean =(TextView)findViewById(R.id.tv_mean);
        clHistoryMenu=(ConstraintLayout)findViewById(R.id.menu_history);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });


        //set toolbar
        setSupportActionBar(toolbar);

        //hide keyboard at startup
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        clickManager();

        //menu drawer manager
        if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }

        actvMainPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(App.sharedPreferences.getInt("translate_mode",0)==0){
                    translateToPersian();
                }
                else{
                    translateToEnglish();
                }
            }
        });
        actvMainPage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(actvMainPage.getText().toString().length()>0){
                        if(App.sharedPreferences.getInt("translate_mode",0)==0){
                            translateToPersian();
                        }
                        else{
                            translateToEnglish();
                        }
                    }
                    else{
                        hideKeyboard();
                        Toast.makeText(MainActivity.this,"لغتی یافت نشد",Toast.LENGTH_SHORT).show();
                    }
                    actvMainPage.dismissDropDown();
                }
                return false;
            }
        });

        actvMainPage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<3){
                    if(App.sharedPreferences.getInt("translate_mode",0)==0){
                        arrayAdapterEnglish=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, englishWords);
                        actvMainPage.setAdapter(arrayAdapterEnglish);
                        persianWords.clear();
                    }
                    else {
                        arrayAdapterPersian=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, persianWords);
                        actvMainPage.setAdapter(arrayAdapterPersian);
                        englishWords.clear();
                    }
                }
                tvMean.setText("");
                imgFavorite.setImageResource(R.drawable.favorite_border);
                imgFavorite.setTag("false");
                if(actvMainPage.getText().toString().equals("") && App.sharedPreferences.getInt("translate_mode",0)==0){
                    tvMean.setText("فارسی");
                    actvMainPage.setHint("English");
                }
                else if(actvMainPage.getText().toString().equals("") && App.sharedPreferences.getInt("translate_mode",0)==1){
                    tvMean.setText("English");
                    actvMainPage.setHint("فارسی");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //hide keyboard
    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void checkHistoryContainer(){
        if(App.sharedPreferences.getInt("translate_mode",0)==0){
            historyWords=App.dbHelper.getHistoryList();
            if(!historyWords.contains(actvMainPage.getText().toString())){
                App.dbHelper.insertHistoryWord(actvMainPage.getText().toString());
            }
        }
        else{
            historyWords=App.dbHelper.getHistoryList();
            if(!historyWords.contains(tvMean.getText().toString())){
                App.dbHelper.insertHistoryWord(tvMean.getText().toString());
            }
        }
    }

    private boolean checkfavoriteContainer(){
        if(App.sharedPreferences.getInt("translate_mode",0)==0){
            favoriteWords=App.dbHelper.getFavoriteList();
            if(favoriteWords.contains(actvMainPage.getText().toString())){
                return true;
            }
        }
        else{
            favoriteWords=App.dbHelper.getFavoriteList();
            if(favoriteWords.contains(tvMean.getText().toString())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        imgFavorite.setImageResource(R.drawable.favorite_border);
        imgFavorite.setTag("false");
    }

    @Override
    protected void onPause() {
        super.onPause();
        imgFavorite.setImageResource(R.drawable.favorite_border);
        imgFavorite.setTag("false");
    }

    @Override
    protected void onResume() {
        super.onResume();
        actvMainPage.dismissDropDown();
        if(actvMainPage.getText().toString().length()==0){
            //Toast.makeText(MainActivity.this,"",Toast.LENGTH_LONG).show();
            appSettings();
        }
        else {
            flagImageSet();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadWords();
            }
        }).start();
    }

    private void appSettings(){

        int translate_mode=App.sharedPreferences.getInt("translate_mode",0);
        if(translate_mode==0){
            imgLeftFlag.setImageResource(R.drawable.england);
            imgRightFlag.setImageResource(R.drawable.iran);
            actvMainPage.setText("");
            actvMainPage.setHint("English");
            tvMean.setText("فارسی");
        }
        else if(translate_mode==1){
            imgLeftFlag.setImageResource(R.drawable.iran);
            imgRightFlag.setImageResource(R.drawable.england);
            actvMainPage.setText("");
            actvMainPage.setHint("فارسی");
            tvMean.setText("English");
        }
    }

    private void flagImageSet(){

        int translate_mode=App.sharedPreferences.getInt("translate_mode",0);
        if(translate_mode==0){
            imgLeftFlag.setImageResource(R.drawable.england);
            imgRightFlag.setImageResource(R.drawable.iran);
        }
        else if(translate_mode==1){
            imgLeftFlag.setImageResource(R.drawable.iran);
            imgRightFlag.setImageResource(R.drawable.england);
        }
    }

    private void loadWords(){

        int translate_mode=App.sharedPreferences.getInt("translate_mode",0);
        if(translate_mode==0){
            if(englishWords.size()==0){
                englishWords = App.dbHelper.englishWordList();
                persianWords.clear();
            }
        }
        else if(translate_mode==1){
            if(persianWords.size()==0){
                persianWords = App.dbHelper.persianWordList();
                englishWords.clear();
            }
        }
    }

    private void translateToPersian(){
        String mean=App.dbHelper.translateToPersian(actvMainPage.getText().toString().toLowerCase());
        if(mean.equals("")){
            Toast.makeText(MainActivity.this,"لغتی یافت نشد",Toast.LENGTH_LONG).show();
        }
        else{
            tvMean.setText(mean);
        }

        hideKeyboard();
        checkHistoryContainer();
        if(checkfavoriteContainer()==true){
            imgFavorite.setImageResource(R.drawable.favorite_fill);
            imgFavorite.setTag("true");
        }
        else {
            imgFavorite.setImageResource(R.drawable.favorite_border);
            imgFavorite.setTag("false");
        }
    }

    private void translateToEnglish(){
        tvMean.setText(App.dbHelper.translateToEnglish(actvMainPage.getText().toString()));
        hideKeyboard();
        checkHistoryContainer();
        if(checkfavoriteContainer()==true){
            imgFavorite.setImageResource(R.drawable.favorite_fill);
            imgFavorite.setTag("true");
        }
        else {
            imgFavorite.setImageResource(R.drawable.favorite_border);
            imgFavorite.setTag("false");
        }
    }

    private void AddToHistory(){
        if(checkfavoriteContainer()==false){
            if(App.sharedPreferences.getInt("translate_mode",0)==0){
                if(App.dbHelper.insertFavoriteWord(actvMainPage.getText().toString())!=-1){
                    Toast.makeText(MainActivity.this,"لغت به مورد علاقه ها افزوده شد",Toast.LENGTH_SHORT).show();
                    imgFavorite.setImageResource(R.drawable.favorite_fill);
                    imgFavorite.setTag("true");
                }
            }
            else{
                if(App.dbHelper.insertFavoriteWord(tvMean.getText().toString())!=-1){
                    Toast.makeText(MainActivity.this,"لغت به مورد علاقه ها افزوده شد",Toast.LENGTH_SHORT).show();
                    imgFavorite.setImageResource(R.drawable.favorite_fill);
                    imgFavorite.setTag("true");
                }
            }
        }
        else {
            if(App.sharedPreferences.getInt("translate_mode",0)==0){
                if(App.dbHelper.deleteFavoriteWord(actvMainPage.getText().toString())!=-1){
                    Toast.makeText(MainActivity.this,"لغت از مجموعه مورد علاقه ها حذف شد",Toast.LENGTH_SHORT).show();
                }
                imgFavorite.setImageResource(R.drawable.favorite_border);
                imgFavorite.setTag("false");
            }
            else{
                if(App.dbHelper.deleteFavoriteWord(tvMean.getText().toString())!=-1){
                    Toast.makeText(MainActivity.this,"لغت از مجموعه مورد علاقه ها حذف شد",Toast.LENGTH_SHORT).show();
                }
                imgFavorite.setImageResource(R.drawable.favorite_border);
                imgFavorite.setTag("false");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.img_menu_history_page):
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                else{
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
                break;
            case (R.id.menu_exit):
                finish();
                break;

            case (R.id.menu_history):
                Intent historyIntent=new Intent(MainActivity.this, History.class);
                startActivity(historyIntent);
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                break;
            case (R.id.menu_favorite):
                Intent favoriteIntent=new Intent(MainActivity.this, Favorite.class);
                startActivity(favoriteIntent);
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                break;
            case (R.id.menu_contact):
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("راه ارتباطی");
                builder.setMessage("javadimehr01@gmail.com");
                /*builder.setPositiveButton("خب", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });*/
                dialog=builder.create();
                //dialog.getButton(Dialog.BUTTON_POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f);

                dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL); // set title and message direction to RTL
                dialog.show();
                break;
            case (R.id.menu_settings):
                Intent settingsIntent=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(settingsIntent);
                if(drawerLayout.isOpaque()){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                break;
            case (R.id.img_pronounce_history_page):
                if(App.sharedPreferences.getInt("translate_mode",0)==0){
                    if(actvMainPage.getText().toString().trim()!=""){
                        t1.speak(actvMainPage.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else{
                    if(actvMainPage.getText().toString().trim()!=""){
                        t1.speak(tvMean.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                break;
            case (R.id.img_favorite):
                if(!tvMean.getText().toString().equals("") && !actvMainPage.getText().toString().equals("")){
                    AddToHistory();
                }
                else {
                    Toast.makeText(MainActivity.this,"لغتی یافت نشد",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void clickManager(){
        imgMenu.setOnClickListener(this);

        clExitMenu.setOnClickListener(this);

        clHistoryMenu.setOnClickListener(this);

        clFavorite.setOnClickListener(this);

        clContact.setOnClickListener(this);

        clSettings.setOnClickListener(this);

        imgPronounce.setOnClickListener(this);

        imgFavorite.setOnClickListener(this);
    }
}
