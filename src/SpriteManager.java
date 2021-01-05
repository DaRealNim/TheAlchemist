import java.util.HashMap;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteManager {
    private static HashMap<String, BufferedImage> sprites = new HashMap<String, BufferedImage>();

    public static boolean registerSprite(String identifier, String path) {
        if (sprites.containsKey(identifier)) {
            System.out.println("WARNING: Sprite with identifier "+identifier+" was already registered");
            return false;
        }
        BufferedImage sprite;
        try {
            sprite = ImageIO.read(new File(path));
            sprites.put(identifier, sprite);
            return true;
        } catch (IOException ex) {
            System.out.println("ERROR: Can't load sprite "+path);
        }
        return false;
    }

    public static BufferedImage getSprite(String identifier) {
        if (!sprites.containsKey(identifier)) {
            System.out.println("ERROR: Can't find sprite with identifier "+identifier);
            return null;
        }
        return sprites.get(identifier);
    }

}
