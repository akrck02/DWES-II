/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import java.util.HashSet;

/**
 *
 * @author aketz
 */
public class Game {
   
    private String word;
    private HashSet<String> showing; 
    private int lives;
    
    public Game(final String word) {
        this.word = word;
        this.lives = word.length() / 2;
        this.showing = new HashSet<>();
    }

    public String getWord() {
        return word;
    }

    public int getLives() {
        return lives;
    }

    public int fail() {
        lives--;
        return lives;
    }
    
    public void show(final String letter) {
        this.showing.add(letter + "");
    }
    
    public boolean canShow(final String letter) {
        return this.showing.contains(letter);
    }
    
}
