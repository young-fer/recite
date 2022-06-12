package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.recite.db.DBTool;
import com.example.recite.tool.Tool;
import com.example.recite.ui.AdminFragment;
import com.example.recite.ui.DateFragment;
import com.example.recite.ui.IndexFragment;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup rg_tab_bar;
    private RadioButton rb_index;

    //Fragment Object
    private IndexFragment indexFragment;
    private DateFragment dateFragment;
    private AdminFragment adminFragment;
    private FragmentManager fManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBTool dbTool = new DBTool(MainActivity.this);
        try {
            dbTool.CopyDBile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initView();
    }

    /**
     * 初始化界面
     */
    public void initView() {
        setFullScreen();
        initRG();
    }

    /**
     * 设置刘海屏全屏
     */
    public void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        Tool.contentPadding = getStatusBarHeight(this);
    }

    /**
     * 获得刘海区域信息
     */
    @TargetApi(28)
    public void getNotchParams() {
        final View decorView = getWindow().getDecorView();
        if (decorView != null) {
            decorView.post(new Runnable() {
                @Override
                public void run() {
                    WindowInsets windowInsets = decorView.getRootWindowInsets();
                    if (windowInsets != null) {
                        // 当全屏顶部显示黑边时，getDisplayCutout()返回为null
                        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                        Log.e("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                        Log.e("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                        Log.e("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                        Log.e("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());
                        // 获得刘海区域
                        List<Rect> rects = displayCutout.getBoundingRects();
                        if (rects == null || rects.size() == 0) {
                            Log.e("TAG", "不是刘海屏");
                        } else {
                            Log.e("TAG", "刘海屏数量:" + rects.size());
                            for (Rect rect : rects) {
                                Log.e("TAG", "刘海屏区域：" + rect);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 设置多选按钮大小
     * @param r_id
     * @param d_id
     */
    public void setRBtnSize(int r_id, int d_id) {
        RadioButton rbtn = (RadioButton) findViewById(r_id);
        Drawable drawable = getResources().getDrawable(d_id);
        drawable.setBounds(0,0,100,100);
        rbtn.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 初始化底部导航栏
     */
    public void initRG() {
        setRBtnSize(R.id.rb_index, R.drawable.tab_menu_index);
        setRBtnSize(R.id.rb_date, R.drawable.tab_menu_date);
        setRBtnSize(R.id.rb_admin, R.drawable.tab_menu_admin);

        fManager = getSupportFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_index = (RadioButton) findViewById(R.id.rb_index);
        rb_index.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.rb_index:
                if(indexFragment == null){
                    indexFragment = new IndexFragment();
                    fTransaction.add(R.id.ry_content, indexFragment);
                }else{
                    fTransaction.show(indexFragment);
                }
                break;
            case R.id.rb_date:
                if(dateFragment == null){
                    dateFragment = new DateFragment();
                    fTransaction.add(R.id.ry_content,dateFragment);
                }else{
                    fTransaction.show(dateFragment);
                }
                break;
            case R.id.rb_admin:
                if(adminFragment == null){
                    adminFragment = new AdminFragment();
                    fTransaction.add(R.id.ry_content,adminFragment);
                }else{
                    fTransaction.show(adminFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    /**
     * 隐藏所有Fragment
     * @param fragmentTransaction
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(indexFragment != null)fragmentTransaction.hide(indexFragment);
        if(dateFragment != null)fragmentTransaction.hide(dateFragment);
        if(adminFragment != null)fragmentTransaction.hide(adminFragment);
    }
}