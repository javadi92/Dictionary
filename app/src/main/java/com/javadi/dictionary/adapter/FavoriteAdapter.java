package com.javadi.dictionary.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.javadi.dictionary.utils.App;
import com.javadi.dictionary.activities.Favorite;
import com.javadi.dictionary.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.myViewHolder> {

    Context mContext;
    List<String> words=new ArrayList<>();
    TextToSpeech t1;
    List<String> wordSelected;
    boolean multiSelected;

    public FavoriteAdapter(Context context,List<String> words){
        this.words=words;
        this.mContext=context;
        this.wordSelected=new ArrayList<>();
        this.multiSelected=false;
        t1 = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.favorite_view_holder,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, int i) {
        myViewHolder.tvEnglishFavorite.setText(words.get(i));
        myViewHolder.tvRow.setText(i+1+"");
        myViewHolder.imgPronunceFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(myViewHolder.tvEnglishFavorite.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        if (!multiSelected){
            myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
        }
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(multiSelected){
                    String temp=myViewHolder.tvEnglishFavorite.getText().toString();
                    if(wordSelected.contains(temp)){
                        wordSelected.remove(temp);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
                    }
                    else {
                        wordSelected.add(temp);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effetq);
                    }
                }
                else {
                    AlertDialog dialog;
                    AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                    builder.setMessage(App.dbHelper.translateToPersian(myViewHolder.tvEnglishFavorite.getText().toString() ));
                    dialog=builder.create();
                    dialog.show();
                }
            }
        });
        myViewHolder.tvEnglishFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(multiSelected){
                    String temp=myViewHolder.tvEnglishFavorite.getText().toString();
                    if(wordSelected.contains(temp)){
                        wordSelected.remove(temp);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
                    }
                    else {
                        wordSelected.add(temp);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effetq);
                    }
                }
                else {
                    AlertDialog dialog;
                    AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                    builder.setMessage(App.dbHelper.translateToPersian(myViewHolder.tvEnglishFavorite.getText().toString() ));
                    dialog=builder.create();
                    dialog.show();
                }
            }
        });
        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((Favorite)myViewHolder.itemView.getContext()).startSupportActionMode(callback);
                Favorite.toolbarFavorite.setVisibility(View.GONE);
                String temp=myViewHolder.tvEnglishFavorite.getText().toString();
                if(wordSelected.contains(temp)){
                    wordSelected.remove(temp);
                    myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
                }
                else {
                    wordSelected.add(temp);
                    myViewHolder.itemView.setBackgroundResource(R.drawable.effetq);
                }
                return true;
            }
        });
        myViewHolder.tvEnglishFavorite.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((Favorite)myViewHolder.itemView.getContext()).startSupportActionMode(callback);
                Favorite.toolbarFavorite.setVisibility(View.GONE);
                String temp=myViewHolder.tvEnglishFavorite.getText().toString();
                if(wordSelected.contains(temp)){
                    wordSelected.remove(temp);
                    myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
                }
                else {
                    wordSelected.add(temp);
                    myViewHolder.itemView.setBackgroundResource(R.drawable.effetq);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView tvEnglishFavorite,tvRow;
        ImageView imgPronunceFavorite;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEnglishFavorite=(TextView)itemView.findViewById(R.id.tv_english_favorite);
            tvRow=(TextView)itemView.findViewById(R.id.tv_row_favorite);
            imgPronunceFavorite=(ImageView)itemView.findViewById(R.id.img_pronunce_favorite);
        }
    }

    ActionMode.Callback callback=new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            multiSelected=true;
            menu.add("حذف");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if(wordSelected.size()>0){
                for(String w:wordSelected){
                    App.dbHelper.deleteFavoriteWord(w);
                    words.remove(w);
                }
            }
            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            Favorite.toolbarFavorite.setVisibility(View.VISIBLE);
            multiSelected=false;
            wordSelected.clear();
            notifyDataSetChanged();
        }
    };
}
