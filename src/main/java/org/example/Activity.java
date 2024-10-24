package org.example;

public enum Activity {
    Main (0),
    PostNews (11),
    DeleteNews (12);

    private int id;

    Activity(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
