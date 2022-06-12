package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recite.adapter.WordAdapter;
import com.example.recite.bean.Word;
import com.example.recite.component.ControlButton;
import com.example.recite.component.OptionButton;
import com.example.recite.component.WordText;
import com.example.recite.db.DBTool;
import com.example.recite.service.PronunciationPlayerService;
import com.example.recite.tool.Tool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ReciteActivity extends AppCompatActivity {
    private int hasStuCnt;
    private int StuCnt;
    private int stu_index;
    private boolean isClicked;

    private DBTool dbTool = null;
    private List<Word> stuWords = null;
    private ArrayList<Word.PartOfSpeech> wordMeans = null;
    private LinearLayout ll_option, ll_info_word_mean;


    private RelativeLayout rl_info_body;
    private WordText word_head;
    private ControlButton control_btn;
    private TextView tv_top_info, tv_usphone;
    private Button read_btn;


    // 单词读音
    private PronunciationPlayerService.PlayPronunciationBinder pronunciationBinder;
    private ServiceConnection connection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            pronunciationBinder = (PronunciationPlayerService.PlayPronunciationBinder) iBinder;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);

        RelativeLayout rlRoot = findViewById(R.id.rl_recite);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);
        initView();
        init();
    }

    private void initView() {
        word_head = findViewById(R.id.word_head);
        ll_option = findViewById(R.id.ll_option);
        rl_info_body = findViewById(R.id.rl_info_body);
        ll_info_word_mean = findViewById(R.id.ll_info_word_mean);
        control_btn = findViewById(R.id.control_btn);
        tv_top_info = findViewById(R.id.tv_top_info);
        tv_usphone = findViewById(R.id.tv_usphone);

        for(int i = 0; i < ll_option.getChildCount(); ++i) {
            ll_option.getChildAt(i).setId(i);
            ll_option.getChildAt(i).setOnClickListener(new BtnClickListener());
        }
        control_btn.setOnClickListener(new ControlBtnClickListener());
        tv_usphone.setOnClickListener(new ReadTVClickListener());
//        read_btn.setOnClickListener(new ReadBtnClickListener());

        bindService(new Intent(this, PronunciationPlayerService.class), connection, Service.BIND_AUTO_CREATE);
    }

    private void init() {
        dbTool = new DBTool(ReciteActivity.this);
        stuWords = dbTool.getStuWords();
        hasStuCnt = 0;
        StuCnt = stuWords.size();
        stu_index = -1;
        isClicked = false;
        updateView();


        System.out.println(dbTool.getHasStuWordCnt());
    }


    private void updateView() {
        stu_index++;
        if(stu_index >= StuCnt) stu_index = 0;

        if(hasStuCnt == StuCnt) {
            finish();
            Intent intent = new Intent(ReciteActivity.this, SuccessActivity.class);
            startActivity(intent);
            return;
        }

        OptionButton optionButton;

        Word word = null;
        for(int i = 0; i < StuCnt; ++i) {
            word = stuWords.get(stu_index);
            if(word.isFinished()){
                stu_index++;
                if(stu_index >= StuCnt) stu_index = 0;
            }
        }

        control_btn.setTag("不认识");
        word_head.setTag(word.getWordHead());
        word_head.invalidate();

        Random random = new Random();
        int r = random.nextInt(4);
        int index = 0;
        int false_index = 0;
        while(index < 4) {
            optionButton = (OptionButton) ll_option.getChildAt(index);
            String feature, meaning;
            if(index == r) {
                feature = word.getPartOfSpeeches().get(0).character;
                meaning = word.getPartOfSpeeches().get(0).mean;
                optionButton.setCorrect(true);
            }
            else {
                feature = word.getFalsePartOfSpeeches().get(false_index).character;
                meaning = word.getFalsePartOfSpeeches().get(false_index).mean;
                optionButton.setCorrect(false);
                false_index++;
            }
            optionButton.setFeatures(feature);
            optionButton.setMeaning(meaning);

            optionButton.setBackgroundResource(R.drawable.option_btn);
            optionButton.invalidate();
            index++;
        }


        tv_top_info.setText(hasStuCnt + "/" + StuCnt);
        tv_usphone.setText("[ " + word.getUsphone() + " ]");

        ll_option.setVisibility(View.VISIBLE);
        rl_info_body.setVisibility(View.GONE);

    }

    private void showAnswerView() {

        new Thread(new Runnable(){
            @Override
            public void run() {
                pronunciationBinder.play(stuWords.get(stu_index).getWordHead());
            }
        }).start();

        wordMeans = stuWords.get(stu_index).getPartOfSpeeches();

        ll_info_word_mean.removeAllViews();
        for(int i = 0 ; i < wordMeans.size(); ++i) {
            TextView textView = new TextView(ReciteActivity.this);
            textView.setText(wordMeans.get(i).character + "  " + wordMeans.get(i).mean);
            textView.setTextSize(16);
            ll_info_word_mean.addView(textView, i);
        }

        control_btn.setTag("下一项");
        control_btn.invalidate();

        ll_option.setVisibility(View.GONE);
        rl_info_body.setVisibility(View.VISIBLE);
    }

    /**
     * 定义内部类，实现OnClickListener接口
     */
    class BtnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            isClicked = true;
            OptionButton optionButton = findViewById(v.getId());
            if (optionButton.isCorrect()) {
                optionButton.setBackgroundResource(R.drawable.option_btn_true);
                stuWords.get(stu_index).setFinished(true);
                dbTool.setHasStudy(stuWords.get(stu_index));
                hasStuCnt++;
            }
            else {
                optionButton.setBackgroundResource(R.drawable.option_btn_false);
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showAnswerView();
                }
            }, 200);


        }
    }

    class ControlBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(isClicked) {
                updateView();
                isClicked = false;
            }
            else {
                isClicked = true;
                showAnswerView();

            }
        }
    }


    class ReadTVClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    pronunciationBinder.play(stuWords.get(stu_index).getWordHead());
                }
            }).start();
        }
    }
}