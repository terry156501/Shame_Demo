package com.terry.shame.ui.countdown;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * Created By Terry on 2020/6/8
 */
public class CheckExitService extends Service {

    private String packageName = "com.terry.shame";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Toast.makeText(CheckExitService.this, "App要退出了", Toast.LENGTH_SHORT).show();
    }

    //service异常停止的回调
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ActivityManager activtyManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activtyManager.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            if (packageName.equals(runningAppProcesses.get(i).processName)) {
                Toast.makeText(this, "app还在运行中", Toast.LENGTH_LONG).show();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(CheckExitService.this, "App检测服务开启了", Toast.LENGTH_SHORT).show();
    }
}
