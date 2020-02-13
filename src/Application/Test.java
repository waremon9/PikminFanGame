/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Shop.Shop;
import Stats.Stats;

/**
 *
 * @author verhi
 */
public class Test {
    public static PikGame leJeu;
    public static Shop leShop;
    public static Stats lesStats;
    
    public static void main(String[] args){
        leJeu = new  PikGame();
        leShop = new Shop();
        lesStats = new Stats();
    }
}

