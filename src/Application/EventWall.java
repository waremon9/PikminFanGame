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
public class EventWall extends Evenement{
    private int lvl;
    private int drop1;
    private int drop2;
    private int drop3;
    private int drop4;
    private int drop5;

    public EventWall(String name, int durablility, String url, int lvl, int drop1, int drop2, int drop3, int drop4, int drop5) {
        super(name, durablility, url);
        this.lvl = lvl;
        this.drop1 = drop1;
        this.drop2 = drop2;
        this.drop3 = drop3;
        this.drop4 = drop4;
        this.drop5 = drop5;
    }

    public int getLvl() {
        return lvl;
    }

    public int getDrop1() {
        return drop1;
    }

    public int getDrop2() {
        return drop2;
    }

    public int getDrop3() {
        return drop3;
    }

    public int getDrop4() {
        return drop4;
    }

    public int getDrop5() {
        return drop5;
    }
}
