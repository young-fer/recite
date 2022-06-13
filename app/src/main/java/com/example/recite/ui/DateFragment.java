package com.example.recite.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recite.R;
import com.example.recite.db.DBTool;
import com.example.recite.tool.Tool;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateFragment extends Fragment{
    private DBTool dbTool;
    BarChart barChart;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_date,container,false);
        v = view;

        RelativeLayout rlRoot = view.findViewById(R.id.rl_date);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);

        initView();
        loadBarChart();
        return view;
    }


    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        loadBarChart();
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    private void initView() {
        dbTool = new DBTool(v.getContext());
        barChart = v.findViewById(R.id.bar_chart);
    }

    private void loadBarChart() {
        getAxisValue();

        //设置无数据提示
        barChart.setNoDataTextColor(Color.parseColor("#003B4C"));
        barChart.setNoDataText("");
        //添加数据
        List<BarEntry> entries = getData();


        //数据添加到数据集
        BarDataSet dataSet = new BarDataSet(entries,"");
        //设置颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FF6363"));
        colors.add(Color.parseColor("#00BBC9"));
        dataSet.setColors(colors);
        //不显示数值
        dataSet.setDrawValues(false);
        //数据集赋值给数据对象
        BarData data = new BarData(dataSet);

        barChart.setData(data);

        //隐藏图例
        barChart.getLegend().setEnabled(false);
        //取消缩放、点击、高亮效果
        barChart.setScaleEnabled(false);
        barChart.setClickable(false);
        barChart.setHighlightPerDragEnabled(false);
        barChart.setHighlightPerTapEnabled(false);
        //设置描述位置
        Description description = barChart.getDescription();
        description.setText("");
        description.setPosition(60,20);
        description.setYOffset(20);
        barChart.setDescription(description);
        //获取X轴
        XAxis xAxis = barChart.getXAxis();
        //设置X轴在下方（默认X轴在上方）
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //给X轴添加标签
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getAxisValue()));
        //取消X轴网格线
        xAxis.setDrawGridLines(false);

        //获取左边Y轴
        YAxis leftYAxis = barChart.getAxisLeft();
        //取消Y轴网格线
        leftYAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();


//        //不显示左边Y轴的标签
//        leftYAxis.setDrawLabels(false);



        //获取右边Y轴
        YAxis rightYAxis = barChart.getAxisRight();
        //隐藏右边Y轴
        rightYAxis.setEnabled(false);
        //设置柱体宽带
        data.setBarWidth(0.3f);




    }

    private String[] getAxisValue() {
        String[] result = new String[7];
        int month, day;

        Calendar calendar = Calendar.getInstance();


        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);

        for(int i = 6; i >= 0; --i) {
            result[6-i] = month + "-" + (day - i);
        }
        return result;
    }

    private  List<BarEntry> getData() {
        List<BarEntry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        float studyCnt, reviewCnt;

        for(int i = 0 ;i < 7; ++i) {
            calendar.add(Calendar.DATE, -i );
            studyCnt = (float) dbTool.getStudyCntByTime(calendar);
            reviewCnt = (float) dbTool.getReviewCntByTime(calendar);
            entries.add(new BarEntry(6 - i,new float[]{studyCnt, reviewCnt}));
        }
        return entries;
    }
}
