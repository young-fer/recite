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

public class ControlButton extends AppCompatButton {
    private String tag;
    public ControlButton(Context context) {
        super(context);
    }

    public ControlButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化一些属性
        init();
        //获取自定义的属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.control_button);
        tag = ta.getString(R.styleable.control_button_control_tag);

//        setBackgroundColor(Color.parseColor("#00000000"));

        init();
        ta.recycle();
    }

    public void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制第一行文字
        Paint paint = new Paint();
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setTextSize(45);
        paint.setColor(Color.parseColor("#000000"));
        float tagWidth = paint.measureText(tag);
        int x = (int) (this.getWidth() - tagWidth) / 2;
        int y = (int) this.getHeight() / 2 + 5;
        canvas.drawText(tag, x, y, paint);

        drawTagRect(tag, canvas);
    }

    private void drawTagRect(String tag, Canvas canvas) {
        //绘制背景
        Paint t_rect = new Paint();
        if(tag.equals("不认识")) {
            t_rect.setColor(Color.parseColor("#F13D3D"));
        }else if (tag.equals("认识")) {
            t_rect.setColor(Color.parseColor("#54F16F"));
        }else {
            t_rect.setColor(Color.parseColor("#9F9898"));
        }


        int left = (int)(this.getWidth()-80) / 2;
        int right = (int)(this.getWidth()-80) / 2 + 80;
        int top = (int) this.getHeight() / 2 +30;
        int bottom = (int) this.getHeight() / 2 + 40;
        canvas.drawRoundRect(new RectF(left, top, right, bottom),15,15,t_rect); //画圆角矩形
    }

}
