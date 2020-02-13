/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.awt.event.ActionEvent;

/**
 *
 * @author verhi
 */
public final class UtilityClass {
    private static PikGame truc = Test.leJeu;
    
    // Private constructor to prevent instantiation
    private UtilityClass() {
        throw new UnsupportedOperationException();
    }

    public static void useBombRockUtil(){
        if(truc.joueurActuel.getBombRock()<=0 || truc.enemyActuel==null) return;
        truc.joueurActuel.gainBombRock(-1);
        truc.bombRockPikmin +=truc.bombRockPower;
        truc.labelInfoBombRock.setText("Bomb-rock : "+truc.joueurActuel.getBombRock());
        truc.labelDescriptionBombRock.setText("Puissance bonus : "+truc.bombRockPikmin);
        truc.labelJeuHautCentre.add(truc.labelDescriptionBombRock);
        truc.refreshBoutton();
        truc.refreshInfo();
    }
    
    public static void useSpicySprayUtil(){
        if(truc.joueurActuel.getSpicySpray()<=0) return;
        truc.joueurActuel.gainSpicySpray(-1);
        truc.spicySpray = truc.spicySprayPower;
        truc.spicySprayLeft += truc.spicySprayDuration;
        truc.labelInfoSpicySpray.setText("Spicy spray : "+truc.joueurActuel.getSpicySpray());
        truc.labelDescriptionSpicySpray.setText("Spicy spray time left : "+truc.spicySprayLeft);
        truc.labelJeuHautCentre.add(truc.labelDescriptionSpicySpray);
        truc.refreshBoutton();
        truc.refreshInfo();
    }
    
    public static void checkSpicyLeft(){
        if (truc.spicySprayLeft>0){
            truc.spicySprayLeft--;
        }else {
            truc.spicySpray=1;
        }
        truc.labelDescriptionSpicySpray.setText("Spicy spray time left : "+truc.spicySprayLeft);
    }
    
        public static void useBitterSprayUtil(){
        if(truc.joueurActuel.getBitterSpray()<=0) return;
        truc.joueurActuel.gainBitterSpray(-1);
        truc.bitterSpray = truc.bitterSprayPower;
        truc.bitterSprayLeft += truc.bitterSprayDuration;
        truc.labelInfoBitterSpray.setText("Bitter spray : "+truc.joueurActuel.getBitterSpray());
        truc.labelDescriptionBitterSpray.setText("Bitter spray use left : "+truc.bitterSprayLeft);
        truc.labelJeuHautCentre.add(truc.labelDescriptionBitterSpray);
        truc.refreshBoutton();
        truc.refreshInfo();
    }
    
    public static void checkBitterLeft(){
        if (truc.bitterSprayLeft>0){
            truc.bitterSprayLeft--;
        }else {
            truc.bitterSpray=1;
        }
        truc.labelDescriptionBitterSpray.setText("Bitter spray time left : "+truc.bitterSprayLeft);
    }
}
