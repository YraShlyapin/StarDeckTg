package org.example.dto.News;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class DeleterNews {
    public News deleteNews(int id) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(this, NewsContent.class));
        Request request = new Request.Builder()
                .url("http://localhost/api/News/" + id)
                .addHeader("Content-Type", "application/json")
                .delete()
                .build();
        try {
            Response response = client.newCall(request).execute();

            News news = new Gson().fromJson(response.body().string(), News.class);
            return news;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
