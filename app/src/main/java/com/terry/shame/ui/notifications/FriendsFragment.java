package com.terry.shame.ui.notifications;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.terry.shame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class FriendsFragment extends Fragment {

    public ListView lv;
    public ArrayList<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    public JSONObject object;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        list.clear();
        lv= view.findViewById(R.id.lv_friends_list);
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient=new OkHttpClient();
                //服务器返回的地址
                Request request=new Request.Builder()
                        .url("http://192.168.147.2:8080/MyNetServer/json/friends.json").build();
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
                            String name = object.getString("name");
                            String ID=object.getString("ID");
                            String status = object.getString("status");
                            String level=object.getString("level");
                            String avatar = object.getString("avatar");
                            //存入map
                            map.put("name", name);
                            map.put("status", status);
                            map.put("ID", ID);
                            map.put("level", level);
                            map.put("avatar", avatar);
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

                convertView = getLayoutInflater().inflate(R.layout.friends_item, null);
                viewHolder.avatar = convertView.findViewById(R.id.iv_avatar);
                viewHolder.ID = convertView.findViewById(R.id.tv_id);
                viewHolder.status = convertView.findViewById(R.id.tv_status);
                viewHolder.name = convertView.findViewById(R.id.tv_name);
                viewHolder.level = convertView.findViewById(R.id.tv_level);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

//            String img = ；
//            viewHolder.avatar.setImageDrawable(getResources().getDrawable(R.id.));
            String avatar = list.get(position).get("avatar").toString();
            Log.i(TAG, "avatar====================="+avatar);
            switch (avatar){
                case "a":
                    viewHolder.avatar.setImageResource(R.drawable.a01_24dp);
                case "b":
                    viewHolder.avatar.setImageResource(R.drawable.a02_24dp);
                default:
                    viewHolder.avatar.setImageResource(R.drawable.a02_24dp);


            }

            viewHolder.ID.setText(list.get(position).get("ID").toString());
            viewHolder.status.setText(list.get(position).get("status").toString());
            viewHolder.name.setText(list.get(position).get("name").toString());
            viewHolder.level.setText(list.get(position).get("level").toString());

            return convertView;
        }

    }

    final static class ViewHolder {
        ImageView avatar;
        TextView ID;
        TextView name;
        TextView status;
        TextView level;
    }

}