package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.recite.adapter.WordAdapter;
import com.example.recite.bean.Word;
import com.example.recite.db.DBTool;
import com.example.recite.tool.Tool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WordListActivity extends AppCompatActivity {
    private List<Word> words = null;
    private WordAdapter wordAdapter = null;
    private Context mContext = null;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        RelativeLayout rlRoot = findViewById(R.id.rl_word_list);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);

        initView();
        initData();


        wordAdapter = new WordAdapter((LinkedList<Word>)words, mContext);
        listView.setAdapter(wordAdapter);


    }


    private void initView() {
        mContext = WordListActivity.this;
        listView = findViewById(R.id.list_word);
    }

    private void initData() {
        DBTool dbTool = new DBTool(mContext);
        words = dbTool.selectWord();
    }
}