package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.recite.bean.User;
import com.example.recite.db.DBTool;
import com.example.recite.tool.Tool;
import com.google.android.material.tabs.TabLayout;

public class RegisterCard extends AppCompatActivity {
    DBTool dbTool;
    private EditText ev_user, ev_pwd;
    private Button register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_card);

        RelativeLayout rlRoot = findViewById(R.id.rl_register_card);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);
        initView();
    }

    private void initView() {
        dbTool = new DBTool(RegisterCard.this);

        ev_user = findViewById(R.id.ev_user);
        ev_pwd = findViewById(R.id.ev_pwd);
        register_btn = findViewById(R.id.register_btn);


        Drawable UserDrawable = getDrawable(R.drawable.user);
        UserDrawable.setBounds(0, 0, 65, 65);
        ev_user.setCompoundDrawables(UserDrawable, null, null, null);
        ev_user.setCompoundDrawablePadding(30);

        Drawable PwdDrawable = getDrawable(R.drawable.pwd);
        PwdDrawable = getDrawable(R.drawable.pwd);
        PwdDrawable.setBounds(0, 0, 65, 65);
        ev_pwd.setCompoundDrawables(PwdDrawable, null, null, null);
        ev_pwd.setCompoundDrawablePadding(30);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = ev_pwd.getText().toString();
                String pwd = ev_pwd.getText().toString();
                if (username.length() >= 6 && pwd.length() >= 6) {
                    User user = new User(username, pwd);
                    dbTool.Register(user);
                    Toast.makeText(RegisterCard.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(RegisterCard.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}