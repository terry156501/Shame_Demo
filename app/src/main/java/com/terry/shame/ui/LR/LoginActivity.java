package com.terry.shame.ui.LR;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.terry.shame.MainActivity;
import com.terry.shame.R;
import com.terry.shame.service.OkHttpUtil;

import java.io.IOException;

/**
 * Created By Terry on 2020/6/1
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="LoginActivity" ;
    //用户名
    private EditText mEtPhone;
    //密码
    private EditText mEtPwd;
    //登录按键
    private Button mBtnLogin;
    private Button mBtnReg;

    private String url ="http://192.168.147.2:2033/users/ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    /**
     * 初始化组件
     */
    private void init() {

        mEtPhone = findViewById(R.id.et_user_phone);
        mEtPwd = findViewById(R.id.et_psw);

        mBtnLogin = findViewById(R.id.btn_login);
        mBtnReg = findViewById(R.id.btn_toregister);

        mBtnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }


    /*
    登录
     */
    private void login() {

        final String username = mEtPhone.getText().toString().trim();
        final String password = mEtPwd.getText().toString().trim();


        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){

            Toast.makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(){
            @Override
            public void run() {

                OkHttpUtil httpUtils = new OkHttpUtil();
                //转换为JSON
                final String user = httpUtils.bolwingJson_Log(username, password);



                //String user ="{'username':" + username + ","+"'password':"+password+"}";

                Log.d(TAG, "user:" + user);

                try {
                    final String result = httpUtils.login(url, user);
                    Log.d(TAG, "结果:" + result);
                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if("200".equals(result)){

                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                Person person = new Person();
                                person.setID(username);
                                person.setPassword(password);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("person",person);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }else{
                                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        }.start();


    }
}

