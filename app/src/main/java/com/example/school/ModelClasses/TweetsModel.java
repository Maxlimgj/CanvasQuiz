package com.example.school.ModelClasses;

public class TweetsModel
{
    String email;
    String tweet;
    String time;
    String date;

    public TweetsModel() {
    }

    public TweetsModel(String email, String tweet, String time, String date) {
        this.email = email;
        this.tweet = tweet;
        this.time = time;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
