package com.example.recite.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.example.recite.R;

public class OptionButton extends AppCompatButton {
    String features;
    String meaning;
    boolean correct;
    private int paddingLeft;
    private int paddingTop;

    public OptionButton(Context context) {
        super(context);
    }

    public OptionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化一些属性
        init();
        //获取自定义的属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.option_button);
        features = ta.getString(R.styleable.option_button_option_features);
        meaning = ta.getString(R.styleable.option_button_option_meaning);
//        setBackgroundColor(Color.parseColor("#00000000"));

        init();
        ta.recycle();
    }

    public void init() {
        paddingLeft = 25;
        paddingTop = 75;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
//        Paint bg_p = new Paint();
//        bg_p.setColor(Color.rgb(0xFF,0xFF,0xFF));
//        canvas.drawRoundRect(new RectF(1,1,this.getWidth()-2,this.getHeight()-2),15,15,bg_p); //画圆角矩形


        //绘制第一行文字
        Paint paint = new Paint();
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setTextSize(45);
        paint.setColor(Color.parseColor("#dadada"));
        float tagWidth = paint.measureText(features);
        int x = (int) paddingLeft;
        int y = paddingTop;
        canvas.drawText(features, x + 10, y, paint);
        //绘制第二行文字
        Paint paint1 = new Paint();
        paint.setAntiAlias(true);
        paint1.setTextSize(50);
        paint1.setColor(Color.parseColor("#000000"));
        float numWidth = paint.measureText(meaning + "");
        int x1 = (int) paddingLeft;
        int y1 = paddingTop + 45 + 35;
        canvas.drawText(meaning+"", x1 + 10, y1, paint1);
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
