package ir.ac.kntu;

import java.io.Serializable;

public class User implements Serializable {
    private String name;

    private int numberOfGames;

    private int highScore;

    public User(String name, int numberOfGames, int highScore) {
        this.name = name;
        this.numberOfGames = numberOfGames;
        this.highScore = highScore;
    }

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

}
