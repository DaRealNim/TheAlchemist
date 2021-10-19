import java.util.HashMap;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.io.*;

public class FontManager {
    private static HashMap<String, Font> fonts = new HashMap<String, Font>();

    public static boolean registerFont(String identifier, String path) {
        if (fonts.containsKey(identifier)) {
            System.out.println("[FontManager] WARNING: Font with identifier "+identifier+" was already registered");
            return false;
        }
        Font font;
        try {
            File ttf = new File(path);
            InputStream is = new FileInputStream(ttf);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            fonts.put(identifier, font);
            System.out.println("[FontManager] Registered font "+identifier+" with path "+path);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("[FontManager] ERROR: Can't find file "+path);
        } catch (FontFormatException e) {
            System.out.println("[FontManager] ERROR: Wrong font format "+path);
        } catch (IOException e) {
            System.out.println("[FontManager] ERROR: Error reading "+path);
        }
        return false;
    }

    public static Font getFont(String identifier) {
        if (!fonts.containsKey(identifier)) {
            System.out.println("[FontManager] ERROR: Can't find font with identifier "+identifier);
            return null;
        }
        return fonts.get(identifier);
    }

}
