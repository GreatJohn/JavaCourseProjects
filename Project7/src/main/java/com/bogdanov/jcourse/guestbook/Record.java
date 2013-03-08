package com.bogdanov.jcourse.guestbook;

public class Record {
    public int id;
    public String postDate;
    public String message;

    public Record(int id, String message, String postDate) {
        this.id = id;
        this.message = message;
        this.postDate = postDate;
    }
}
