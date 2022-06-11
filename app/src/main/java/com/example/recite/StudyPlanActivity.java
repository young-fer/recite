package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.recite.tool.Tool;

public class StudyPlanActivity extends AppCompatActivity {
    private Button change_book_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_plan);

        RelativeLayout rlRoot = findViewById(R.id.rl_stu_plan);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);
        initView();
    }

    private void initView() {
        change_book_btn = findViewById(R.id.change_book_btn);
        change_book_btn.setOnClickListener(new ClickListener());
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (Tool.bookID == 1) {
                Tool.bookID = 2;
            }else {
                Tool.bookID = 1;
            }
        }
    }

}