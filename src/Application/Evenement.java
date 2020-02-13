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
public abstract class Evenement {
    private String name;
    private int durability;
    private String url;

    public Evenement(String name, int durability, String url) {
        this.name = name;
        this.durability = durability;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getDurability() {
        return durability;
    }
    
    public void reduceDurability(int degats){
        durability-=degats;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
