package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recite.tool.Tool;

public class StudyPlanActivity extends AppCompatActivity {
    private Button change_book_btn;
    private ImageView book_img;
    private TextView book_title;

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
        book_img = findViewById(R.id.book_img);
        book_title = findViewById(R.id.book_title);
        change_book_btn = findViewById(R.id.change_book_btn);
        change_book_btn.setOnClickListener(new ClickListener());

        updateBookImage(Tool.bookID);
        System.out.println(Tool.bookID);
        book_img.invalidate();
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(Tool.bookID == 1) {
                System.out.println("书1");
                Tool.bookID = 2;
                updateBookImage(2);
            }else if(Tool.bookID == 2) {
                System.out.println("书2");
                Tool.bookID = 1;
                updateBookImage(1);
            }
        }
    }

    public void updateBookImage(int bookID) {
        if (bookID == 1) {
            book_img.setImageResource(R.drawable.book1);
            book_title.setText("红宝书");
        }else if(bookID == 2){
            book_img.setImageResource(R.drawable.book2);
            book_title.setText("六级高频词汇");
        }
    }

}