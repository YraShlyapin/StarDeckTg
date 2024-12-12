package org.example.dto.Homework;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.dto.News.News;

import java.io.IOException;

public class DeleterHomework {
    public static Homework deleteHomework(int id) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost/api/Homework/" + id)
                .addHeader("Content-Type", "application/json")
                .delete()
                .build();
        try {
            Response response = client.newCall(request).execute();

            Homework homework = new Gson().fromJson(response.body().string(), Homework.class);
            return homework;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
