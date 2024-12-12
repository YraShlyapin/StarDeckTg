package org.example.dto.Subject;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.dto.News.News;

import java.io.IOException;

public class GetterSubject {
    public static Subject[] getAllSubjects() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost/api/AllSubjects")
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            Subject[] subject = new Gson().fromJson(response.body().string(), Subject[].class);
            return subject;
        } catch (IOException e) {
            return new Subject[]{};
        }
    }
}
