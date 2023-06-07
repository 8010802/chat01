package com.example.chat05;

import java.util.Date;

public class Message {

    private final String sender;
    private final String messageText;
    private final Date time;
    private final boolean received;

    public Message(String sender, String messageText, Date time, boolean received) {
        this.sender = sender;
        this.messageText = messageText;
        this.time = time;
        this.received = received;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return messageText;
    }

    public Date getTime() {
        return time;
    }

    public boolean isReceived() {
        return received;
    }
}