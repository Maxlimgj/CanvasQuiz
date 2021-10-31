package com.example.school.data;

public class UserAttempt {

    int id;
    String email, subject, correct, points, dateAttempt, overallPoints;

    public UserAttempt(int id, String email, String subject, String correct, String points, String dateAttempt) {
        this.id = id;
        this.email = email;
        this.subject = subject;
        this.correct = correct;
        this.points = points;
        this.dateAttempt = dateAttempt;
//        this.overallPoints = overallPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDateAttempt() {
        return dateAttempt;
    }

//    public String getOverallPoints() {return overallPoints;}

    public void setDateAttempt(String dateAttempt) {
        this.dateAttempt = dateAttempt;
    }
}
