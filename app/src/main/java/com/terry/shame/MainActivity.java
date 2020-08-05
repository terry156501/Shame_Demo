package com.terry.shame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.terry.shame.ui.LR.Person;
import com.terry.shame.ui.countdown.ClockActivity;
import com.terry.shame.ui.dashboard.NewFragment;
import com.terry.shame.ui.personal.PersonalActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements NewFragment.MyListener {
    private TextView title;
    private ImageView avatar;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.text_title);
        final Intent intent=getIntent();
        // 实例化一个Bundle
        Bundle bundle=intent.getExtras();
        //获取里面的Persion里面的数据
        Person person= (Person) bundle.getSerializable("person");
        title.setText("ID:"+person.getID());


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);



        //设置底部菜单
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        avatar = findViewById(R.id.btn_avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, PersonalActivity.class);
                startActivity(intent1);
            }
        });


    }


    @Override
    public void sendTime(String info) {
        Intent intent = new Intent(MainActivity.this,ClockActivity.class);
        intent.putExtra("time",info);
        startActivity(intent);
    }
    @Override
    public void sendName(String info) {
        Intent intent = new Intent(MainActivity.this,ClockActivity.class);
        intent.putExtra("name",info);
        startActivity(intent);
    }
    @Override
    public void sendContext(String Time,String Name) {
        Intent intent = new Intent(MainActivity.this,ClockActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",Name);
        bundle.putString("time",Time);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
