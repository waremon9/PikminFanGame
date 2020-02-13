/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import javax.swing.JLabel;

/**
 *
 * @author verhi
 */
public abstract class ItemShop{
    private int id;
    private String name;
    private int prix;
    private String monnaie;
    private String description;

    public ItemShop(int id, String name, int prix, String monnaie, String description) {
        this.id=id;
        this.name = name;
        this.prix = prix;
        this.monnaie = monnaie;
        this.description = description;
    }

    public String getMonnaie() {
        return monnaie;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrix() {
        return prix;
    }

    public String getDescription() {
        return description;
    }
}
