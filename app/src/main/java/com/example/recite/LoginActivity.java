package com.example.recite;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.recite.tool.Tool;

public class LoginActivity extends AppCompatActivity {
    private CheckBox checkbox;
    private Button login_btn, register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RelativeLayout rlRoot = findViewById(R.id.rl_root);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);
        initView();


    }

    private void initView() {
        setFullScreen();

        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);
        checkbox = findViewById(R.id.checkbox);
        Drawable drawable = getDrawable(R.drawable.radio_btn);
        drawable.setBounds(0, 0, 65, 65);
        checkbox.setCompoundDrawables(drawable, null, null, null);
        checkbox.setCompoundDrawablePadding(30);

        login_btn.setOnClickListener(new ClickListener());
        register_btn.setOnClickListener(new ClickListener());
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (checkbox.isChecked()) {
                switch (view.getId()) {
                    case R.id.login_btn:
//                    startActivity(new Intent(LoginActivity.this, LoginCard.class));
                        Intent intent = new Intent(LoginActivity.this, LoginCard.class);
                        startActivityForResult(intent, 1);

                        break;
                    case R.id.register_btn:
                        startActivity(new Intent(LoginActivity.this, RegisterCard.class));
                        break;
                    default:
                        break;
                }
            }else {
                Toast.makeText(LoginActivity.this, "请先阅读并勾选同意", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            boolean isLogin = data.getExtras().getBoolean("isLogin");
            if (isLogin) {
                finish();
            }
        }
    }

    /**
     * 获得刘海区域信息
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

}