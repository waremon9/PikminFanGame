/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author verhi
 */
public class ActionPikminOther extends ActionPikmin {
    private Evenement event;
    private JButton button;

    public ActionPikminOther(int numberPikmin, int typeAction ,String proprio, Evenement event, JButton button,  JLabel label) {
        super(numberPikmin, typeAction, proprio, label);
        this.event = event;
        this.button = button;
    }

    public Evenement getEvent() {
        return event;
    }

    public JButton getButton() {
        return button;
    }
    
    public boolean reduceDurability(int strenght){
        event.reduceDurability(strenght*this.getNumberPikmin());
        if (event.getDurability()<=0){
            return true;
        }
        return false;
    }
    
    public void setDurability(int x){
        event.setDurability(x);
    }
}
