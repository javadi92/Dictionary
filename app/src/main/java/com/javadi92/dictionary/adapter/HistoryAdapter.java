package com.javadi92.dictionary.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.javadi92.dictionary.utils.App;
import com.javadi92.dictionary.activities.History;
import com.javadi92.dictionary.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.myViewHolder> {

    List<String> words=new ArrayList<>();
    Context mContext;
    TextToSpeech t1;
    boolean multiSelect;
    List<String> selectedItems;

    public HistoryAdapter(Context context,List<String> words){
        this.words=words;
        this.mContext=context;
        this.multiSelect=false;
        this.selectedItems=new ArrayList<>();
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
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_view_holder,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, int i) {
        myViewHolder.tvEnglishHistory.setText(words.get(i));
        myViewHolder.tvRow.setText(i+1+"");
        myViewHolder.imgPronunceHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(myViewHolder.tvEnglishHistory.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        if(!multiSelect){
            myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordSelected=myViewHolder.tvEnglishHistory.getText().toString();
                if(multiSelect){
                    if(selectedItems.contains(wordSelected)){
                        selectedItems.remove(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
                    }
                    else {
                        selectedItems.add(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effetq);
                    }
                }
                else {
                    AlertDialog dialog;
                    AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                    builder.setMessage(App.dbHelper.translateToPersian(myViewHolder.tvEnglishHistory.getText().toString() ));
                    dialog=builder.create();
                    dialog.show();
                }
            }
        });

        myViewHolder.tvEnglishHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordSelected=myViewHolder.tvEnglishHistory.getText().toString();
                if(multiSelect){
                    if(selectedItems.contains(wordSelected)){
                        selectedItems.remove(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
                    }
                    else {
                        selectedItems.add(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effetq);
                    }
                }
                else {
                    AlertDialog dialog;
                    AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                    builder.setMessage(App.dbHelper.translateToPersian(myViewHolder.tvEnglishHistory.getText().toString() ));
                    dialog=builder.create();
                    dialog.show();
                }
            }
        });

        myViewHolder.clHistoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wordSelected=myViewHolder.tvEnglishHistory.getText().toString();
                if(multiSelect){
                    if(selectedItems.contains(wordSelected)){
                        selectedItems.remove(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
                    }
                    else {
                        selectedItems.add(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effetq);
                    }
                }
                else {
                    AlertDialog dialog;
                    AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                    builder.setMessage(App.dbHelper.translateToPersian(myViewHolder.tvEnglishHistory.getText().toString() ));
                    dialog=builder.create();
                    dialog.show();
                }
            }
        });
        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((History)myViewHolder.itemView.getContext()).startSupportActionMode(callback);
                String wordSelected=myViewHolder.tvEnglishHistory.getText().toString();
                if(multiSelect){
                    if(selectedItems.contains(wordSelected)){
                        selectedItems.remove(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
                    }
                    else {
                        selectedItems.add(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effetq);
                    }
                }
                History.toolbarHistory.setVisibility(View.GONE);
                return true;
            }
        });
        myViewHolder.tvEnglishHistory.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((History)myViewHolder.itemView.getContext()).startSupportActionMode(callback);
                String wordSelected=myViewHolder.tvEnglishHistory.getText().toString();
                if(multiSelect){
                    if(selectedItems.contains(wordSelected)){
                        selectedItems.remove(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effect);
                    }
                    else {
                        selectedItems.add(wordSelected);
                        myViewHolder.itemView.setBackgroundResource(R.drawable.effetq);
                    }
                }
                History.toolbarHistory.setVisibility(View.GONE);
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

        TextView tvEnglishHistory,tvRow;
        ImageView imgPronunceHistory;
        ConstraintLayout clHistoryCard;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEnglishHistory=(TextView)itemView.findViewById(R.id.tv_english_history);
            tvRow=(TextView)itemView.findViewById(R.id.tv_row_favorite);
            imgPronunceHistory=(ImageView)itemView.findViewById(R.id.img_pronunce_history);
            clHistoryCard =(ConstraintLayout)itemView.findViewById(R.id.cl_history_card);
        }
    }

    ActionMode.Callback callback=new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            multiSelect=true;
            menu.add("حذف");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if(selectedItems.size()>0){
                for(String w:selectedItems){
                    App.dbHelper.deleteHistoryWord(w);
                    words.remove(w);
                }
            }
            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            History.toolbarHistory.setVisibility(View.VISIBLE);
            multiSelect=false;
            selectedItems.clear();
            notifyDataSetChanged();
        }
    };
}
