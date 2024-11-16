package org.example.dto.User;

public class UserContent {
    private long chat_id;
    private String name;
    private String role;

    public UserContent() {
    }

    public UserContent(long chat_id) {
        this.chat_id = chat_id;
    }

    public UserContent(long chat_id, String role) {
        this.chat_id = chat_id;
        this.role = role;
    }

    public UserContent(long chat_id, String name, String role) {
        this.chat_id = chat_id;
        this.name = name;
        this.role = role;
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
