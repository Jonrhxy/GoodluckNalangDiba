package com.example.cverdetotoo;

public class Player {
    private String name;
    private int points;
    private int rank;
    private String rankTitle;
    private int avatarResId;

    public Player(String name, int points, int rank, String rankTitle, int avatarResId) {
        this.name = name;
        this.points = points;
        this.rank = rank;
        this.rankTitle = rankTitle;
        this.avatarResId = avatarResId;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getRank() {
        return rank;
    }

    public String getRankTitle() {
        return rankTitle;
    }

    public int getAvatarResId() {
        return avatarResId;
    }
}
