package org.example.dto.Homework;

public class HomeworkContent {
    private String title;
    private String description;
    private boolean status;
    private int id_subject;

    public HomeworkContent() {}

    public HomeworkContent(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public HomeworkContent(String title, String description, boolean status, int id_subject) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id_subject = id_subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId_subject() {
        return id_subject;
    }

    public void setId_subject(int id_subject) {
        this.id_subject = id_subject;
    }

    public String format() {
        return "*" + this.title + "*\n\n"
                + this.description + "\n\n"
                + (this.status ? "сделано" : "несделано");
    }
}
