package com.terry.shame.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.terry.shame.R;
import com.terry.shame.ui.countdown.ClockActivity;

import static android.content.ContentValues.TAG;

public class NewFragment extends Fragment {

    private Button mBtnStart;
    private EditText mEeTime;
    private EditText mEeName;

    private MyListener myListener;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_new, container, false);

        mBtnStart = view.findViewById(R.id.btn_start);
        mEeTime = view.findViewById(R.id.et_time);
        mEeName = view.findViewById(R.id.et_name);
        mBtnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String time = mEeTime.getText().toString().trim();
                String name = mEeName.getText().toString().trim();

                Log.i(TAG, "time==========="+time);
                if(TextUtils.isEmpty(time)){
                    Toast.makeText(getActivity(), "时间不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else if(Integer.valueOf(time).intValue()<=20){
                    Toast.makeText(getActivity(), "时间不能小于20分钟", Toast.LENGTH_SHORT).show();
                    return;
                }else {
//                    myListener.sendTime(time);
//                    myListener.sendName(name);
                    myListener.sendContext(time,name);
                }
            }
        });

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        myListener = (MyListener) getActivity();

    }


    public interface MyListener{
        public void sendTime(String info);
        public void sendName(String info);
        public void sendContext(String Time,String Name);

    }
}
