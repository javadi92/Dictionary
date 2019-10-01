package com.javadi.dictionary.activities;

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
import android.widget.ImageView;

import com.javadi.dictionary.utils.App;
import com.javadi.dictionary.R;
import com.javadi.dictionary.adapter.FavoriteAdapter;
import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity {

    RecyclerView recyFavorite;
    List<String> words=new ArrayList<>();
    FavoriteAdapter favoriteAdapter;
    ImageView imgMenuFavoritePage;
    DrawerLayout drawerFavorite;
    ConstraintLayout clMainPage,clExit,clHistory,clFavorite;
    public static Toolbar toolbarFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite );

        recyFavorite=(RecyclerView)findViewById(R.id.recy_favorite);
        clMainPage=(ConstraintLayout)findViewById(R.id.menu_main_page);
        clHistory=(ConstraintLayout)findViewById(R.id.menu_history);
        clFavorite=(ConstraintLayout)findViewById(R.id.menu_favorite);
        clExit=(ConstraintLayout)findViewById(R.id.menu_exit);
        drawerFavorite=(DrawerLayout)findViewById(R.id.drawer_favorite);
        toolbarFavorite=(Toolbar)findViewById(R.id.toobar_favorite_page);
        imgMenuFavoritePage=(ImageView)findViewById(R.id.img_menu_favorite_page);

        LinearLayoutManager llm=new LinearLayoutManager(Favorite.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyFavorite.setLayoutManager(llm);

        setSupportActionBar(toolbarFavorite);

        words= App.dbHelper.getFavoriteList();
        favoriteAdapter=new FavoriteAdapter(this,words);
        recyFavorite.setAdapter(favoriteAdapter);

        imgMenuFavoritePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerFavorite.isDrawerOpen(Gravity.RIGHT)){
                    drawerFavorite.closeDrawer(Gravity.RIGHT);
                }
                else{
                    drawerFavorite.openDrawer(Gravity.RIGHT);
                }
            }
        });
        clMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain=new Intent(Favorite.this, MainActivity.class);
                startActivity(intentMain);
                /*if(drawerFavorite.isDrawerOpen(Gravity.RIGHT)){
                    drawerFavorite.closeDrawer(Gravity.RIGHT);
                }*/
            }
        });
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
