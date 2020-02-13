/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

/**
 *
 * @author verhi
 */
public class Pellet {
    private int levelPellet;
    private int number;
    private String url;

    public Pellet(int levelPellet, int number, String url) {
        this.levelPellet = levelPellet;
        this.number = number;
        this.url = url;
    }

    public int getNumber() {
        return number;
    }
    
    public int getLevelPellet(){
        return levelPellet;
    }
    
    public String getUrl() {
        return url;
    }
}
