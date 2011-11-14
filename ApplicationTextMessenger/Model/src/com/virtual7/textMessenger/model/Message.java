package com.virtual7.textMessenger.model;

public class Message {
    public Message() {
        super();
    }

    private int id;
    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
