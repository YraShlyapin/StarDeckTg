package org.example.dto.User;

public class User extends UserContent {
    int id;

    public int getId() {
        return id;
    }

    public User() {
    }

    public User(int chat_id, String role, int id) {
        super(chat_id, role);
        this.id = id;
    }

    public User(int chat_id, String name, String role, int id) {
        super(chat_id, name, role);
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "role=" + this.getRole() +
                "name=" + this.getName() +
                "chat_id=" + this.getChat_id() +
                '}';
    }
}
