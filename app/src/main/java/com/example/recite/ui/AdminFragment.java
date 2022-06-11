package com.example.recite.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.example.recite.R;
import com.example.recite.StudyPlanActivity;
import com.example.recite.db.DBTool;
import com.example.recite.tool.Tool;

public class AdminFragment extends Fragment{
    private DBTool dbTool;
    private TextView tv_has_study, tv_not_study;
    private RelativeLayout rl_stu_plan;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_admin,container,false);
        this.view = view;
        RelativeLayout rlRoot = view.findViewById(R.id.rl_admin);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);

        initView();
        initRoundedDrawable(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int hasStuCnt = dbTool.getHasStuWordCnt();
        int hasNotStuCnt = dbTool.getNotStuWordCnt();
        tv_has_study.setText(String.valueOf(hasNotStuCnt));
        tv_not_study.setText(String.valueOf(hasStuCnt));
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        int hasStuCnt = dbTool.getHasStuWordCnt();
        int hasNotStuCnt = dbTool.getNotStuWordCnt();
        tv_has_study.setText(String.valueOf(hasNotStuCnt));
        tv_not_study.setText(String.valueOf(hasStuCnt));

        return super.onCreateAnimation(transit, enter, nextAnim);
    }



    private void initRoundedDrawable(View view) {
        ImageView imageView = view.findViewById(R.id.iv_head);
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.head_image); //获取Bitmap图片
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), src); //创建RoundedBitmapDrawable对象
        roundedBitmapDrawable.setCornerRadius(500); //设置圆角Radius（根据实际需求）
        roundedBitmapDrawable.setAntiAlias(true); //设置抗锯齿
        imageView.setImageDrawable(roundedBitmapDrawable); //显示圆角
    }

    private void initView() {
        dbTool = new DBTool(view.getContext());

        tv_has_study = view.findViewById(R.id.tv_has_study);
        tv_not_study = view.findViewById(R.id.tv_not_study);
        rl_stu_plan =  view.findViewById(R.id.rl_stu_plan);

        rl_stu_plan.setOnClickListener(new ClickListener());

    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(view.getContext(), StudyPlanActivity.class);
            startActivity(intent);
        }
    }

}
