/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author verhi
 */
public class PlaySound{
    private static Clip clip;
    public static void playSound(String music) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(System.getenv("HOME"), "src/res/other/"+music));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    public static void stopSound(){
        clip.stop();
    }
}