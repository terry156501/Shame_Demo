package com.terry.shame.service;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * Created By Terry on 2020/6/4
 */


/**
 * Created by Administrator on 2016-03-27.
 */
public class OkHttpUtil {

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public String login(String url, String json) throws IOException {
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(JSON, json);
        //RequestBody formBody = new FormEncodingBuilder()
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;


    }

    public String reg(String url, String json) throws IOException {
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(JSON, json);
        //RequestBody formBody = new FormEncodingBuilder()
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;


    }

    public String del(String url, String json) throws IOException {
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(JSON, json);
        //RequestBody formBody = new FormEncodingBuilder()
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;


    }

    public String modify(String url, String json) throws IOException {
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(JSON, json);
        //RequestBody formBody = new FormEncodingBuilder()
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;


    }

    public String bolwingJson(String phone, String password) {
//        return "{'phone':" + phone + "," + "'password':" + password + "}";
        return "{"+'"'+"phone"+'"'+":"+'"'+phone+'"'+","+'"'+"password"+'"'+":"+'"'+password+'"'+"}";
        //   "{'username':" + username + ","+"'password':"+password+"}";
    }

    public String bolwingJson_Log(String ID, String password) {
//        return "{'phone':" + phone + "," + "'password':" + password + "}";
        return "{"+'"'+"ID"+'"'+":"+'"'+ID+'"'+","+'"'+"password"+'"'+":"+'"'+password+'"'+"}";
        //   "{'username':" + username + ","+"'password':"+password+"}";
    }

    public String bolwingJson_modify(String ID, String OldPsw, String NewPsw) {
//        return "{'phone':" + phone + "," + "'password':" + password + "}";
        return "{"+'"'+"ID"+'"'+":"+'"'+ID+'"'+","+'"'+"oldPasswd"+'"'+":"+'"'+OldPsw+'"'+","+'"'+"newPasswd"+'"'+":"+'"'+NewPsw+'"'+"}";
        //   "{'username':" + username + ","+"'password':"+password+"}";
    }

    public String bolwingJson_del(String ID, String password) {
//        return "{'phone':" + phone + "," + "'password':" + password + "}";
        return "{"+'"'+"ID"+'"'+":"+'"'+ID+'"'+","+'"'+"password"+'"'+":"+'"'+password+'"'+"}";
        //   "{'username':" + username + ","+"'password':"+password+"}";
    }

    public String bolwingJson_mission(String name, String time,String Status) {
//        return "{'phone':" + phone + "," + "'password':" + password + "}";
        return "{"+'"'+"name"+'"'+":"+'"'+name+'"'+","+'"'+"time"+'"'+":"+'"'+time+'"'+","+'"'+"status"+'"'+":"+'"'+Status+'"'+"}";
        //   "{'username':" + username + ","+"'password':"+password+"}";
    }
}

