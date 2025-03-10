package com.example.cverdetotoo;

public class CharacterModel {
    private String id;        // e.g. "char001"
    private String name;      // e.g. "Green Warrior"
    private int imageResId;   // R.drawable.character_green
    private int cost;         // cost in coins
    private boolean unlocked; // whether the user has purchased/unlocked it

    public CharacterModel(String id, String name, int imageResId, int cost, boolean unlocked) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.cost = cost;
        this.unlocked = unlocked;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getCost() {
        return cost;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
