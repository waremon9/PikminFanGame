package Stats;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Application.Test;
import Application.Test;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author verhi
 */
public class Stats {
    
    private JFrame fenetreStats=new JFrame();
    
    private JPanel panelFenetre=new JPanel();
    
    private JLabel labelInfo = new JLabel();
    
    public Stats(){
        // créé la fenêtre en elle-même 
        fenetreStats.setSize(500, 600);
        fenetreStats.setTitle("Fenetre Stats");
        fenetreStats.setLocationRelativeTo(Test.leJeu.labelImage);
        fenetreStats.setResizable(false);

        // créé le contentPane
        panelFenetre.setBackground(Color.WHITE);
        fenetreStats.getContentPane().add(panelFenetre);

        textLabelInfo();
        panelFenetre.add(labelInfo);
    }
    
    public void textLabelInfo(){
        labelInfo.setText("<html><font size=\"5\"><font size=\"7\"><u>Onion :</u></font><br><br>"
                + " - Onion size : "+Test.leJeu.joueurActuel.getOnionSizeLvl()+" ("+(Test.leJeu.joueurActuel.getOnionSizeLvl()*40+60)+" max Pikmins)<br>"
                + " - Onion power : "+Test.leJeu.joueurActuel.getOnionPowerLvl()+" (x"+(Math.round(Test.leJeu.onionPower * (10 ^ 2)) / (double) (10 ^ 2))+")<br><br><br>"
                + "<font size=\"7\"><u>Items :</u></font><br><br>"
                + " - Bomb-rock power : "+Test.leJeu.joueurActuel.getBombRockPowerLvl()+" ("+(Test.leJeu.bombRockPower)+" Pikmins power)<br><br>"
                + " - Spicy spray power : "+Test.leJeu.joueurActuel.getSpicySprayPowerLvl()+" (x"+(Test.leJeu.spicySprayPower)+" Pikmins speed)<br>"
                + " - Spicy spray duration : "+Test.leJeu.joueurActuel.getSpicySprayDurationLvl()+" ("+(Test.leJeu.spicySprayDuration)+" turns)<br><br>"
                + " - Bitter spray power : "+Test.leJeu.joueurActuel.getBitterSprayPowerLvl()+" (/"+(Test.leJeu.bitterSprayPower)+" monster strenght)<br>"
                + " - Bitter spray duration : "+Test.leJeu.joueurActuel.getBitterSprayDurationLvl()+" ("+(Test.leJeu.bitterSprayDuration)+" turns)<br></font></html>");
        fenetreStats.revalidate();
        fenetreStats.repaint();
    }

    public void setVisible(boolean visible) {
        fenetreStats.setVisible(visible);
    }
}
