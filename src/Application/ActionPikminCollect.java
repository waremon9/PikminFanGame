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
public class ActionPikminCollect extends ActionPikmin{
    int gainPikmin;
    int gainPokos;
    int timeLeft;

    public ActionPikminCollect(int numberPikmin, int typeAction, String proprio, int gainPikmin, int gainPokos, int timeLeft, JLabel label) {
        super(numberPikmin, typeAction, proprio, label);
        this.gainPikmin = gainPikmin;
        this.gainPokos = gainPokos;
        this.timeLeft = timeLeft;
    }

    public int getGainPikmin() {
        return gainPikmin;
    }

    public int getGainPokos() {
        return gainPokos;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setGainPokos(int gainPokos) {
        this.gainPokos = gainPokos;
    }
    
    public boolean reduceTime(int speed){
        timeLeft-=speed;
        if (timeLeft<=0){
            return true;
        }
        return false;
    }
}
