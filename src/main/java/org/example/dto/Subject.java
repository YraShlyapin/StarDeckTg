package org.example.dto;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Subject {
    private int id;
    private String name;

    public Subject() {

    }

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject[] getAllSubjects() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost/api/AllSubjects")
                .addHeader("Accept", "application/json")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            Subject[] subject = new Gson().fromJson(response.body().string(), Subject[].class);
            return subject;
        } catch (IOException e ) {
            return new Subject[]{};
//            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
