package org.example.dto.User;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GetterUser {
    public static User getUserByChatId(long chat_id) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("http://localhost/api/User/" + chat_id)
                .addHeader("Content-Type", "application/json")
                .get()
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
