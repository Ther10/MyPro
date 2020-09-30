package com.example.fouthapp;

public class Message {
    private String messageId;
    private String name;
    private int imageId;

    public Message(String name, int imageId){
        this.name = name;
        this.imageId= imageId;
    }

    public Message( String name, int imageId, String messageId) {
        this.messageId = messageId;
        this.name = name;
        this.imageId = imageId;
    }

    public Message() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
