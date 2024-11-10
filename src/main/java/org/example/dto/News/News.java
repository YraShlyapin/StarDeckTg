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

    @Override
    public String toString() {
        return "News{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", content='" + this.getContent() + '\'' +
                '}';
    }
}
