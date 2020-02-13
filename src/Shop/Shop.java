/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import Application.FileUtil;
import Application.Test;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author verhi
 */
public class Shop implements MouseListener{
    private static JButton[] button = null;
    private static Map<JButton,ItemShop> mapLienButtonItem = new HashMap<JButton,ItemShop>();
    static{
        try {
            List<String> itemsShop=FileUtil.readResourceLines("/res/items_shop.txt");
            button = new JButton[itemsShop.size()];
            int y=0;
            int x=-1;
            for (String truc:itemsShop){
                if(x!=-1) {
                    String[] infoItemsShop = truc.split(":");
                    int id = Integer.parseInt(infoItemsShop[0]);
                    int group = Integer.parseInt(infoItemsShop[1]);
                    String name = infoItemsShop[2];
                    int price = Integer.parseInt(infoItemsShop[3]);
                    String monnaie = infoItemsShop[4];
                    String description = infoItemsShop[5];
                    button[x]=new JButton("Buy");
                    if (group==1) mapLienButtonItem.put(button[x], new ItemShopUpgrade(id, name, price, monnaie, description, new JLabel(""), Test.leJeu.joueurActuel.getMapLienItemLvl().get(name)));
                    else if (group==2) mapLienButtonItem.put(button[x], new ItemShopItems(id, name, price, monnaie, description));
                    else if (group==3) mapLienButtonItem.put(button[x], new ItemShopOther(id, name, price, monnaie, description));
                }
                x++;
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private JFrame fenetreShop=new JFrame();
    
    private JPanel panelFenetre=new JPanel();
    private JPanel panelChoix=new JPanel();
    private JPanel panelShop1=new JPanel();
    private JPanel panelShop2=new JPanel();
    private JPanel panelShopUpgrade=new JPanel();
    private JPanel panelShopItems=new JPanel();
    private JPanel panelShopOthers=new JPanel();
    
    private JButton upgrade = new JButton("Upgrade");
    private JButton items = new JButton("Items");
    private JButton others = new JButton("Others");
    
    private JLabel description = new JLabel();
    
    //private JButton[] tabJButton = new JButton[12];
    
    public Shop(){
        // créé la fenêtre en elle-même 
        fenetreShop.setSize(800, 500);
        fenetreShop.setTitle("Fenetre Shop");
        fenetreShop.setLocationRelativeTo(Test.leJeu.labelImage);
        fenetreShop.setResizable(false);

        // créé le contentPane
        fenetreShop.getContentPane().add(panelFenetre);
        
        panelFenetre.setLayout(new BorderLayout());
        panelFenetre.add("West",panelChoix);
        panelFenetre.add("Center",panelShop1);
        
        panelChoix.setPreferredSize(new Dimension(250, 0));
        panelChoix.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panelChoix.setLayout(new GridLayout(3, 1, 0, 10));
        upgrade.addActionListener(this::onUpgrade);
        items.addActionListener(this::onItems);
        others.addActionListener(this::onOthers);
        panelShopUpgrade.setLayout(new GridLayout(0,3));
        panelShopItems.setLayout(new GridLayout(0,3));
        panelShopOthers.setLayout(new GridLayout(0,3));
        for(JButton key:mapLienButtonItem.keySet()){
            key.addMouseListener(this);
            key.addActionListener(this::onBuyToutEstNImporteQuoiUpgrade);
            ItemShop item = mapLienButtonItem.get(key);
            if( item instanceof ItemShopUpgrade){
                panelShopUpgrade.add(new JLabel(item.getName()));
                ((ItemShopUpgrade)item).getLabel().setText((((ItemShopUpgrade)item).getLvl()*item.getPrix()) + " " +item.getMonnaie());
                panelShopUpgrade.add(((ItemShopUpgrade)item).getLabel());
                panelShopUpgrade.add(key);
            }else if(item instanceof ItemShopItems){
                panelShopItems.add(new JLabel(item.getName()));
                panelShopItems.add(new JLabel(item.getPrix()+" "+item.getMonnaie()));
                panelShopItems.add(key);
            }else if(item instanceof ItemShopOther){
                panelShopOthers.add(new JLabel(item.getName()));
                panelShopOthers.add(new JLabel(item.getPrix()+" "+item.getMonnaie()));
                panelShopOthers.add(key);
            }
        }
        
        panelChoix.add(upgrade);
        panelChoix.add(items);
        panelChoix.add(others);
        
        panelShop1.setLayout(new BorderLayout());
        panelShop1.add("Center",panelShop2);
        description.setPreferredSize(new Dimension(0, 50));
        panelShop1.add("South",description);
        
        panelShop2.setLayout(new GridLayout(1,1));
        panelShop2.add(panelShopUpgrade);
    }
    
    private void onBuyToutEstNImporteQuoiUpgrade(ActionEvent e){
        ItemShop item = mapLienButtonItem.get((JButton) e.getSource());
        if (item.getMonnaie().equals("Pikmins")) {
            if(Test.leJeu.joueurActuel.getPikmin()<(((ItemShopUpgrade)item).getLvl()*item.getPrix())+1) return;
            Test.leJeu.joueurActuel.gainPikmin(-(((ItemShopUpgrade)item).getLvl()*item.getPrix()));
        }
        else if (item.getMonnaie().equals("pokos")) {
            if (item instanceof ItemShopUpgrade){
                if(Test.leJeu.joueurActuel.getPokos()<(((ItemShopUpgrade)item).getLvl()*item.getPrix())) return;
                Test.leJeu.joueurActuel.gainPokos(-(((ItemShopUpgrade)item).getLvl()*item.getPrix()));
            } else {
                if(Test.leJeu.joueurActuel.getPokos()<(item.getPrix())) return;
                Test.leJeu.joueurActuel.gainPokos(-(item.getPrix()));
            }
        }
        if (item instanceof ItemShopUpgrade) ((ItemShopUpgrade)item).levelUp(item.getName());
        if (item.getName().equals("Pellet 50")){
            Test.leJeu.joueurActuel.gainPikmin((int) (50*Test.leJeu.onionPower));
        }
        else if (item.getName().equals("Pellet 100")){
            Test.leJeu.joueurActuel.gainPikmin((int) (100*Test.leJeu.onionPower));
        }
        else if (item.getName().equals("Bomb-rock")){
            Test.leJeu.joueurActuel.gainBombRock(1);
            Test.leJeu.labelInfoBombRock.setText("Bomb-rock : "+Test.leJeu.joueurActuel.getBombRock());
        }
        else if (item.getName().equals("Spicy spray")){
            Test.leJeu.joueurActuel.gainSpicySpray(1);
            Test.leJeu.labelInfoSpicySpray.setText("Spicy spray : "+Test.leJeu.joueurActuel.getSpicySpray());
        }
        else if (item.getName().equals("Bitter spray")){
            Test.leJeu.joueurActuel.gainBitterSpray(1);
            Test.leJeu.labelInfoBitterSpray.setText("Bitter spray : "+Test.leJeu.joueurActuel.getBitterSpray());
        }
        else if (item.getName().equals("Onion size")){
            
        }
        else if (item.getName().equals("Onion power")) {
            Test.leJeu.onionPower*=1.2;
        }
        else if (item.getName().equals("Bomb-rock power")) {
            Test.leJeu.bombRockPower+=25;
        }
        else if (item.getName().equals("Spicy spray power")) {
            Test.leJeu.spicySprayPower+=1;
        }
        else if (item.getName().equals("Spicy spray duration")) {
            Test.leJeu.spicySprayDuration+=15;
        }
        else if (item.getName().equals("Bitter spray power")) {
            Test.leJeu.bitterSprayPower+=1;
        }
        else if (item.getName().equals("Bitter spray duration")) {
            Test.leJeu.bitterSprayDuration+=1;
        }
        Test.leJeu.labelInfoPikmin.setText("Pikmins : "+Test.leJeu.joueurActuel.getPikmin());
        Test.leJeu.labelInfoPokos.setText("Pokos : "+Test.leJeu.joueurActuel.getPokos());
        Test.leJeu.labelInfoPikminDispo.setText("Pikmins disponibles : "+ Test.leJeu.pikminDispo());
        Test.leJeu.refreshInfo();
        Test.leJeu.sliderPikmin.setMaximum(Test.leJeu.pikminDispo());
        Test.leJeu.refreshBoutton();
        if (item instanceof ItemShopUpgrade) ((ItemShopUpgrade)item).getLabel().setText((((ItemShopUpgrade)item).getLvl()*item.getPrix()) + " " +item.getMonnaie());
        refreshShop();
        Test.leJeu.autoSave();
    }
    
    
    private void onItems(ActionEvent e){
        panelShop2.remove(panelShopUpgrade);
        panelShop2.remove(panelShopOthers);
        panelShop2.add(panelShopItems);
        refreshShop();
    }
    private void onUpgrade(ActionEvent e){
        panelShop2.remove(panelShopOthers);
        panelShop2.remove(panelShopItems);
        panelShop2.add(panelShopUpgrade);
        refreshShop();
    }
    private void onOthers(ActionEvent e){
        panelShop2.remove(panelShopUpgrade);
        panelShop2.remove(panelShopItems);
        panelShop2.add(panelShopOthers);
        refreshShop();
    }
    
    public void refreshShop(){
        panelFenetre.revalidate();
        panelFenetre.repaint();
    }
    
    public void setVisible(boolean visible) {
        this.fenetreShop.setVisible(visible);
    }
    
    public void changePlayerShop(){
        panelShopItems.removeAll();
        panelShopOthers.removeAll();
        panelShopUpgrade.removeAll();
        for(ItemShop item:mapLienButtonItem.values()){
            if (item instanceof ItemShopUpgrade){
                ((ItemShopUpgrade) item).setLvl(Test.leJeu.joueurActuel.getMapLienItemLvl().get(item.getName()));
            }
        }
        for(JButton key:mapLienButtonItem.keySet()){
            key.addMouseListener(this);
            key.addActionListener(this::onBuyToutEstNImporteQuoiUpgrade);
            ItemShop item = mapLienButtonItem.get(key);
            if( item instanceof ItemShopUpgrade){
                panelShopUpgrade.add(new JLabel(item.getName()));
                ((ItemShopUpgrade)item).getLabel().setText((((ItemShopUpgrade)item).getLvl()*item.getPrix()) + " " +item.getMonnaie());
                panelShopUpgrade.add(((ItemShopUpgrade)item).getLabel());
                panelShopUpgrade.add(key);
            }else if(item instanceof ItemShopItems){
                panelShopItems.add(new JLabel(item.getName()));
                panelShopItems.add(new JLabel(item.getPrix()+" "+item.getMonnaie()));
                panelShopItems.add(key);
            }else if(item instanceof ItemShopOther){
                panelShopOthers.add(new JLabel(item.getName()));
                panelShopOthers.add(new JLabel(item.getPrix()+" "+item.getMonnaie()));
                panelShopOthers.add(key);
            }
        }
        refreshShop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        description.setText(mapLienButtonItem.get(e.getSource()).getDescription());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        description.setText(" ");
    }
}
