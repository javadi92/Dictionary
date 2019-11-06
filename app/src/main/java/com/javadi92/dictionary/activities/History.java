package com.javadi92.dictionary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.javadi92.dictionary.utils.App;
import com.javadi92.dictionary.R;
import com.javadi92.dictionary.adapter.HistoryAdapter;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    RecyclerView recyclerViewHistory;
    HistoryAdapter historyAdapter;
    List<String> words=new ArrayList<>();
    DrawerLayout drawerLayoutHistory;
    ConstraintLayout clExit,clFavorite,clHistory;
    public static Toolbar toolbarHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbarHistory=(Toolbar)findViewById(R.id.toobar_history);
        clFavorite=(ConstraintLayout)findViewById(R.id.menu_favorite);
        clHistory=(ConstraintLayout)findViewById(R.id.menu_history);
        clExit=(ConstraintLayout) findViewById(R.id.menu_exit);
        recyclerViewHistory=(RecyclerView)findViewById(R.id.recy_history);
        drawerLayoutHistory=(DrawerLayout)findViewById(R.id.drawer_history);
        LinearLayoutManager llm=new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHistory.setLayoutManager(llm);
        words= App.dbHelper.getHistoryList();
        historyAdapter=new HistoryAdapter(History.this,words);
        recyclerViewHistory.setAdapter(historyAdapter);

        setSupportActionBar(toolbarHistory);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbarHistory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        clHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayoutHistory.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayoutHistory.closeDrawer(Gravity.RIGHT);
                }
            }
        });
        clFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favoriteIntent=new Intent(History.this, Favorite.class);
                finish();
                startActivity(favoriteIntent);
                if(drawerLayoutHistory.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayoutHistory.closeDrawer(Gravity.RIGHT);
                }
            }
        });
        //exit program
        clExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}
