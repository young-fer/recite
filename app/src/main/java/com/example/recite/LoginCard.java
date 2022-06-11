package com.example.recite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginCard extends AppCompatActivity {
    private DBTool dbTool;
    private EditText ev_user, ev_pwd;
    private Button login_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card);

        RelativeLayout rlRoot = findViewById(R.id.rl_login_card);
        //  设置根布局的paddingTop
        rlRoot.setPadding(0, Tool.contentPadding, 0, 0);

        initView();

    }

    private void initView() {

        dbTool = new DBTool(LoginCard.this);

        ev_user = findViewById(R.id.ev_user);
        ev_pwd = findViewById(R.id.ev_pwd);
        login_btn = findViewById(R.id.login_btn);
        Drawable UserDrawable = getDrawable(R.drawable.user);
        UserDrawable.setBounds(0, 0, 65, 65);
        ev_user.setCompoundDrawables(UserDrawable, null, null, null);
        ev_user.setCompoundDrawablePadding(30);

        Drawable PwdDrawable = getDrawable(R.drawable.pwd);
        PwdDrawable = getDrawable(R.drawable.pwd);
        PwdDrawable.setBounds(0, 0, 65, 65);
        ev_pwd.setCompoundDrawables(PwdDrawable, null, null, null);
        ev_pwd.setCompoundDrawablePadding(30);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ev_pwd.getText().toString();
                String pwd = ev_pwd.getText().toString();
                User user = new User(username, pwd);

                if (dbTool.Login(user)) {
                    startActivity(new Intent(LoginCard.this, MainActivity.class));
                    Toast.makeText(LoginCard.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra("isLogin", true);
                    setResult(1, i);
                    finish();
                }else {
                    Toast.makeText(LoginCard.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}