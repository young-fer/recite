package com.example.recite.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.recite.R;

public class WordText extends AppCompatTextView {
    private String tag;
    private int num;
    private int paddingLeft;


    public WordText(Context context) {
        super(context);
    }

    public WordText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //获取自定义的属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.word_text);
        tag = ta.getString(R.styleable.word_text_word_tag);
        System.out.println("*******" + tag);
        num = ta.getInteger(R.styleable.word_text_word_num, -1);
        setBackgroundColor(Color.parseColor("#00000000"));

        init();
        ta.recycle();
    }

    public void init() {
        paddingLeft = 25;

    }

    @Override
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制单词
        Paint paint = new Paint();
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setTextSize(100);
        Rect rect = new Rect();
        paint.getTextBounds(tag, 0, tag.length(), rect);
        float tagHeight = rect.height();

        int x = (int) paddingLeft;
        int y = (int) (this.getHeight() + tagHeight/2)/2;
        canvas.drawText(tag, x, y, paint);
    }

}
