package com.example.recite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recite.R;
import com.example.recite.bean.Word;

import java.util.LinkedList;

public class WordAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<Word> words;

    public WordAdapter() {}

    public WordAdapter(LinkedList<Word> words, Context mContext) {
        this.words = words;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_word,viewGroup,false);
            holder = new ViewHolder();
            holder.tv_word_head =(TextView) view.findViewById(R.id.tv_word_head);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_word_head.setText(words.get(i).getWordHead());
        return view;
    }


    private class ViewHolder{
//        ImageView img_icon;
        TextView tv_word_head;
    }
}
