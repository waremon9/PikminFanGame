/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author verhi
 */
public class Joueur {
    private static int id=1;
    private String pseudo;
    private int pikmin;
    private int pokos;
    private int bombRock;
    private int spicySpray;
    private int bitterSpray;
    private int tour;
    private Map<String, Integer> mapLienItemLvl;

    public Joueur(String pseudo, int pikmin, int pokos, int bombRock, int spicySpray, int bitterSpray, int tour, int onionSizeLvl,
            int onionPowerLvl, int bombRockPowerLvl, int spicySprayPowerLvl, int spicySprayDurationLvl,
            int bitterSprayPowerLvl, int bitterSprayDurationLvl) {
        this.pseudo = pseudo;
        this.pikmin = pikmin;
        this.pokos = pokos;
        this.bombRock = bombRock;
        this.spicySpray = spicySpray;
        this.bitterSpray = bitterSpray;
        this.tour = tour;
        Map<String, Integer> mapLienItemLvl = new HashMap<String, Integer>();
        mapLienItemLvl.put("Onion size", onionSizeLvl);
        mapLienItemLvl.put("Onion power", onionPowerLvl);
        mapLienItemLvl.put("Bomb-rock power", bombRockPowerLvl);
        mapLienItemLvl.put("Spicy spray power", spicySprayPowerLvl);
        mapLienItemLvl.put("Spicy spray duration", spicySprayDurationLvl);
        mapLienItemLvl.put("Bitter spray power", bitterSprayPowerLvl);
        mapLienItemLvl.put("Bitter spray duration", bitterSprayDurationLvl);
        this.mapLienItemLvl = mapLienItemLvl;
        ;
    }

    public Map<String, Integer> getMapLienItemLvl() {
        return mapLienItemLvl;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getPikmin() {
        return pikmin;
    }

    public int getPokos() {
        return pokos;
    }

    public int getTour() {
        return tour;
    }

    public int getBombRock() {
        return bombRock;
    }
    
    public int getSpicySpray() {
        return spicySpray;
    }

    public int getBitterSpray() {
        return bitterSpray;
    }

    public int getOnionSizeLvl() {
        return mapLienItemLvl.get("Onion size");
    }

    public int getOnionPowerLvl() {
        return mapLienItemLvl.get("Onion power");
    }

    public int getBombRockPowerLvl() {
        return mapLienItemLvl.get("Bomb-rock power");
    }

    public int getSpicySprayPowerLvl() {
        return mapLienItemLvl.get("Spicy spray power");
    }

    public int getSpicySprayDurationLvl() {
        return mapLienItemLvl.get("Spicy spray duration");
    }

    public int getBitterSprayPowerLvl() {
        return mapLienItemLvl.get("Bitter spray power");
    }

    public int getBitterSprayDurationLvl() {
        return mapLienItemLvl.get("Bitter spray duration");
    }

    public void setPikmin(int pikmin) {
        this.pikmin = pikmin;
    }
    
    public void gainPikmin(int gain){
        this.pikmin += gain;
    }
    
    public void gainPokos(int gain){
        this.pokos += gain;
    }
    
    public void gainBombRock(int gain){
        this.bombRock += gain;
    }
    
    public void gainSpicySpray(int gain){
        this.spicySpray += gain;
    }
    
    public void gainBitterSpray(int gain){
        this.bitterSpray += gain;
    }
}
