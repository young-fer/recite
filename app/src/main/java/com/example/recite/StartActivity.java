package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {
    private ImageView iv_beige, iv_danci;
    private Animation trans_animation = null;
    private Animation alpha_animation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initView();

    }

    private void initView() {
        iv_beige = findViewById(R.id.iv_beige);
        iv_danci = findViewById(R.id.iv_danci);

        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1300);
        alphaAnimation.setFillAfter(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -500);
        translateAnimation.setDuration(1500);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);

        iv_beige.startAnimation(animationSet);
        iv_danci.startAnimation(translateAnimation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        }, 1300);


    }
}