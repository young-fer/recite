package com.example.recite.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recite.MainActivity;
import com.example.recite.R;
import com.example.recite.ReciteActivity;
import com.example.recite.ReviewActivity;
import com.example.recite.WordListActivity;
import com.example.recite.component.StuButton;
import com.example.recite.db.DBTool;
import com.example.recite.tool.Tool;

public class IndexFragment extends Fragment{
    private StuButton learnBtn, reviewBtn;
    private DBTool dbTool;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_index,container,false);
        RelativeLayout rlRoot = view.findViewById(R.id.rl_index);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);

        initView(view);
        setOnclick(view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        learnBtn.setNum(dbTool.getHasStuWordCnt());
        learnBtn.invalidate();
        reviewBtn.setNum(dbTool.getReviewWordCnt());
        reviewBtn.invalidate();
    }

    public void initView(View view) {
        dbTool = new DBTool(view.getContext());
        learnBtn = (StuButton) view.findViewById(R.id.learn_btn);
        reviewBtn = (StuButton) view.findViewById(R.id.review_btn);

    }


    /**
     * 设置点击事件
     */
    private void setOnclick(View view) {
        learnBtn.setOnClickListener(new BtnClickListener());
        reviewBtn.setOnClickListener(new BtnClickListener());
    }

    /**
     * 定义内部类，实现OnClickListener接口
     */
    //定义一个内部类,实现View.OnClickListener接口,并重写onClick()方法
    class BtnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.learn_btn:
                    Intent intent = new Intent(getActivity(), ReciteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.review_btn:
                    Intent intent1 = new Intent(getActivity(), ReviewActivity.class);
                    startActivity(intent1);
                    break;
                default:
                    break;
            }
        }
    }
}
