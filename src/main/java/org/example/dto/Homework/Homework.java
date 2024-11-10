package org.example.dto.Homework;

public class Homework extends HomeworkContent {
    private int id;

    public Homework() {
        super();
    }

    public Homework(String title, String content) {
        super(title, content);
    }

    public Homework(String title, String description, boolean status, int id_subject, int id) {
        super(title, description, status, id_subject);
        this.id = id;
    }

    public Homework(int id, String title, String content) {
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
        return "Homework{" +
                "id=" + id +
                '}';
    }
}