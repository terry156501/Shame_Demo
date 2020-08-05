package com.terry.shame.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.terry.shame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


public class HistoryFragment extends Fragment {
    public ListView lv;
    public ArrayList<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    public  JSONObject object;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        list.clear();
        lv= view.findViewById(R.id.lv_list);
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient=new OkHttpClient();
                //服务器返回的地址
                Request request=new Request.Builder()
                        .url("http://192.168.147.2:2033/users/mission").build();
                try {
                    Response response=okHttpClient.newCall(request).execute();
                    //获取到数据
                    String date=response.body().string();
                    Log.i(TAG, "============="+date);
                    //把数据传入解析josn数据方法
                    jsonJX(date);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();;


        return view;
    }


    private void jsonJX(String date) {
        //判断数据是空
        Log.i(TAG, "++++++++++++++++"+date);
        if(date!=null){
            try {
                //将字符串转换成jsonObject对象
                JSONObject jsonObject = new JSONObject(date);
                Log.i(TAG, "++++++++++++++++++++"+jsonObject);
                //获取返回数据中flag的值
                String resultCode = jsonObject.getString("flag");
                //如果返回的值是success则正确
                if (resultCode.equals("success")) {
                    //获取到json数据中里的activity数组内容
                    JSONArray resultJsonArray = jsonObject.getJSONArray("activity");
                    //遍历
                    for(int i=0;i<resultJsonArray.length();i++){
                        object=resultJsonArray.getJSONObject(i);

                        Map<String, Object> map=new HashMap<String, Object>();

                        try {
                            //获取到json数据中的activity数组里的内容name
                            String name = object.getString("name");
                            //获取到json数据中的activity数组里的内容startTime
                            String shijian=object.getString("time");
                            String status = object.getString("status");
                            //存入map
                            map.put("name", name);
                            map.put("shijian", shijian);
                            map.put("status", status);
                            //ArrayList集合
                            list.add(map);
                            Log.i(TAG, "jsonJX: =============="+map);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }




    }
    //Handler运行在主线程中(UI线程中)，  它与子线程可以通过Message对象来传递数据
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Mybaseadapter list_item=new Mybaseadapter();
                    lv.setAdapter((ListAdapter) list_item);
                    break;
            }


        }
    };
    //listview的适配器
    public class Mybaseadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();

        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();

                convertView = getLayoutInflater().inflate(R.layout.history_item, null);
                viewHolder.textView = convertView.findViewById(R.id.tv_mission_name);
                viewHolder.shijian = convertView.findViewById(R.id.tv_mission_time);
                viewHolder.status = convertView.findViewById(R.id.tv_mission_status);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Log.i(TAG, "=========================test"+list.get(position).get("name").toString());

            viewHolder.textView.setText(list.get(position).get("name").toString());
            viewHolder.shijian.setText(list.get(position).get("shijian").toString());
            viewHolder.status.setText(list.get(position).get("status").toString());

            return convertView;
        }

    }

    final static class ViewHolder {
        TextView textView;
        TextView shijian;
        TextView status;
    }

}
