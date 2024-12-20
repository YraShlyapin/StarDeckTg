package org.example.dto.Homework;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class UpdaterStatusHomework {

    public static void UpdateStatus(int id) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody requestBody = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url("http://localhost/api/changeStatus/" + id)
                .addHeader("Content-Type", "application/json")
                .put(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            response.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static Homework[] getAllHomeworks() {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        Request request = new Request.Builder()
//                .url("http://localhost/api/AllHomeworks")
//                .addHeader("Content-Type", "application/json")
//                .get()
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//
//            Homework[] news = new Gson().fromJson(response.body().string(), Homework[].class);
//            return news;
//        } catch (IOException e) {
//            return new Homework[]{};
//        }
//    }
//
//    public static Homework getHomeworkById(int id) {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        Request request = new Request.Builder()
//                .url("http://localhost/api/Homework/" + id)
//                .addHeader("Content-Type", "application/json")
//                .get()
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//
//            Homework news = new Gson().fromJson(response.body().string(), Homework.class);
//            return news;
//        } catch (IOException e) {
//            return new Homework();
//        }
//    }
}
