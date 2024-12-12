package org.example.dto.Homework;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class CreatorHomework {
    public static Homework createHomework(HomeworkContent homeworkContent) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(homeworkContent, HomeworkContent.class));
        Request request = new Request.Builder()
                .url("http://localhost/api/Homework")
                .addHeader("Content-Type", "application/json")
                .post(body)
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
