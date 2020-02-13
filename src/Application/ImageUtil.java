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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtil {
    public static BufferedImage resize(BufferedImage img, int width, int height) {
        BufferedImage out=new BufferedImage(width, height, img.getType());
        Graphics2D g=out.createGraphics();
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();
        return out;
    }
    public static BufferedImage getImageResource(String name) {
        try {
            return ImageIO.read(ImageUtil.class.getResource(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static BufferedImage getImageResource(String name, int width, int height) {
        return resize(getImageResource(name), width, height);
    }
    public static ImageIcon getIcon(String name) {
        return new ImageIcon(getImageResource(name));
    }
    public static ImageIcon getIcon(String name, int width, int height) {
        return new ImageIcon(getImageResource(name, width, height));
    }
}
