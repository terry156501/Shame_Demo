package com.terry.shame.ui.countdown;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.terry.shame.R;

/**
 * Created By Terry on 2020/6/6
 */

public class CountDownActivity extends AppCompatActivity {

    private CountDownProgressBar cpb_countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        Button btn_start = findViewById(R.id.btn_start);
        cpb_countdown = (CountDownProgressBar) findViewById(R.id.cpb_countdown);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cpb_countdown.setDuration(1000000, new CountDownProgressBar.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        Toast.makeText(CountDownActivity.this, "完成了", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

