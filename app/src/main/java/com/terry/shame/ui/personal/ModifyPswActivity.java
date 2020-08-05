package com.terry.shame.ui.personal;

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
import com.terry.shame.ui.LR.Person;

import java.io.IOException;

/**
 * Created By Terry on 2020/6/9
 */
public class ModifyPswActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;
    //用户名
    private EditText mEtID;
    //密码
    private EditText mEtOldPsw;
    private EditText mEtNewPsw;
    //注册按键
    private Button mBtnC;

    private String url ="http://192.168.147.2:2033/users/ID/password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpassword);

        init();
    }

    /**
     * 初始化组件
     */
    private void init() {

        mEtID = findViewById(R.id.et_id);
        mEtOldPsw = findViewById(R.id.et_old_psw);
        mEtNewPsw = findViewById(R.id.et_new_psw);

        mBtnC = findViewById(R.id.btn_confirm);

        mBtnC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                modify();
            }
        });

    }


    /*
    修改密码
     */
    private void modify() {

        final String ID = mEtID.getText().toString().trim();
        final String OldPsw = mEtOldPsw.getText().toString().trim();
        final String NewPsw = mEtNewPsw.getText().toString().trim();


        if(TextUtils.isEmpty(ID) || TextUtils.isEmpty(OldPsw)){

            Toast.makeText(ModifyPswActivity.this, "ID或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty((NewPsw))){
            Toast.makeText(ModifyPswActivity.this,"输入新密码",Toast.LENGTH_SHORT).show();
            return;
        }


        new Thread(){
            @Override
            public void run() {

                OkHttpUtil httpUtils = new OkHttpUtil();
                //转换为JSON
                String user = httpUtils.bolwingJson_modify(ID, OldPsw,NewPsw);



                //String user ="{'username':" + username + ","+"'password':"+password+"}";

                Log.d(TAG, "user:" + user);

                try {
                    final String result = httpUtils.modify(url, user);
                    Log.d(TAG, "结果:" + result);
                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if("200".equals(result)){

                                Toast.makeText(ModifyPswActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Person person = new Person();
                                person.setID(ID);
                                person.setPassword(NewPsw);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("person",person);
                                Intent intent = new Intent(ModifyPswActivity.this, MainActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }else{
                                Toast.makeText(ModifyPswActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
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