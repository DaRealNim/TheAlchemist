import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class CustomPopup extends JPanel {

    BufferedImage sprite;

    public CustomPopup(String title, CustomButton changeOrRetryButton, CustomButton menuButton) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setLocation(200, 250);
        setSize(400, 500);
        // setBackground(new Color(128, 128, 128));
        setBackground(new Color(0,0,0,0));

        sprite = SpriteManager.getSprite("spr_popup");

        Font font = null;
        try {
            File ttf = new File("./res/fonts/planewalker.ttf");
            InputStream is = new FileInputStream(ttf);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find font.tff");
        } catch (FontFormatException e) {
            System.out.println("Wrong font format");
        } catch (IOException e) {
            System.out.println("NOT FOUND");
        }
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(font);
        font = font.deriveFont(40f);

        JLabel label = new JLabel(title);
        label.setLocation(10,0);
        label.setSize(200,50);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        label.setVisible(true);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 30)));
        add(label);
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(changeOrRetryButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(menuButton);

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite, 0, 0, 400, 500, this);
    }
}
