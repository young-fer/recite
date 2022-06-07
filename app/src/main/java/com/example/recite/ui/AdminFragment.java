package com.example.recite.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.example.recite.R;
import com.example.recite.tool.Tool;

public class AdminFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_admin,container,false);
        RelativeLayout rlRoot = view.findViewById(R.id.rl_admin);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);

        initRoundedDrawable(view);
        return view;
    }

    private void initRoundedDrawable(View view) {
        ImageView imageView = view.findViewById(R.id.iv_head);
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.head_image); //获取Bitmap图片
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), src); //创建RoundedBitmapDrawable对象
        roundedBitmapDrawable.setCornerRadius(500); //设置圆角Radius（根据实际需求）
        roundedBitmapDrawable.setAntiAlias(true); //设置抗锯齿
        imageView.setImageDrawable(roundedBitmapDrawable); //显示圆角
    }

}
