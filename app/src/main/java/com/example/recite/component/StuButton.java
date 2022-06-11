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

public class StuButton extends AppCompatButton {
    String tag;
    int num;
    private int paddingLeft;

    public StuButton(Context context) {
        super(context);
    }

    public StuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化一些属性
        init();
        //获取自定义的属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.stu_btn_style);
        tag = ta.getString(R.styleable.stu_btn_style_tag);
        num = ta.getInteger(R.styleable.stu_btn_style_num, -1);
        setBackgroundColor(Color.parseColor("#00000000"));

        ta.recycle();
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

    public void init() {
        paddingLeft = 60;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        Paint bg_p = new Paint();
        bg_p.setColor(Color.rgb(0xFF,0xFF,0xFF));
        bg_p.setAlpha(152);
        canvas.drawRoundRect(new RectF(0,0,this.getWidth(),this.getHeight()),15,15,bg_p); //画圆角矩形
        //绘制第一行文字
        Paint paint = new Paint();
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setTextSize(60);
        float tagWidth = paint.measureText(tag);
        int x = (int) paddingLeft;
        int y = this.getHeight()/2;
        canvas.drawText(tag, x, y, paint);
        //绘制第二行文字
        Paint paint1 = new Paint();
        paint.setAntiAlias(true);
        paint1.setTextSize(40);
        paint1.setColor(Color.parseColor("#00BBC9"));
        float numWidth = paint.measureText(num + "");
        int x1 = (int) paddingLeft;
        int y1 = this.getHeight()/2 + 50;
        canvas.drawText(num+"", x1, y1, paint1);
    }
}
