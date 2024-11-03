package org.example.dto.News;

public class News extends NewsContent {
    private int id;

    public News() {
        super();
    }

    public News(String title, String content) {
        super(title, content);
    }

    public News(int id, String title, String content) {
        super(title, content);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }









//    public News[] deleteNews(int[] ids) {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(this, NewsContent.class));
//        System.out.println(new Gson().toJson(this));
//        Request request = new Request.Builder()
//                .url("http://localhost/api/News")
//                .post(body)
//                .addHeader("Content-Type", "application/json")
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//
//            News news = new Gson().fromJson(response.body().string(), News.class);
//            return news;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", content='" + this.getContent() + '\'' +
                '}';
    }
}
