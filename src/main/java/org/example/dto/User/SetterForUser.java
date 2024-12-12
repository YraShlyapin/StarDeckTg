package org.example.dto.User;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class SetterForUser {
    public static void setActivity(int id, int activity) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody requestBody = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url("http://localhost/api/setActivity/" + id + "/" + activity)
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

    public static void setRole(int id, String role) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody requestBody = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url("http://localhost/api/setRole/" + id + "/" + role)
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

    public static void setCurrentSubject(int id, int id_subject) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody requestBody = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url("http://localhost/api/setCurrentSubject/" + id + "/" + id_subject)
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
}
