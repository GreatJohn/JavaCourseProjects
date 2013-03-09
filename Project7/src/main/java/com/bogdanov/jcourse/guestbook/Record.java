package com.bogdanov.jcourse.guestbook;

public class Record {
    private int id;
    private String postDate;
    private String message;

    public Record(int id, String message, String postDate) {
        this.id = id;
        this.message = message;
        this.postDate = postDate;
    }

    public int getId() {
        return id;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getMessage() {
        return message;
    }
}
