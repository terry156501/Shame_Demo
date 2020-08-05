package com.terry.shame.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.terry.shame.R;
import com.terry.shame.ui.LR.LoginActivity;
import com.terry.shame.service.OkHttpUtil;
import com.terry.shame.ui.LR.Person;

import java.io.IOException;


/**
 * Created By Terry on 2020/6/9
 */
public class DelActivity extends AppCompatActivity {

    private TextView mEtPsw;
    private Button mBtnBack;
    private Button mBtnConfirm;
    private String url ="http://192.168.147.2:2033/users/ID";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del);

        init();
    }

    public void init(){

        mEtPsw = findViewById(R.id.et_del_psw);
        mBtnBack = findViewById(R.id.btn_del_back);
        mBtnConfirm = findViewById(R.id.btn_del_con);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DelActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mBtnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                del();
            }
        });
    }

    /*
   注销
    */
    private void del() {

        Intent intent=getIntent();
        // 实例化一个Bundle
        Bundle bundle=intent.getExtras();
        //获取里面的Persion里面的数据
        Person person= (Person) bundle.getSerializable("person");
        final String ID = person.getID();
        final String password = mEtPsw.getText().toString().trim();


        if(TextUtils.isEmpty(password)){

            Toast.makeText(DelActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(){
            @Override
            public void run() {

                OkHttpUtil httpUtils = new OkHttpUtil();
                //转换为JSON
                final String user = httpUtils.bolwingJson_del(ID, password);



                //String user ="{'username':" + username + ","+"'password':"+password+"}";

                try {
                    final String result = httpUtils.del(url, user);
                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if("200".equals(result)){

                                Toast.makeText(DelActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DelActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(DelActivity.this, "注销失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();


    }
}
