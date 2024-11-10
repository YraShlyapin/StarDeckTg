package org.example.dto.News;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class CreatorNews {

    public static News createNews(NewsContent newsContent) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(newsContent, NewsContent.class));
        Request request = new Request.Builder()
                .url("http://localhost/api/News")
                .addHeader("Content-Type", "application/json")
                .post(body)
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
