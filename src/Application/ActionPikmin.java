/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import javax.swing.JLabel;

/**
 *
 * @author verhi
 */
public abstract class ActionPikmin {
    int numberPikmin;
    int typeAction; //1:enemy 2:enemy/treasure: 3:treasure 4:pellet 5:eventWall 6:eventOther
    String proprio;
    JLabel label;

    public ActionPikmin(int numberPikmin, int typeAction, String proprio, JLabel label) {
        this.numberPikmin = numberPikmin;
        this.typeAction = typeAction;
        this.proprio = proprio;
        this.label = label;
    }

    public String getProprio() {
        return proprio;
    }

    public JLabel getLabel() {
        return label;
    }
    
    public int getNumberPikmin() {
        return numberPikmin;
    }

    public int getTypeAction() {
        return typeAction;
    }

    public void ajoutPikmin(int pikmin){
        this.numberPikmin+=pikmin;
    }

    public void setTypeAction(int typeAction) {
        this.typeAction = typeAction;
    }

    public void setNumberPikmin(int numberPikmin) {
        this.numberPikmin = numberPikmin;
    }
}
