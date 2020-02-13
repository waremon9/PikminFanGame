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
public class Treasure {
    private String name;
    private int valeur;
    private String url;
    private int poids;
    int rareté;

    public Treasure(String name, int valeur, String url, int poids, int rareté) {
        this.name = name;
        this.valeur = valeur;
        this.url = url;
        this.poids = poids;
        this.rareté = rareté;
    }

    public String getName() {
        return name;
    }

    public int getRareté() {
        return rareté;
    }

    public int getValeur() {
        return valeur;
    }

    public String getUrl() {
        return url;
    }

    public int getPoids() {
        return poids;
    }
}
