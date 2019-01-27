package com.example.ibrahim.messages.Model;

/**
 * Created by Ibrahim on 1/27/2019.
 */

public class Message
{
    String message , sentiment ;

    public Message (){}

    public Message(String message, String sentiment) {
        this.message = message;
        this.sentiment = sentiment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
} // class of Message
