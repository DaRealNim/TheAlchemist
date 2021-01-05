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
        setBackground(new Color(0,0,0,0));

        sprite = SpriteManager.getSprite("spr_popup");

        Font font = FontManager.getFont("fnt_planewalker");
        font = font.deriveFont(40f);

        JLabel label = new JLabel(title);
        label.setSize(400,50);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        label.setVisible(true);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        changeOrRetryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 50)));
        add(label);
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(changeOrRetryButton);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(menuButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite, 0, 0, 400, 500, this);
    }
}
