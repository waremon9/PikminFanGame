/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import static Application.UtilityClass.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author verhi
 */
public class PikGame implements FocusListener{
    private static NumberFormat fmt;
    private static Enemy[] tabEnemy = null;
    private static Pellet[] tabPellets = new Pellet [4];
    private static Treasure[] tabTreasure = null;
    private static Evenement[] tabEvenement = new Evenement[6];
    private static List<ActionPikmin> listeAction = new ArrayList<ActionPikmin>();
    private static Map<JButton, ActionPikminOther> mapLienEventButton =new HashMap<JButton, ActionPikminOther>();
    private static Map<JButton, Zone> mapLienButtonZone = new HashMap<JButton, Zone>();
    private static Map<JButton, Joueur> mapLienButtonPlayer = new HashMap<JButton, Joueur>();
    
    private static File saveJoueur=new File(System.getenv("HOME"), "save/.Pikmin_save.txt");
    private static File saveAction=new File(System.getenv("HOME"), "save/.Action_save.txt");
    
    public static Joueur joueurActuel = null;
    
    static{
        try {
            java.util.List<String> joueurs=Files.readAllLines(saveJoueur.toPath());
            int x=0;
            for (String truc1:joueurs){
                String[] infoJoueur = truc1.split(";");
                for (String truc2:infoJoueur){
                    String[] infoJoueur2 = truc2.split(":");
                    String name = infoJoueur2[0];
                    int pikmin = Integer.parseInt(infoJoueur2[1]);
                    int pokos = Integer.parseInt(infoJoueur2[2]);
                    int bombRock = Integer.parseInt(infoJoueur2[3]);
                    int spraySpicy = Integer.parseInt(infoJoueur2[4]);
                    int bitterSpray = Integer.parseInt(infoJoueur2[5]);
                    int tour = Integer.parseInt(infoJoueur2[6]);
                    int onionSizeLvl = Integer.parseInt(infoJoueur2[7]);
                    int onionPowerLvl = Integer.parseInt(infoJoueur2[8]);
                    int bombRockPowerLvl = Integer.parseInt(infoJoueur2[9]);
                    int spicySprayPowerLvl = Integer.parseInt(infoJoueur2[10]);
                    int spicySprayDurationLvl = Integer.parseInt(infoJoueur2[11]);
                    int bitterSprayPowerLvl = Integer.parseInt(infoJoueur2[12]);
                    int bitterSprayDurationLvl = Integer.parseInt(infoJoueur2[13]);
                    Joueur machin = new Joueur(name, pikmin, pokos, bombRock, spraySpicy, bitterSpray, tour, onionSizeLvl, onionPowerLvl,
                            bombRockPowerLvl, spicySprayPowerLvl, spicySprayDurationLvl, bitterSprayPowerLvl, bitterSprayDurationLvl);
                    mapLienButtonPlayer.put(new JButton(name), machin);
                    if (joueurActuel==null){
                        joueurActuel = machin;
                    }
                    x++;
                }
            }
            java.util.List<String> action=Files.readAllLines(saveAction.toPath());
            x=0;
            for (String truc:action){
                String[] ligneAction = truc.split(";");
                for (String reTruc:ligneAction){
                    String[] infoAction = reTruc.split(":");
                    int nbPikmin = Integer.parseInt(infoAction[0]);
                    int typeAction = Integer.parseInt(infoAction[1]);
                    String proprio = infoAction[2];
                    if (typeAction<=4){
                        int gainPikmin = Integer.parseInt(infoAction[3]);
                        int gainPokos = Integer.parseInt(infoAction[4]);
                        int temps = Integer.parseInt(infoAction[5]);
                        listeAction.add(new ActionPikminCollect(nbPikmin, typeAction, proprio, gainPikmin, gainPokos, temps, new JLabel()));
                    }else if (typeAction==5){
                        String name = infoAction[3];
                        int durability = Integer.parseInt(infoAction[4]);
                        String url = infoAction[5];
                        int lvl = Integer.parseInt(infoAction[6]);
                        int drop1 = Integer.parseInt(infoAction[7]);
                        int drop2 = Integer.parseInt(infoAction[8]);
                        int drop3 = Integer.parseInt(infoAction[9]);
                        int drop4 = Integer.parseInt(infoAction[10]);
                        int drop5 = Integer.parseInt(infoAction[11]);
                        listeAction.add(new ActionPikminOther(nbPikmin, typeAction, proprio, new EventWall(name, durability, url, lvl, drop1, drop2, drop3, drop4, drop5), new JButton("Result"), new JLabel()));
                    }else if (typeAction==6){
                        String name = infoAction[3];
                        int durability = Integer.parseInt(infoAction[4]);
                        String url = infoAction[5];
                        String drop = infoAction[6];
                        int dropMin = Integer.parseInt(infoAction[7]);
                        int dropMax = Integer.parseInt(infoAction[8]);
                        String urlDrop = infoAction[9];
                        listeAction.add(new ActionPikminOther(nbPikmin, typeAction, proprio,  new EventOther(name, durability, url, drop, dropMin, dropMax, urlDrop), new JButton("Result"), new JLabel()));
                    }
                }
            }
            List<String> monstres=FileUtil.readResourceLines("/res/enemy.txt");
            x=-1;
            tabEnemy = new Enemy[monstres.size()];
            for (String truc:monstres){
                if(x!=-1) {
                    String[] infoEnemy = truc.split(":");
                    String name = infoEnemy[1];
                    int avgGain = Integer.parseInt(infoEnemy[2]);
                    int sureKill = Integer.parseInt(infoEnemy[3]);
                    String mort = infoEnemy[4];
                    int poids = Integer.parseInt(infoEnemy[5]);
                    String urlImage = infoEnemy[6];
                    int danger = Integer.parseInt(infoEnemy[7]);
                    int dropChance = Integer.parseInt(infoEnemy[8]);
                    int drop1 = Integer.parseInt(infoEnemy[9]);
                    int drop2 = Integer.parseInt(infoEnemy[10]);
                    int drop3 = Integer.parseInt(infoEnemy[11]);
                    int drop4 = Integer.parseInt(infoEnemy[12]);
                    int drop5 = Integer.parseInt(infoEnemy[13]);
                    tabEnemy[x] = new Enemy(name, avgGain, sureKill, mort, poids, urlImage, danger, dropChance, drop1, drop2, drop3, drop4, drop5);
                }
                x++;
            }
            List<String> pellets=FileUtil.readResourceLines("/res/pellet.txt");
            x=-1;
            for (String truc:pellets){
                if(x!=-1){
                    String[] infoPellets = truc.split(":");
                    int level = Integer.parseInt(infoPellets[0]);
                    int number = Integer.parseInt(infoPellets[1]);
                    String urlImage = infoPellets[2];
                    tabPellets[x] = new Pellet(level, number, urlImage);
                }
                x++;
            }
            List<String> treasure=FileUtil.readResourceLines("/res/treasure.txt");
            x=-1;
            tabTreasure = new Treasure[treasure.size()];
            for (String truc:treasure){
                if(x!=-1){
                    String[] infoTreasure = truc.split(":");
                    String name = infoTreasure[1];
                    int value = Integer.parseInt(infoTreasure[2]);
                    String urlImage = infoTreasure[3];
                    int poids = Integer.parseInt(infoTreasure[4]);
                    int rare = Integer.parseInt(infoTreasure[5]);
                    tabTreasure[x] = new Treasure(name, value, urlImage, poids, rare);
                }
                x++;
            }
            List<String> evenementwall=FileUtil.readResourceLines("/res/evenementWall.txt");
            x=-1;
            for (String truc:evenementwall){
                if(x!=-1){
                    String[] infoEvenementWall = truc.split(":");
                    String name = infoEvenementWall[1];
                    int stamina = Integer.parseInt(infoEvenementWall[2]);
                    String urlImage = infoEvenementWall[3];
                    int level = Integer.parseInt(infoEvenementWall[0]);
                    int drop1 = Integer.parseInt(infoEvenementWall[4]);
                    int drop2 = Integer.parseInt(infoEvenementWall[5]);
                    int drop3 = Integer.parseInt(infoEvenementWall[6]);
                    int drop4 = Integer.parseInt(infoEvenementWall[7]);
                    int drop5 = Integer.parseInt(infoEvenementWall[8]);
                    tabEvenement[x] = new EventWall(name, stamina, urlImage, level, drop1, drop2, drop3, drop4, drop5);
                }
                x++;
            }
            List<String> evenementOther=FileUtil.readResourceLines("/res/evenementOther.txt");
            for (String truc:evenementOther){
                if(x!=3){
                    String[] infoEvenementOther = truc.split(":");
                    String name = infoEvenementOther[1];
                    int stamina = Integer.parseInt(infoEvenementOther[2]);
                    String urlImage = infoEvenementOther[3];
                    String drop = infoEvenementOther[4];
                    int min = Integer.parseInt(infoEvenementOther[5]);
                    int max = Integer.parseInt(infoEvenementOther[6]);
                    String urlDrop = infoEvenementOther[7];
                    tabEvenement[x-1] = new EventOther(name, stamina, urlImage, drop, min, max, urlDrop);
                }
                x++;
            }
            List<String> zones=FileUtil.readResourceLines("/res/zone.txt");
            x=-1;
            for (String truc:zones){
                if(x!=-1) {
                    String[] infoZone = truc.split(":");
                    int id = Integer.parseInt(infoZone[0]);
                    String name = infoZone[1];
                    String url = infoZone[2];
                    mapLienButtonZone.put(new JButton(name),new Zone(id, name, url));
                }
                x++;
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public Joueur joueurChoix = joueurActuel;
    
    public Enemy enemyActuel;
    private Pellet paletActuel;
    private Treasure treasureActuel;
    private ActionPikminCollect actionPikminActuel;
    private Evenement eventActuel;
    private int testIntA = 30;
    private int testIntB = 0;
    private int temps;
    public int bombRockPikmin =0;
    public int spicySprayLeft =0;
    public int spicySpray =1;
    public int bitterSprayLeft =0;
    public int bitterSpray =1;
    private int tour = joueurActuel.getTour()+1;
    public int bombRockPower = 25 + (25*joueurActuel.getBombRockPowerLvl());
    public int spicySprayPower = 1 + joueurActuel.getSpicySprayPowerLvl();
    public int spicySprayDuration = 15 + (15*joueurActuel.getSpicySprayDurationLvl());
    public int bitterSprayPower = 1 + joueurActuel.getBitterSprayPowerLvl();
    public int bitterSprayDuration = 1 + joueurActuel.getBitterSprayDurationLvl();
    private Zone zoneChoix = null;
    private Zone zoneActuel = null;
    private String contenue = "";
    private List<ActionPikmin> listeActionARemove = new ArrayList<ActionPikmin>();
    public double onionPower = 1.;
    
    private Random rndGenerator = new Random();

    private JFrame fenetre = new JFrame();
    
    private JPanel panelFenetre = new JPanel();
    private JPanel panelInfo = new JPanel();
    private JPanel panelInfoDetails = new JPanel();
    private JLabel labelJeu = new JLabel();
    private JPanel panelJeuBouttons = new JPanel();
    private JLabel labelJeuHaut = new JLabel();
    public JLabel labelJeuHautCentre = new JLabel();
    private JPanel panelDroite = new JPanel();
    private JPanel panelEtatPikmin = new JPanel();
    private JPanel panelPseudo = new JPanel();
    
    private JButton search = new JButton("Search");
    private JButton attack = new JButton("Attack");
    private JButton send = new JButton("Send Pikmin");
    private JButton shop = new JButton("SHOP");
    private JButton collect = new JButton("collect");
    private JButton keepGoing = new JButton("Keep Going");
    private JButton pick = new JButton("Pick");
    private JButton wait = new JButton("Wait");
    private JButton buttonBombRock = new JButton("Use Bomb-rock");
    private JButton buttonSpicySpray = new JButton("Use Spicy spray");
    private JButton buttonBitterSpray = new JButton("Use Bitter spray");
    private JButton buttonEvent = new JButton("GO");
    private JButton buttonStat = new JButton("Statisitiques");
    private JButton buttonAreaChange = new JButton("Change area");
    private JButton land = new JButton("Land");
    private JButton changePlayer = new JButton("Change player");
    private JButton newPlayer = new JButton("New player");
    private JButton deletePlayer = new JButton("Delete player");
    
    public JLabel labelImage = new JLabel(ImageUtil.getIcon("/res/other/greatings.png"));
    private JLabel labelDescription = new JLabel("<<I was waiting for you. Shall we get started?>>");
    public JLabel labelInfoPikminDispo = new JLabel("Pikmins disponibles : "+ pikminDispo());
    public JLabel labelInfoPikmin = new JLabel("Pikmins : "+ joueurActuel.getPikmin());
    public JLabel labelInfoPokos = new JLabel("Pokos : "+ joueurActuel.getPokos());
    public JLabel labelInfoOnionLvl = new JLabel("Onion size level : "+ joueurActuel.getOnionSizeLvl());
    public JLabel labelInfoBombRock = new JLabel("Bomb-rock : "+ joueurActuel.getBombRock());
    public JLabel labelInfoSpicySpray = new JLabel("Spicy spray : "+ joueurActuel.getSpicySpray());
    public JLabel labelInfoBitterSpray = new JLabel("Bitter spray : "+ joueurActuel.getBitterSpray());
    private JLabel labelTime = new JLabel("Turn : "+tour);
    public JLabel labelDescriptionBombRock = new JLabel();
    public JLabel labelDescriptionSpicySpray = new JLabel();
    public JLabel labelDescriptionBitterSpray = new JLabel();
    private JLabel pseudo = new JLabel("<html><font size=\"6\"><u>"+joueurActuel.getPseudo()+"</u></font></html>");
    
    public JSlider sliderPikmin = new JSlider(0,100+(joueurActuel.getOnionSizeLvl()-1)*30);
    
    private JFormattedTextField qtePikmin = new JFormattedTextField(new NumberFormatter(fmt));
    
    //Pour la fenetre de demande de zone
    private JPanel panelZone=new JPanel();
    private JLabel pikRestant = new JLabel();
    private JLabel labelZoneActuel = new JLabel();
    private JLabel labelZoneChoix = new JLabel("Selected : None");
    
    //Pour la fenetre de demande de joueur
    private JPanel panelPlayer=new JPanel();
    private JLabel labelPlayerActuel = new JLabel();
    private JLabel labelPlayerChoix = new JLabel("Selected : None");
    
    //Pour la fenetre de demande de nouveau joueur
    private JPanel panelNewPlayer=new JPanel();
    private JLabel askNameNewPlayer=new JLabel("Pseudo : ");
    private JFormattedTextField nameNewPlayer = new JFormattedTextField();
    
    //Pour la fenetre de demande de suppression joueur
    private JPanel panelDeletePlayer=new JPanel();
    private JLabel nameDeletePlayer=new JLabel("Confirmer pseudo du joueur : ");
    private JFormattedTextField sureDeletePlayer = new JFormattedTextField();
    
    public PikGame() {
        PlaySound.playSound("Wistful_Wild.wav");
        for (int i=1; i<joueurActuel.getOnionPowerLvl(); i++){
            onionPower *=1.2;
        }
        for (Zone zone:mapLienButtonZone.values()){
            if (zone.getId()==1) zoneActuel=zone;
        }
        labelZoneActuel.setText("Selected : "+zoneActuel.getName());
        labelPlayerActuel.setText("Actual : "+joueurActuel.getPseudo());
    	// créé la fenêtre en elle-même 
        fenetre.setSize(1340, 800);
        fenetre.setTitle("Fenetre PikGame");
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // créé le contentPane
        fenetre.getContentPane().add(panelFenetre);
        
        panelFenetre.setLayout(new BorderLayout());
        panelFenetre.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setPreferredSize(new Dimension(270,0));
        panelFenetre.add("West",panelInfo);
        panelFenetre.add("Center",labelJeu);
        panelDroite.setPreferredSize(new Dimension(270,0));
        panelDroite.setLayout(new BorderLayout());
        panelDroite.setBackground(Color.WHITE);
        panelDroite.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panelFenetre.add("East",panelDroite);
        panelEtatPikmin.setBackground(Color.WHITE);
        panelDroite.add("Center", panelEtatPikmin);
        buttonAreaChange.setPreferredSize(new Dimension(0,40));
        buttonAreaChange.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
        panelDroite.add("South", buttonAreaChange);
        
        labelJeu.setLayout(new BorderLayout());
        labelJeu.setIcon(ImageUtil.getIcon("/res/other/Wild_Background.png"));
        labelJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        labelJeuHaut.setBackground(Color.WHITE);
        labelJeu.add("North", labelJeuHaut);
        labelJeu.add("Center",labelImage);
        panelJeuBouttons.setPreferredSize(new Dimension(0, 40));
        labelJeu.add("South",panelJeuBouttons);
        
        labelJeuHautCentre.setLayout(new BoxLayout(labelJeuHautCentre, BoxLayout.PAGE_AXIS));
        labelDescription.setBorder(BorderFactory.createMatteBorder(12, 8, 0, 0, new Color(0,0,0,0)));
        labelJeuHautCentre.add(labelDescription);
        labelDescriptionBombRock.setBorder(BorderFactory.createMatteBorder(8, 8, 0, 0, new Color(0,0,0,0)));
        labelDescriptionSpicySpray.setBorder(BorderFactory.createMatteBorder(8, 8, 0, 0, new Color(0,0,0,0)));
        labelDescriptionBitterSpray.setBorder(BorderFactory.createMatteBorder(8, 8, 0, 0, new Color(0,0,0,0)));
        
        labelJeuHaut.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        labelJeuHaut.setLayout(new BorderLayout());
        labelJeuHaut.setPreferredSize(new Dimension(0,190));
        labelJeuHaut.setIcon(ImageUtil.getIcon("/res/other/FondBlanc.png"));
        
        labelJeuHaut.add("Center",labelJeuHautCentre);
        labelJeuHaut.add("East",labelTime);
        
        for(ActionPikmin action:listeAction) {
            if(action.getProprio().equals(joueurActuel.getPseudo())) ecrireEtatPikmin(action);
        }
        
        panelJeuBouttons.setBackground(Color.WHITE);
        panelJeuBouttons.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
        panelJeuBouttons.setLayout(new GridLayout(1,3,20,20));
        search.addActionListener(this::onSearch);
        attack.addActionListener(this::onAttack);
        collect.addActionListener(this::onCollect);
        shop.addActionListener(this::onShop);
        pick.addActionListener(this::onPick);
        sliderPikmin.addChangeListener(this::onSlide);
        sliderPikmin.setBackground(Color.WHITE);
        qtePikmin.addFocusListener(this);
        keepGoing.addActionListener(this::onKeepGoing);
        wait.addActionListener(this::onWait);
        buttonEvent.addActionListener(this::onGo);
        buttonBombRock.addActionListener(this::useBombRock);
        buttonSpicySpray.addActionListener(this::useSpicySpray);
        buttonBitterSpray.addActionListener(this::useBitterSpray);
        buttonStat.addActionListener(this::onStat);
        buttonAreaChange.addActionListener(this::onChangeArea);
        land.addActionListener(this::onLand);
        changePlayer.addActionListener(this::onChangePlayer);
        panelJeuBouttons.add(wait);
        panelJeuBouttons.add(search);
        
        panelInfo.setLayout(new BorderLayout(0,50));
        panelInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panelPseudo.setLayout(new BorderLayout());
        panelPseudo.setPreferredSize(new Dimension(0, 120));
        panelPseudo.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        panelPseudo.add("North", changePlayer);
        JLabel vide = new JLabel();
        vide.setBorder(BorderFactory.createLineBorder(Color.WHITE,30));
        panelPseudo.add("West",vide);
        panelPseudo.setBackground(Color.WHITE);
        panelPseudo.add("Center",pseudo);
        panelInfo.add("North", panelPseudo);
        
        panelInfoDetails.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, new Color(0,0,0,0)));
        panelInfoDetails.setBackground(Color.WHITE);
        panelInfo.add("Center", panelInfoDetails);
        panelInfoDetails.setLayout(new GridLayout(0, 1, 10, 0));
        panelInfoDetails.add(labelInfoPikmin);
        panelInfoDetails.add(labelInfoPikminDispo);
        panelInfoDetails.add(labelInfoPokos);
        panelInfoDetails.add(buttonStat);
        panelInfoDetails.add(new JLabel("<html><font size=\"4\"> - Inventory</font></html>"));
        panelInfoDetails.add(labelInfoBombRock);
        panelInfoDetails.add(buttonBombRock);
        panelInfoDetails.add(labelInfoSpicySpray);
        panelInfoDetails.add(buttonSpicySpray);
        panelInfoDetails.add(labelInfoBitterSpray);
        panelInfoDetails.add(buttonBitterSpray);
        shop.setPreferredSize(new Dimension(0, 120));
        panelInfo.add("South", shop);
        
        fenetre.setVisible(true);
        
        //Panel Zone
        panelZone.setLayout(new GridLayout(0,1));
        panelZone.add(pikRestant);
        for (JButton button:mapLienButtonZone.keySet()) {
            button.addActionListener(this::onButtonZone);
            panelZone.add(button);
        }
        panelZone.add(labelZoneActuel);
        panelZone.add(labelZoneChoix);

        //Panel Player
        panelPlayer.setLayout(new GridLayout(0,1));
        for (JButton button:mapLienButtonPlayer.keySet()) {
            button.addActionListener(this::onButtonPlayer);
            panelPlayer.add(button);
        }
        newPlayer.addActionListener(this::onNewPlayer);
        deletePlayer.addActionListener(this::onDeletePlayer);
        panelPlayer.add(labelPlayerActuel);
        panelPlayer.add(labelPlayerChoix);
        panelPlayer.add(newPlayer);
        panelPlayer.add(deletePlayer);
        
        //Panel NewPlayer
        panelNewPlayer.setLayout(new GridLayout(0,1));
        panelNewPlayer.add(askNameNewPlayer);
        panelNewPlayer.add(nameNewPlayer);
        
        //Panel DeletePlayer
        panelDeletePlayer.setLayout(new GridLayout(0,1));
        panelDeletePlayer.add(nameDeletePlayer);
        panelDeletePlayer.add(sureDeletePlayer);
    }
    
    public void refreshBoutton(){
        panelJeuBouttons.revalidate();
        panelJeuBouttons.repaint();
    }
    
    public void refreshInfo(){
        panelInfoDetails.revalidate();
        panelInfoDetails.repaint();
    }
    
    public void refreshPanelPlayer(){
        panelPlayer.removeAll();
        for (JButton button:mapLienButtonPlayer.keySet()) {
            button.addActionListener(this::onButtonPlayer);
            panelPlayer.add(button);
        }
        newPlayer.addActionListener(this::onNewPlayer);
        deletePlayer.addActionListener(this::onDeletePlayer);
        panelPlayer.add(labelPlayerActuel);
        panelPlayer.add(labelPlayerChoix);
        panelPlayer.add(newPlayer);
        panelPlayer.add(deletePlayer);
        panelPlayer.revalidate();
        panelPlayer.repaint();
    }
    
    public void autoSave(){
        Test.lesStats.textLabelInfo();
        String save1 = "";
        String save2 = "";
        try {
            File savePlayer=new File(System.getenv("HOME"), "save/.Pikmin_save.txt");
            File saveAction=new File(System.getenv("HOME"), "save/.Action_save.txt");
                for (Joueur joueur:mapLienButtonPlayer.values()){
                save1=save1+joueur.getPseudo()+":"+joueur.getPikmin()+":"+joueur.getPokos()+":"+joueur.getBombRock()+":"+joueur.getSpicySpray()+":"
                        +joueur.getBitterSpray()+":"+tour+":"+joueur.getOnionSizeLvl()+":"+joueur.getOnionPowerLvl()+":"+joueur.getBombRockPowerLvl()+":"
                        +joueur.getSpicySprayPowerLvl()+":"+joueur.getSpicySprayDurationLvl()+":"+joueur.getBitterSprayPowerLvl()+":"+joueur.getBitterSprayDurationLvl()+";";}
                for(ActionPikmin action:listeAction){
                    if (action instanceof ActionPikminCollect){
                        save2=save2+action.getNumberPikmin()+":"+action.getTypeAction()+":"+action.getProprio()+":"+((ActionPikminCollect) action).getGainPikmin()+":"
                                +((ActionPikminCollect) action).getGainPokos()+":"+((ActionPikminCollect) action).getTimeLeft()+";";
                    }
                    else if (action instanceof ActionPikminOther){
                        Evenement event = ((ActionPikminOther) action).getEvent();
                        if (event instanceof EventWall){
                            save2 = save2+action.getNumberPikmin()+":"+action.getTypeAction()+":"+action.getProprio()+":"+event.getName()+":"+event.getDurability()+":"
                                    +event.getUrl()+":"+((EventWall) event).getLvl()+":"+((EventWall) event).getDrop1()+":"+((EventWall) event).getDrop2()
                                    +":"+((EventWall) event).getDrop3()+":"+((EventWall) event).getDrop4()+":"+((EventWall) event).getDrop5()+";";
                        }
                        else if (event instanceof EventOther){
                            save2 = save2+action.getNumberPikmin()+":"+action.getTypeAction()+":"+action.getProprio()+":"+event.getName()+":"+event.getDurability()+":"
                                    +event.getUrl()+":"+((EventOther) event).getDrop()+":"+((EventOther) event).getMin()+":"+((EventOther) event).getMax()+":"
                                    +((EventOther) event).getUrlDrop()+";";
                        }
                    }
                }
            Files.write(savePlayer.toPath(), save1.getBytes());
            Files.write(saveAction.toPath(), save2.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(PikGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void onShop(ActionEvent e){
        Test.leShop.setVisible(true);
    }
    
    private void onStat(ActionEvent e){
        Test.lesStats.setVisible(true);
    }
    
    private void onWait(ActionEvent e){
        turnPassing();
        tour++;
        labelTime.setText("Turn : "+tour);
        sliderPikmin.setMaximum(pikminDispo());
        refreshBoutton();
        autoSave();            
    }
    
    private void onSearch(ActionEvent e) {
        turnPassing();
        actionPikminActuel=null;
        enemyActuel=null;
        treasureActuel=null;
        autoSave();
        int x = rndGenerator.nextInt(100);
        tour++;
        panelJeuBouttons.remove(attack);
        panelJeuBouttons.remove(buttonEvent);
        panelJeuBouttons.remove(sliderPikmin);
        panelJeuBouttons.remove(qtePikmin);
        collect.setText("Collect");
        panelJeuBouttons.remove(collect);
        pick.setText("Pick");
        panelJeuBouttons.remove(pick);
        labelTime.setText("Turn : "+tour);
        refreshBoutton();
        if(x<=33){
            monsterSpawn();
        }else if(x<=48){
            pelletSpawn();
        }else if(x<=49){
            treasureSpawn();
        }else if (x<=59){
            eventSpawn();
        }else{
            nothingFound();
        }
    }
    
    private void onPick(ActionEvent e){
        if(pikminDispo()<treasureActuel.getPoids()){
            pick.setText("Pas assez de pikmin");
            return;
        } 
        pick.setText("Pick");
        panelJeuBouttons.remove(pick);
        labelDescription.setText("<html>You take the "+treasureActuel.getName()+"<br>You will gain "+treasureActuel.getValeur()+" pokos.</html>");
        refreshBoutton();
        listeAction.add(new ActionPikminCollect(treasureActuel.getPoids(),3, joueurActuel.getPseudo(), 0, treasureActuel.getValeur(), temps, new JLabel()));
        ecrireEtatPikminToutesAction();
        labelInfoPikminDispo.setText("Pikmins disponibles : "+pikminDispo());
        refreshInfo();
    }
    
    private void onAttack(ActionEvent e){
        refreshInfo();
        int dead;
        int pikmin = Integer.parseInt(qtePikmin.getText());
        if (pikmin<=0) return;
        labelJeuHautCentre.remove(labelDescriptionBombRock);
        panelJeuBouttons.remove(qtePikmin);
        panelJeuBouttons.remove(attack);
        panelJeuBouttons.remove(sliderPikmin);
        refreshBoutton();
        if (pikmin>pikminDispo()) pikmin = pikminDispo();
        if(enemyActuel.getDanger()==0){
            dead = 0;
        }else if(pikmin+bombRockPikmin>=enemyActuel.getSureKill()){
            dead = ((enemyActuel.getDanger()-rndGenerator.nextInt(enemyActuel.getDanger()/3+1))
                    -(rndGenerator.nextInt((pikmin+bombRockPikmin+1-(enemyActuel.getSureKill())))))/bitterSpray;
            if (dead<0) dead = 0;
            if (dead>=pikmin) dead=pikmin-1;
        }else{
            dead = ((enemyActuel.getDanger()-rndGenerator.nextInt(enemyActuel.getDanger()/3+1))
                    +(rndGenerator.nextInt(enemyActuel.getSureKill()-(pikmin+bombRockPikmin))))/bitterSpray;
            if (dead<0) dead = 0;
        }
        checkBitterLeft();
        if (bitterSprayLeft<=0) labelJeuHautCentre.remove(labelDescriptionBitterSpray);
        if (dead>=pikmin){
            labelImage.setIcon(ImageUtil.getIcon("/res/other/defaite.png"));
            labelDescription.setText("<html>All pikmin are dead!<br>Next time, send more of them.</html>");
            joueurActuel.gainPikmin(-(pikmin));
        } else {
            enemyKill(pikmin, dead);
            ecrireEtatPikminToutesAction();
        }
        if (joueurActuel.getPikmin()<=0){
            joueurActuel.setPikmin(0);
            labelImage.setIcon(ImageUtil.getIcon("/res/other/game_over.jpg"));
            labelDescription.setText("<html>Every Pikmin are DEAD. It's an EXTINCTION.<br>How is it even possible to loose at this game?<br>"
                    + "Well, whatever, it seems like the onion have a some power left and is able to sprout 1 pimin.<br>Don't waste "
                    + "this chance and do your best!</html>");
            panelJeuBouttons.remove(search);
            panelJeuBouttons.remove(wait);
            panelJeuBouttons.add(keepGoing);
            refreshBoutton();
        }
        labelInfoPikminDispo.setText("Pikmins disponibles : "+pikminDispo());
        enemyActuel = null;
        autoSave();
    }
    
    public void onCollect(ActionEvent e){
        refreshInfo();
        if(pikminDispo()<paletActuel.getNumber()){
            collect.setText("Pas assez de pikmin");
            return;
        } 
        collect.setText("Collect");
        labelDescription.setText("You collected the pellet "+paletActuel.getNumber()+" and you will gain "+(int) (onionPower*paletActuel.getNumber())+" pikmin");
        panelJeuBouttons.remove(collect);
        labelInfoPikmin.setText("Pikmins : "+joueurActuel.getPikmin());
        listeAction.add(new ActionPikminCollect(paletActuel.getNumber(), 4, joueurActuel.getPseudo(), (int) (onionPower*paletActuel.getNumber()), 0, temps, new JLabel()));
        ecrireEtatPikminToutesAction();
        labelInfoPikminDispo.setText("Pikmins disponibles : "+pikminDispo());
        autoSave();
    }
    
    private void onKeepGoing(ActionEvent e){
        panelJeuBouttons.remove(keepGoing);
        panelJeuBouttons.add(search);
        panelJeuBouttons.add(wait);
        labelDescription.setText("Haw shit! Here we go again");
        labelImage.setIcon(ImageUtil.getIcon("/res/other/greatings.png"));
        joueurActuel.setPikmin(1);
        labelInfoPikmin.setText("Pikmins : "+joueurActuel.getPikmin());
        refreshBoutton();
        refreshInfo();
    }
    
    private void onSlide(ChangeEvent e) {
        int p=sliderPikmin.getValue();
        this.setValeur(p);
    }
    
    private void setValeur(int p){
        sliderPikmin.setValue(p);
        qtePikmin.setText(p + "");
        refreshBoutton();
    }
    
    private void monsterSpawn(){
        temps = rndGenerator.nextInt(10)+15;
        panelJeuBouttons.remove(collect);
        refreshBoutton();
        enemyActuel = tabEnemy[rndGenerator.nextInt(tabEnemy.length)];
        /*enemyActuel = tabEnemy[testIntA++];
        System.out.println(enemyActuel.getName());
        if(testIntA<=tabEnemy.length-1) testIntA=0;*/
        labelImage.setIcon(ImageUtil.getIcon(enemyActuel.getUrl()));
        labelDescription.setText("<html>A "+ enemyActuel.getName() +" arise from the slumber.<br>Recommended : "+enemyActuel.getSureKill()
                +" or more Pimin.<br>He is "+temps+" turns from the base and weight "+enemyActuel.getPoids()+".</html>");
        panelJeuBouttons.add(attack);
        sliderPikmin.setValue(0);
        sliderPikmin.setMaximum(pikminDispo());
        panelJeuBouttons.add(sliderPikmin);
        qtePikmin.setValue(0);
        panelJeuBouttons.add(qtePikmin);
    }
    private void pelletSpawn(){
        int x = rndGenerator.nextInt(100);
        int chose;
        if (x<40) chose=0;
        else if (x<70) chose=1;
        else if (x<90) chose=2;
        else chose=3;
        paletActuel = tabPellets[chose];
        temps = (rndGenerator.nextInt(3)+2)*paletActuel.getLevelPellet();
        labelImage.setIcon(ImageUtil.getIcon(paletActuel.getUrl()));
        labelDescription.setText("<html>You found a pellet posy "+paletActuel.getNumber()+".<br>Pick it, it's free pikmin."
                + "<br>He is "+temps+" turns from the base.</html>");
        panelJeuBouttons.add(collect);
    }
    private void treasureSpawn(){
        temps = rndGenerator.nextInt(24)+15;
        treasureActuel = tabTreasure[rndGenerator.nextInt(23)];
        /*treasureActuel = tabTreasure[testIntA++];
        System.out.println(testIntA);
        if(testIntA==23) testIntA=0;*/
        labelImage.setIcon(ImageUtil.getIcon(treasureActuel.getUrl()));
        labelDescription.setText("<html>You found a treasure : "+treasureActuel.getName()+".<br>It is worth "
                +treasureActuel.getValeur()+" pokos and weighs "+treasureActuel.getPoids()+".<br>He is "+temps+" turns from the base.</html>");
        panelJeuBouttons.add(pick);
    }
    private void eventSpawn(){
        Evenement event = tabEvenement[rndGenerator.nextInt(6)];
        if (event instanceof EventWall)  eventActuel = new EventWall(event.getName(), event.getDurability(), event.getUrl(),
                ((EventWall) event).getLvl(), ((EventWall) event).getDrop1(), ((EventWall) event).getDrop2(),
                ((EventWall) event).getDrop3(), ((EventWall) event).getDrop4(), ((EventWall) event).getDrop5());
        else if (event instanceof  EventOther) eventActuel = new EventOther(event.getName(), event.getDurability(),
                event.getUrl(), ((EventOther) event).getDrop(), ((EventOther) event).getMin(), ((EventOther) event).getMax(),
                ((EventOther) event).getUrlDrop());
        labelImage.setIcon(ImageUtil.getIcon(eventActuel.getUrl()));
        panelJeuBouttons.add(buttonEvent);
        sliderPikmin.setValue(0);
        sliderPikmin.setMaximum(pikminDispo());
        panelJeuBouttons.add(sliderPikmin);
        qtePikmin.setValue(0);
        panelJeuBouttons.add(qtePikmin);
        if (eventActuel instanceof EventWall) { eventWallHappen(); }
        else {eventOtherHappen(); }
    }
    private void nothingFound(){
        labelImage.setIcon(ImageUtil.getIcon("/res/other/nothing_found.jpg"));
        labelDescription.setText("<html>You are searching but find nothing.<br>Keep going, there is probably something further.</html>");
    }
    
    private void eventWallHappen(){
        labelDescription.setText("<html>You find a wall, there is probably something behind it<br>"
                + "The "+eventActuel.getName()+" seems to have a durabiblity of "+eventActuel.getDurability()+" pikmins."
                + "<br>Send pikmin to destroy it while we keep exploring?</html>");
    }
    private void eventOtherHappen(){
        labelDescription.setText("<html>You find a "+eventActuel.getName()+", we can probably get some "+((EventOther) eventActuel).getDrop()+".<br>"
                + "The "+eventActuel.getName()+" seems to have a durabiblity of "+eventActuel.getDurability()+" pikmins."
                + "<br>Send pikmin to get them while we keep exploring?</html>");
    }
    
    private void onGo(ActionEvent e){
        int pikmin = Integer.parseInt(qtePikmin.getText());
        if (pikmin<eventActuel.getDurability()/50) {
            buttonEvent.setText("Send more pikmin pls");
            return;
        }
        buttonEvent.setText("GO");
        panelJeuBouttons.remove(buttonEvent);
        panelJeuBouttons.remove(sliderPikmin);
        panelJeuBouttons.remove(qtePikmin);
        if (eventActuel instanceof EventWall) {
            labelDescription.setText("<html>You send "+pikmin+" pikmin to destroy this wall.<br>It should take around "
                    +(eventActuel.getDurability()/pikmin)
                    +" turn to be destroyed.</html>");
            listeAction.add(new ActionPikminOther(pikmin, 5, joueurActuel.getPseudo(), eventActuel, new JButton("Result"), new JLabel()));
        }
        else {
            labelDescription.setText("<html>You send "+pikmin+" pikmin to collect "+((EventOther) eventActuel).getDrop()
                    +" at the "+eventActuel.getName()+".<br>It should take around "+(eventActuel.getDurability()/pikmin)
                    +" turn to be finished.</html>");
            listeAction.add(new ActionPikminOther(pikmin, 6,joueurActuel.getPseudo(), eventActuel, new JButton("Result"), new JLabel()));
        }
        labelInfoPikminDispo.setText("Pikmins disponibles : "+pikminDispo());
        ecrireEtatPikminToutesAction();
        refreshBoutton();
        refreshInfo();
    }
    
    private void ecrireEtatPikminToutesAction(){
        for (ActionPikmin action:listeAction){
            if (action.getProprio().equals(joueurActuel.getPseudo())) ecrireEtatPikmin(action);
        }
    }
    
    private void ecrireEtatPikmin(ActionPikmin action){
        boolean retire = false;
        JLabel label = ((ActionPikmin) action).getLabel();
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        if (action instanceof ActionPikminCollect){
            if (((ActionPikminCollect) action).getTimeLeft()<=0) retire = true;
            label.setText("<html> - Pikmin sur la tache : "+action.getNumberPikmin()
                    +". Fini dans : <font color=\"red\" size=\"5\">"+((ActionPikminCollect) action).getTimeLeft()+"</font>.<br>GAIN : "+((ActionPikminCollect) action).getGainPikmin()
                    +" Pikmin et "+((ActionPikminCollect) action).getGainPokos()+" Pokos.<br><br></html");
        } else {
            int reste;
            if(((ActionPikminOther) action).getEvent().getDurability()<=0){
                reste = 0;
                panelEtatPikmin.add(((ActionPikminOther) action).getButton());
            } 
            else 
                if ((((ActionPikminOther) action).getEvent().getDurability()%action.getNumberPikmin())==0) reste = (((ActionPikminOther) action).getEvent().getDurability()/action.getNumberPikmin());
                else reste = (((ActionPikminOther) action).getEvent().getDurability()/action.getNumberPikmin()+1);
            label.setText("<html> - "+((ActionPikminOther) action).getEvent().getName()+".<br>Pikmin sur la tache : "+action.getNumberPikmin()+". durabilité : "
                +((ActionPikminOther) action).getEvent().getDurability()+".<br>Temps restant éstimé : <font color=\"red\" size=\"5\">"+reste+"</font><br><br></html>");
        }
        panelEtatPikmin.add(label);
        if (retire) {
            panelEtatPikmin.remove(label);
        }
        panelEtatPikmin.revalidate();
        panelEtatPikmin.repaint();
    }
    
    private void turnPassing(){
        attendre();
        checkSpicyLeft();
        if (spicySprayLeft<=0) labelJeuHautCentre.remove(labelDescriptionSpicySpray);
        refreshBoutton();
        for(ActionPikmin action:listeAction){
            if (action.getProprio().equals(joueurActuel.getPseudo())){
                if(action instanceof ActionPikminCollect){
                    if(((ActionPikminCollect) action).reduceTime(spicySpray)){
                        joueurActuel.gainPikmin(((ActionPikminCollect) action).getGainPikmin());
                        joueurActuel.gainPokos(((ActionPikminCollect) action).getGainPokos());
                        labelInfoPikmin.setText("Pikmins : "+joueurActuel.getPikmin());
                        labelInfoPokos.setText("Pokos : "+joueurActuel.getPokos());
                        refreshInfo();
                        listeActionARemove.add(action);
                    }
                }else if (action instanceof ActionPikminOther){
                    if(((ActionPikminOther) action).reduceDurability(spicySpray)){
                        ((ActionPikminOther) action).setDurability(0);
                        action.setNumberPikmin(0);
                        JButton button = ((ActionPikminOther)action).getButton();
                        (button).addActionListener(this::onCollectOther);
                        panelEtatPikmin.add(button);
                        mapLienEventButton.put(button, (ActionPikminOther)action);
                    }
                }
                ecrireEtatPikmin(action);
            }
        }
        for(ActionPikmin actionRemove:listeActionARemove){
            listeAction.remove(actionRemove);
        }
        listeActionARemove.clear();
        labelInfoPikminDispo.setText("Pikmins disponibles : "+pikminDispo());
        refreshInfo();
        panelEtatPikmin.revalidate();
        panelEtatPikmin.repaint();
    }
    
    private void onCollectOther(ActionEvent e){
        JButton button = (JButton) e.getSource();
        ActionPikminOther action = mapLienEventButton.remove(button);
        Evenement event;
        try{
            event = action.getEvent();
        }catch (java.lang.NullPointerException ex){
            return;
        }
        event = action.getEvent();
        listeAction.remove(action);
        panelEtatPikmin.remove(button);
        panelEtatPikmin.remove(action.getLabel());
        panelEtatPikmin.revalidate();
        panelEtatPikmin.repaint();
        //affichage
        panelJeuBouttons.remove(attack);
        panelJeuBouttons.remove(buttonEvent);
        panelJeuBouttons.remove(qtePikmin);
        panelJeuBouttons.remove(sliderPikmin);
        panelJeuBouttons.remove(pick);
        panelJeuBouttons.remove(collect);
        if (event instanceof EventOther){
            labelImage.setIcon(ImageUtil.getIcon(((EventOther)event).getUrlDrop()));
            int gain = rndGenerator.nextInt(((EventOther) event).getMax())+((EventOther) event).getMin();
            labelDescription.setText("Your pikmin dropped "+gain+" "+((EventOther) event).getDrop()+" from the "+event.getName()+".");
            if (((EventOther) event).getDrop().equals("bomb-rock")){
                joueurActuel.gainBombRock(gain);
                labelInfoBombRock.setText("Bomb-rock : "+joueurActuel.getBombRock());
            }
            else if (((EventOther) event).getDrop().equals("spicy-spray")){
                joueurActuel.gainSpicySpray(gain);
                labelInfoSpicySpray.setText("Spicy-spray : "+joueurActuel.getSpicySpray());
            }
            else if (((EventOther) event).getDrop().equals("bitter-spray")){
                joueurActuel.gainBitterSpray(gain);
                labelInfoBitterSpray.setText("Bitter-spray : "+joueurActuel.getBitterSpray());
            }
            refreshInfo();
        } else if (event instanceof EventWall) {
            treasureActuel = wallDestroyed((EventWall)event);
            if (treasureActuel==null){
                labelDescription.setText("There was nothing behind the wall. Too bad!");
                labelImage.setIcon(null);
            }
            else{
                temps = rndGenerator.nextInt(16)+15;
                labelDescription.setText("<html>You find a "+treasureActuel.getName()+" behind the wall<br>"
                    + "It worth "+treasureActuel.getValeur()+" and weight "+treasureActuel.getPoids()+".<br>You are "+temps+" turn from the base.</html>");
                panelJeuBouttons.add(pick);
                labelImage.setIcon(ImageUtil.getIcon(treasureActuel.getUrl()));
            }
        }
        refreshBoutton();
        autoSave();
    }
    
    public int pikminDispo(){
        int nbPikminLibre;
        if (joueurActuel.getPikmin()<100+(joueurActuel.getOnionSizeLvl()-1)*40) nbPikminLibre = joueurActuel.getPikmin();
        else nbPikminLibre = 100+(joueurActuel.getOnionSizeLvl()-1)*40;
        for(ActionPikmin action:listeAction){
            if (action.getProprio().equals(joueurActuel.getPseudo())){
                nbPikminLibre -= action.getNumberPikmin();
                //sécurité, je pense qu'elle est inutile mais je laisse
                if (nbPikminLibre<0) nbPikminLibre=0;
            }
        }
        return nbPikminLibre;
    }
    
    private Treasure dropTreasureRarete(Enemy enemy){
        int chance = rndGenerator.nextInt(100);
        if (chance<enemy.getDropRare1()) return dropTreasureSelonRarete(1);
        else if (chance<enemy.getDropRare1()+enemy.getDropRare2()) return dropTreasureSelonRarete(2);
        else if (chance<enemy.getDropRare1()+enemy.getDropRare2()+enemy.getDropRare3()) return dropTreasureSelonRarete(3);
        else if (chance<enemy.getDropRare1()+enemy.getDropRare2()+enemy.getDropRare3()+enemy.getDropRare4()) return dropTreasureSelonRarete(4);
        else  return dropTreasureSelonRarete(5);
    }
    
    private Treasure dropTreasureSelonRarete(int rare){
        List<Treasure> listTreasureQuiReste = new ArrayList<>();
        for(int i=0; i<tabTreasure.length; i++){
            if(tabTreasure[i].getRareté()==rare)listTreasureQuiReste.add(tabTreasure[i]);
        }
        return listTreasureQuiReste.get(rndGenerator.nextInt(listTreasureQuiReste.size()));
    }
    
    private void enemyKill(int pikmin, int dead){
        if(pikmin-dead>=enemyActuel.getPoids()) enemyKillOk(pikmin, dead);
        else enemyKillNo(pikmin, dead);
    }
    
    private void enemyKillNo(int pikmin, int dead){
        if (rndGenerator.nextInt(1)<enemyActuel.getDropChance()){
            treasureActuel = dropTreasureRarete(enemyActuel);
            panelJeuBouttons.add(pick);
            labelImage.setIcon(ImageUtil.getIcon(treasureActuel.getUrl()));
            labelDescription.setText("<html>Enemy defeated!<br>Pikmin send : "+(pikmin+bombRockPikmin)+"<br>Pikmin dead : "+dead+"<br>"
                    +enemyActuel.getDeathWord()+ "<br>There isn't enough survivor to bring the corpse to the onion, you're forced to let it there.<br>"+
                    "Sending other pikmin to take it would only mean that you allow them to steal somebody else prey and that is not nice at all."
                    +"<br>The "+enemyActuel.getName()+" dropped a "
                    +treasureActuel.getName()+" who worth "+treasureActuel.getValeur()+" pokos and weight "+treasureActuel.getPoids()+"</html>");
        }else{
            labelDescription.setText("<html>Enemy defeated!<br>Pikmin send : "+(pikmin+bombRockPikmin)+"<br>Pikmin dead : "+dead+"<br>"
                    +enemyActuel.getDeathWord()+ "<br>There isn't enough survivor to bring the corpse to the onion, you're forced to let it there.<br>"+
                    "Sending other pikmin to take it would only mean that you allow them to steal somebody else prey and that is not nice at all.</html>");
        }
        bombRockPikmin = 0;
        enemyActuel = null;
    }
    
    private void enemyKillOk(int pikmin, int dead){
        int sprouted = (int) (enemyActuel.getAvgGain()*onionPower);
        int profit = sprouted-dead;
        if (rndGenerator.nextInt(100)<enemyActuel.getDropChance()){
            treasureActuel = dropTreasureRarete(enemyActuel);
            panelJeuBouttons.add(pick);
            labelImage.setIcon(ImageUtil.getIcon(treasureActuel.getUrl()));
            labelDescription.setText("<html>Enemy defeated!<br>Pikmin send : "+(pikmin+bombRockPikmin)+"<br>Pikmin dead : "+dead+"<br>Pikmin will be sprouted from corpse : "
                    +sprouted+"<br>Profit : "+profit+"<br>"+enemyActuel.getDeathWord()+"<br>The "+enemyActuel.getName()+" dropped a "
                    +treasureActuel.getName()+" who worth "+treasureActuel.getValeur()+" pokos and weight "+treasureActuel.getPoids()+".</html>");
        }else{
            labelDescription.setText("<html>Enemy defeated!<br>Pikmin send : "+(pikmin+bombRockPikmin)+"<br>Pikmin dead : "+dead+"<br>Pikmin sprouted from corpse : "
                    +sprouted+"<br>Profit : "+profit+"<br>"+enemyActuel.getDeathWord()+"</html>");
        }
        actionPikminActuel = new ActionPikminCollect(pikmin-dead, 1,joueurActuel.getPseudo(),profit, 0, temps, new JLabel());
        listeAction.add(actionPikminActuel);
        bombRockPikmin = 0;
        enemyActuel = null;
    }
    
    private Treasure wallDestroyed(EventWall event){
        int chance = rndGenerator.nextInt(100);
        if (chance<event.getDrop1()) return dropTreasureSelonRarete(1);
        else if (chance<event.getDrop1()+event.getDrop2()) return dropTreasureSelonRarete(2);
        else if (chance<event.getDrop1()+event.getDrop2()+event.getDrop3()) return dropTreasureSelonRarete(3);
        else if (chance<event.getDrop1()+event.getDrop2()+event.getDrop3()+event.getDrop4()) return dropTreasureSelonRarete(4);
        else if (chance<event.getDrop1()+event.getDrop2()+event.getDrop3()+event.getDrop4()+event.getDrop5()) return dropTreasureSelonRarete(5);
        else  return null;
    }
    
    public void attendre(){
        new Thread(() -> {
            try {
                search.setEnabled(false);
                wait.setEnabled(false);
                Thread.sleep(1000);
                search.setEnabled(true);
                wait.setEnabled(true);
            } catch(InterruptedException e) {} //nope
        }).start();
    }
    
    private void onChangeArea(ActionEvent e) {
        //on vérifie qu'il n'y a plus de pikmin sur le terrain
        if (pikminDispo()!=joueurActuel.getOnionSizeLvl()*40+60 || pikminDispo()!=joueurActuel.getPikmin()){
            pikRestant.setText("<html>"+pikminDispo()+" Pikmins sont occupés à une taches,<br>Si vous partez, ils seront abandonnés.</html>");
        }
        else {
            pikRestant.setText("");
        }
        // on affiche la dialog et vérifie qu'on a bien une confirmation
        if(JOptionPane.showConfirmDialog(fenetre, panelZone, "Changer de zone", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            //On récupere les infos
            labelImage.setIcon(null);
            labelJeu.setIcon(ImageUtil.getIcon("/res/other/Change_Zone.png"));
            refreshBoutton();
            panelJeuBouttons.removeAll();
            panelJeuBouttons.add(land);
            labelDescription.setText("<html>You're heading from the "+zoneActuel.getName()+" to the "+zoneChoix.getName()+".<br>Let's find more treasure!");
            zoneActuel=zoneChoix;
            zoneChoix = null;
            labelZoneChoix.setText("Selected : None");
            listeAction.clear();
            autoSave();
            panelEtatPikmin.removeAll();
            panelEtatPikmin.revalidate();
            panelEtatPikmin.repaint();
        }
    }
    
    private void playerUpdateInfo(){
        labelDescription.setText("<<I was waiting for you. Shall we get started?>>");
        labelPlayerActuel.setText("Actual : "+joueurActuel.getPseudo());
        tour = joueurActuel.getTour()+1;
        bombRockPower = 25 + (25*joueurActuel.getBombRockPowerLvl());
        spicySprayPower = 1 + joueurActuel.getSpicySprayPowerLvl();
        spicySprayDuration = 15 + (15*joueurActuel.getSpicySprayDurationLvl());
        bitterSprayPower = 1 + joueurActuel.getBitterSprayPowerLvl();
        bitterSprayDuration = 1 + joueurActuel.getBitterSprayDurationLvl();
        labelInfoPikmin.setText("Pimins : "+ joueurActuel.getPikmin());
        labelInfoPokos.setText("Pokos : "+ joueurActuel.getPokos());
        labelInfoOnionLvl.setText("Onion size level : "+ joueurActuel.getOnionSizeLvl());
        labelInfoBombRock.setText("Bomb-rock : "+ joueurActuel.getBombRock());
        labelInfoSpicySpray.setText("Spicy spray : "+ joueurActuel.getSpicySpray());
        labelInfoBitterSpray.setText("Bitter spray : "+ joueurActuel.getBitterSpray());
        sliderPikmin.setMaximum(100+(joueurActuel.getOnionSizeLvl()-1)*30);
        onionPower = 1;
        labelInfoPikminDispo.setText("Pikmins disponibles : "+pikminDispo());
        for (int i=1; i<joueurActuel.getOnionPowerLvl(); i++){
            onionPower *=1.2;
        }
        joueurChoix=joueurActuel;
        labelPlayerChoix.setText("Selected : None");
        labelJeu.setIcon(ImageUtil.getIcon("/res/other/Wild_Background.png"));
        labelImage.setIcon(ImageUtil.getIcon("/res/other/greatings.png"));
        Test.leShop.changePlayerShop();
        Test.leShop.refreshShop();
        refreshBoutton();
        refreshInfo();
        panelEtatPikmin.removeAll();
        panelEtatPikmin.revalidate();
        panelEtatPikmin.repaint();
        ecrireEtatPikminToutesAction();
    }
    
    private void onButtonZone(ActionEvent e){
        zoneChoix = mapLienButtonZone.get((JButton) (e.getSource()));
        labelZoneChoix.setText("Selected : "+ zoneChoix.getName());
        panelZone.revalidate();
        panelZone.repaint();
    }
    
    private void onButtonPlayer(ActionEvent e){
        joueurChoix = mapLienButtonPlayer.get((JButton) (e.getSource()));
        labelPlayerChoix.setText("Selected : "+ joueurChoix.getPseudo());
        panelPlayer.revalidate();
        panelPlayer.repaint();
    }
    
    private void onLand(ActionEvent e){
        labelJeu.setIcon(ImageUtil.getIcon(zoneActuel.getImage()));
        panelJeuBouttons.remove(land);
        panelJeuBouttons.add(wait);
        panelJeuBouttons.add(search);
        labelDescription.setText("<html>You arrived at the "+zoneActuel.getName()+".<br>Let's go!</html>");
        refreshBoutton();
    }
    
        
    private void onChangePlayer(ActionEvent e) {
        // on affiche la dialog et vérifie qu'on a bien une confirmation
        if(JOptionPane.showConfirmDialog(fenetre, panelPlayer, "Changer de joueur", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            //On change le joueur et met ses infos
            autoSave();
            pseudo.setText("<html><font size=\"6\"><u>"+joueurChoix.getPseudo()+"</u></font></html>");
            joueurActuel=joueurChoix;
            playerUpdateInfo();
        }
    }
    
    private void onNewPlayer(ActionEvent e){
        // on affiche la dialog et vérifie qu'on a bien une confirmation
        if(JOptionPane.showConfirmDialog(fenetre, panelNewPlayer, "Nouveau de joueur", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            if (nameNewPlayer.getText().equals("")) return;
            for (Joueur j:mapLienButtonPlayer.values()) if (nameNewPlayer.getText().equals(j.getPseudo())) return;
            Joueur player = new Joueur(nameNewPlayer.getText(), 10, 100, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1);
            mapLienButtonPlayer.put(new JButton(player.getPseudo()), player);
            joueurActuel=player;
            playerUpdateInfo();
            autoSave();
            Window w = SwingUtilities.getWindowAncestor(labelPlayerActuel);
            if (w != null) {
              w.setVisible(false);
            }
            refreshPanelPlayer();
        }
    }
    
    private void onDeletePlayer(ActionEvent e){
        // on affiche la dialog et vérifie qu'on a bien une confirmation
        if(JOptionPane.showConfirmDialog(fenetre, panelDeletePlayer, "Nouveau de joueur", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            if (!(sureDeletePlayer.getText().equals(joueurChoix.getPseudo()))) return;
            JButton bBis = null;
            for (JButton b:mapLienButtonPlayer.keySet()){
                if (mapLienButtonPlayer.get(b).getPseudo().equals(joueurChoix.getPseudo())) bBis = b;
            }
            mapLienButtonPlayer.remove(bBis);
            playerUpdateInfo();
            autoSave();
            Window w = SwingUtilities.getWindowAncestor(labelPlayerActuel);
            if (w != null) {
              w.setVisible(false);
            }
            refreshPanelPlayer();
            for (ActionPikmin action:listeAction){
                if (action.getProprio().equals(joueurChoix.getPseudo())) listeActionARemove.add(action);
            }
            for (ActionPikmin action:listeActionARemove){
                listeAction.remove(action);
            }
            listeActionARemove.clear();
        }
    }
    
    //tentative d'essai d'une class util
    public void useBombRock(ActionEvent e) { useBombRockUtil(); }
    public void useSpicySpray(ActionEvent e) { useSpicySprayUtil(); }
    public void useBitterSpray(ActionEvent e) { useBitterSprayUtil(); }
    
    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e) {
        int p=Integer.parseInt(qtePikmin.getText());
        if (p>pikminDispo()){
            p=pikminDispo();
        }else if (p<0){
            p=0;
        }
        this.setValeur(p);
    }
}