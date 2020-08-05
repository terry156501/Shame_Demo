package com.terry.shame.ui.countdown;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.terry.shame.MainActivity;
import com.terry.shame.R;
import com.terry.shame.service.OkHttpUtil;
import com.terry.shame.ui.LR.LoginActivity;
import com.terry.shame.ui.LR.Person;

import java.io.IOException;

/**
 * Created By Terry on 2020/6/6
 */
public class ClockActivity extends AppCompatActivity implements MyClockView.DownCountTimerListener {
    private static final String TAG = "test";
    private MyClockView myClockView;
    private String url ="http://192.168.147.2:2033/users/mission";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(this,CheckExitService.class);
        getApplicationContext().startService(intent);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_time);

        myClockView = (MyClockView) findViewById(R.id.clockView);
        myClockView.setDownCountTimerListener(this);

        Intent intent1 = getIntent();

        String time = intent1.getStringExtra("time");
        int i = Integer.valueOf(time).intValue();
        Log.i(TAG, "time: ============"+i);
        myClockView.setDownCountTime(i*60000L);
        myClockView.startDownCountTimer();


//        if(!CommonUtil.isForeground(ClockActivity.this)){
//            String[] rest = myClockView.getClockRestTime();
//            Integer minEnd = Integer.valueOf(rest[2]).intValue();
//
//        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = getIntent();
        final String time = intent1.getStringExtra("time");
        final String name = intent1.getStringExtra("name");
        Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
        new Thread(){
            @Override
            public void run() {

                OkHttpUtil httpUtils = new OkHttpUtil();
                //转换为JSON
                final String mission = httpUtils.bolwingJson_mission(name, time,"Failed");



                //String user ="{'username':" + username + ","+"'password':"+password+"}";

                Log.d(TAG, "mission:" + mission);

                try {
                    final String result = httpUtils.login(url, mission);
                    Log.d(TAG, "结果:" + result);
                    //更新UI,在UI线程中
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        }.start();
    }
    @Override
    protected void onResume() {

        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        super.onResume();
    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.button1:
//                //设置倒计时时间
//                //获取数据
//                Intent intent = getIntent();
//
//                String time = intent.getStringExtra("time");
//                int i = Integer.valueOf(time).intValue();
//                Log.i(TAG, "time: ============"+i);
//                myClockView.setDownCountTime(i*60000L);
//                myClockView.startDownCountTimer();
//
//                break;
//
//            default:
//                break;
//        }
//    }

    @Override
    public void stopDownCountTimer() {
        Toast.makeText(this,"结束了",Toast.LENGTH_SHORT).show();
        Intent intent1 = getIntent();
        final String time = intent1.getStringExtra("time");
        final String name = intent1.getStringExtra("name");
        new Thread(){
            @Override
            public void run() {

                OkHttpUtil httpUtils = new OkHttpUtil();
                //转换为JSON
                final String mission = httpUtils.bolwingJson_mission(name, time,"Success");



                //String user ="{'username':" + username + ","+"'password':"+password+"}";

                Log.d(TAG, "mission:" + mission);

                try {
                    final String result = httpUtils.login(url, mission);
                    Log.d(TAG, "结果:" + result);
                    //更新UI,在UI线程中
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        }.start();
        Intent intent = new Intent(ClockActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
