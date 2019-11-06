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
import com.javadi92.dictionary.adapter.FavoriteAdapter;
import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity {

    RecyclerView recyFavorite;
    List<String> words=new ArrayList<>();
    FavoriteAdapter favoriteAdapter;
    DrawerLayout drawerFavorite;
    ConstraintLayout clExit,clHistory,clFavorite;
    public static Toolbar toolbarFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite );

        recyFavorite=(RecyclerView)findViewById(R.id.recy_favorite);

        clHistory=(ConstraintLayout)findViewById(R.id.menu_history);
        clFavorite=(ConstraintLayout)findViewById(R.id.menu_favorite);
        clExit=(ConstraintLayout)findViewById(R.id.menu_exit);
        drawerFavorite=(DrawerLayout)findViewById(R.id.drawer_favorite);
        toolbarFavorite=(Toolbar)findViewById(R.id.toobar_favorite_page);

        LinearLayoutManager llm=new LinearLayoutManager(Favorite.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyFavorite.setLayoutManager(llm);

        setSupportActionBar(toolbarFavorite);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbarFavorite.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        words= App.dbHelper.getFavoriteList();
        favoriteAdapter=new FavoriteAdapter(this,words);
        recyFavorite.setAdapter(favoriteAdapter);

        clFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerFavorite.isDrawerOpen(Gravity.RIGHT)){
                    drawerFavorite.closeDrawer(Gravity.RIGHT);
                }
            }
        });
        clHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistory=new Intent(Favorite.this, History.class);
                finish();
                startActivity(intentHistory);
                if(drawerFavorite.isDrawerOpen(Gravity.RIGHT)){
                    drawerFavorite.closeDrawer(Gravity.RIGHT);
                }
            }
        });
        clExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}
