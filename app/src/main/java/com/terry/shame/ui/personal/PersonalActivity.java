package com.terry.shame.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.terry.shame.R;
import com.terry.shame.ui.LR.Person;

/**
 * Created By Terry on 2020/6/9
 */
public class PersonalActivity extends AppCompatActivity {
    private static final String TAG ="PersonalActivity" ;
    private Button mBtnModify;
    private Button mBtnDel;

    private String url ="http://192.168.147.2:2033/users/ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        init();
    }

    /**
     * 初始化组件
     */
    private void init() {

        mBtnModify = findViewById(R.id.btn_cpsw);
        mBtnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalActivity.this,ModifyPswActivity.class);
                startActivity(intent);
            }
        });
        mBtnDel = findViewById(R.id.btn_del);
        mBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalActivity.this,DelActivity.class);
                startActivity(intent);
            }
        });


    }

}

