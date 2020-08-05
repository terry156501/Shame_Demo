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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Terry on 2020/6/1
 */

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    //用户名
    private EditText mEtPhone;
    //密码
    private EditText mEtPwd;
    private EditText mEtPwdA;
    //注册按键
    private Button mBtnReg;
    public JSONObject object;
    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


    private String url = "http://192.168.147.2:2033/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    /**
     * 初始化组件
     */
    private void init() {

        mEtPhone = findViewById(R.id.et_phone);
        mEtPwd = findViewById(R.id.et_psw);
        mEtPwdA = findViewById(R.id.et_psw_again);

        mBtnReg = findViewById(R.id.btn_register);

        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }


    /*
    注册
     */
    private void register() {

        final String phone = mEtPhone.getText().toString().trim();
        final String password = mEtPwd.getText().toString().trim();
        final String pswA = mEtPwdA.getText().toString().trim();


        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {

                Toast.makeText(RegisterActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty((pswA))) {
                Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.equals(pswA)) {
            Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
//        OkHttpUtil httpUtils = new OkHttpUtil();
//        String user = httpUtils.bolwingJson(phone, password);
//        final String result = httpUtils.bolwingJson(url, user);
//        Log.d(TAG, "结果:" + result);


        new Thread() {
            @Override
            public void run() {

                OkHttpUtil httpUtils = new OkHttpUtil();
                //转换为JSON
                String user = httpUtils.bolwingJson(phone, password);


                //String user ="{'username':" + username + ","+"'password':"+password+"}";

                Log.d(TAG, "user:" + user);

                try {
                    final String result = httpUtils.reg(url, user);
                    Log.d(TAG, "结果:" + result);
                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            jsonJX(result);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();

    }

    private void jsonJX(String date) {
        //判断数据是空
        Log.i(TAG, "++++++++++++++++" + date);
        if (date != null) {
            try {
                //将字符串转换成jsonObject对象
                JSONObject jsonObject = new JSONObject(date);
                Log.i(TAG, "++++++++++++++++++++" + jsonObject);
                //获取返回数据中flag的值
                String resultCode = jsonObject.getString("code");

                if (resultCode.equals("200")) {
                    //获取到json数据中里的activity数组内容
                    JSONArray resultJsonArray = jsonObject.getJSONArray("basicinfo");
                    Log.i(TAG, "in============="+"t");
                    //遍历
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        object = resultJsonArray.getJSONObject(i);

                        Map<String, Object> map = new HashMap<String, Object>();

                        try {
                            String phone = object.getString("phone");
                            String ID = object.getString("ID");
                            String psw = object.getString("password");
                            //存入person
                            Person person = new Person();
                            person.setID(ID);
                            person.setPhone(phone);
                            person.setPassword(psw);
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("person",person);
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            Log.i(TAG, "jsonJX: ==============" + map);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                }else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}

