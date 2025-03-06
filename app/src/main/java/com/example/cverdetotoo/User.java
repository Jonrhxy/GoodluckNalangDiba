package com.example.cverdetotoo;

public class User {
    private String username;
    private int points;
    private int level;
    private String rank;

    public User() { } // Empty constructor for Firestore

    public User(String username, int points, int level, String rank) {
        this.username = username;
        this.points = points;
        this.level = level;
        this.rank = rank;
    }

    public String getUsername() { return username; }
    public int getPoints() { return points; }
    public int getLevel() { return level; }
    public String getRank() { return rank; }
}

