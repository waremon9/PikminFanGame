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
public class EventOther extends Evenement{
    private String drop;
    private int min;
    private int max;
    private String urlDrop;

    public EventOther(String name, int stamina, String url, String drop, int min, int max, String urlDrop) {
        super(name, stamina, url);
        this.drop = drop;
        this.min = min;
        this.max = max;
        this.urlDrop = urlDrop;
    }

    public String getDrop() {
        return drop;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getUrlDrop() {
        return urlDrop;
    }
}
