package org.example.dto.News;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GetterNews {
    public News[] getAllNews() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost/api/AllNews")
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            News[] news = new Gson().fromJson(response.body().string(), News[].class);
            return news;
        } catch (IOException e) {
            return new News[]{};
//            throw new RuntimeException(e);
        }
    }

    public News[] getNewsById(int id) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost/api/News/" + id)
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            News[] news = new Gson().fromJson(response.body().string(), News[].class);
            return news;
        } catch (IOException e) {
            return new News[]{};
        }
    }
}
