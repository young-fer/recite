package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recite.bean.Word;
import com.example.recite.component.ControlButton;
import com.example.recite.component.OptionButton;
import com.example.recite.component.WordText;
import com.example.recite.db.DBTool;
import com.example.recite.service.PronunciationPlayerService;
import com.example.recite.tool.Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReviewActivity extends AppCompatActivity {
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
    private ControlButton know_control_btn, not_know_control_btn, next_control_btn;
    private TextView tv_top_info, tv_usphone;


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
        setContentView(R.layout.activity_review);

        RelativeLayout rlRoot = findViewById(R.id.rl_review);
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

        know_control_btn = findViewById(R.id.know_control_btn);
        not_know_control_btn = findViewById(R.id.not_know_control_btn);
        next_control_btn = findViewById(R.id.next_control_btn);

        tv_top_info = findViewById(R.id.tv_top_info);
        tv_usphone = findViewById(R.id.tv_usphone);

        know_control_btn.setOnClickListener(new ControlBtnClickListener());
        not_know_control_btn.setOnClickListener(new ControlBtnClickListener());
        next_control_btn.setOnClickListener(new ControlBtnClickListener());
        tv_usphone.setOnClickListener(new ReadTVClickListener());

        bindService(new Intent(this, PronunciationPlayerService.class), connection, Service.BIND_AUTO_CREATE);
    }

    private void init() {
        dbTool = new DBTool(ReviewActivity.this);
        stuWords = dbTool.getReviewWords();
//        hasStuCnt = 0;
        StuCnt = stuWords.size();
        stu_index = -1;
//        isClicked = false;
        updateView();

    }

    private void updateView() {
        stu_index++;
        if(stu_index >= StuCnt) stu_index = 0;

        if(hasStuCnt == StuCnt) {
            finish();
            Intent intent = new Intent(ReviewActivity.this, SuccessActivity.class);
            startActivity(intent);
            return;
        }


        Word word = null;
        for(int i = 0; i < StuCnt; ++i) {
            word = stuWords.get(stu_index);
            if(word.isFinished()){
                stu_index++;
                if(stu_index >= StuCnt) stu_index = 0;
            }
        }

        ll_info_word_mean.removeAllViews();
        know_control_btn.setVisibility(View.VISIBLE);
        not_know_control_btn.setVisibility(View.VISIBLE);
        next_control_btn.setVisibility(View.GONE);

        word_head.setTag(word.getWordHead());
        word_head.invalidate();


        tv_top_info.setText(hasStuCnt + "/" + StuCnt);
        tv_usphone.setText("[ " + word.getUsphone() + " ]");


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
            TextView textView = new TextView(ReviewActivity.this);
            textView.setText(wordMeans.get(i).character + "  " + wordMeans.get(i).mean);
            textView.setTextSize(16);
            ll_info_word_mean.addView(textView, i);
        }

        know_control_btn.setVisibility(View.GONE);
        not_know_control_btn.setVisibility(View.GONE);
        next_control_btn.setVisibility(View.VISIBLE);

//        rl_info_body.setVisibility(View.VISIBLE);
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

    class ControlBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.know_control_btn:
                    stuWords.get(stu_index).setFinished(true);
                    dbTool.updateReviewDate(stuWords.get(stu_index));
                    hasStuCnt++;
                    showAnswerView();
                    break;
                case R.id.not_know_control_btn:
                    showAnswerView();
                    break;
                case R.id.next_control_btn:
                    updateView();
                    break;
                default:
                    break;
            }
        }
    }
}