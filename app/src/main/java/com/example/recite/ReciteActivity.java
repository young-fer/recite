package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.recite.tool.Tool;

public class ReciteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);

        RelativeLayout rlRoot = findViewById(R.id.rl_recite);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);


    }


}