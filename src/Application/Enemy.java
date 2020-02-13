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
public class Enemy {
    private String name;
    private int avgGain;
    private int sureKill;
    private String deathWord;
    private String url;
    private int danger;
    private int dropChance;
    private int dropRare1;
    private int dropRare2;
    private int dropRare3;
    private int dropRare4;
    private int dropRare5;
    private int poids;

    public Enemy(String name, int avgGain, int sureKill, String deathWord, int poids, String url, int danger, int dropChance,
            int dropRare1, int dropRare2, int dropRare3, int dropRare4, int dropRare5) {
        this.name = name;
        this.avgGain = avgGain;
        this.sureKill = sureKill;
        this.deathWord = deathWord;
        this.poids = poids;
        this.url = url;
        this.danger = danger;
        this.dropChance = dropChance;
        this.dropRare1 = dropRare1;
        this.dropRare2 = dropRare2;
        this.dropRare3 = dropRare3;
        this.dropRare4 = dropRare4;
        this.dropRare5 = dropRare5;
    }

    public int getPoids() {
        return poids;
    }

    public int getDropChance() {
        return dropChance;
    }

    public int getDropRare1() {
        return dropRare1;
    }

    public int getDropRare2() {
        return dropRare2;
    }

    public int getDropRare3() {
        return dropRare3;
    }

    public int getDropRare4() {
        return dropRare4;
    }

    public int getDropRare5() {
        return dropRare5;
    }

    public int getDanger() {
        return danger;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getAvgGain() {
        return avgGain;
    }

    public int getSureKill() {
        return sureKill;
    }

    public String getDeathWord() {
        return deathWord;
    }
}
