package org.example.dto.User;

import com.google.gson.Gson;
import okhttp3.*;
import org.example.dto.News.NewsContent;

import java.io.IOException;

public class CreatorUser {
    public static User createUser(UserContent userContent) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        System.out.println(new Gson().toJson(userContent, UserContent.class));
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(userContent, UserContent.class));
        Request request = new Request.Builder()
                .url("http://localhost/api/User")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();

            User user = new Gson().fromJson(response.body().string(), User.class);
            return user;
        } catch (IOException e) {
            return new User();
        }
    }
}
