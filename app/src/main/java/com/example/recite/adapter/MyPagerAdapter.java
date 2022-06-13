package com.example.recite.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;
import com.example.recite.R;
import com.example.recite.bean.Word;

public class MyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Word.Sentence> mData;

    public MyPagerAdapter(Context context , ArrayList<Word.Sentence> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.vp_item,null);
        TextView tv_sentence = (TextView) view.findViewById(R.id.tv_sentence);
        TextView tv_mean = (TextView) view.findViewById(R.id.tv_mean);
        tv_sentence.setText(mData.get(position).sContent);
        tv_mean.setText(mData.get(position).sCn);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}