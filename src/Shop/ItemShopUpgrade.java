/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import Application.Test;
import Application.Test;
import javax.swing.JLabel;

/**
 *
 * @author verhi
 */
public class ItemShopUpgrade extends ItemShop{
    private int lvl;
    private JLabel label;

    public ItemShopUpgrade(int id, String name, int prix, String monnaie, String description, JLabel label, int lvl) {
        super(id, name, prix, monnaie, description);
        this.lvl = lvl;
        this.label = label;
    }

    public int getLvl() {
        return lvl;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    
    public void levelUp(String name){
        this.lvl++;
        Test.leJeu.joueurActuel.getMapLienItemLvl().remove(name);
        Test.leJeu.joueurActuel.getMapLienItemLvl().put(name, this.lvl);
    }
}
