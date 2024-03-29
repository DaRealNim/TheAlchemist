import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class CustomButton extends JPanel implements MouseInputListener {
    JButton button;
    JLabel label;
    BufferedImage sprite;
    Runnable action;
    int sizeX;
    int sizeY;

    public CustomButton(String text, BufferedImage sprite, int sizeX, int sizeY, Runnable action) {
        setLayout(new GridBagLayout());
        this.button = button;
        this.action = action;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        setSize(sizeX, sizeY);
        setMaximumSize(new Dimension(sizeX, sizeY));

        Font font = FontManager.getFont("fnt_planewalker");
        font = font.deriveFont(30f);

        JLabel label = new JLabel(text);
        label.setSize(sizeX,50);
        label.setForeground(Color.WHITE);
        label.setFont(font);

        this.label = label;
        this.sprite = sprite;

        add(label);
        setOpaque(false);
        addMouseListener(this);
    }

    public void setFontSize(int n) {
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, n));
    }

    public void mouseClicked(MouseEvent event) {
        AudioManager.playSound("click", false);
        action.run();
    }
    public void mouseEntered(MouseEvent event) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    public void mouseExited(MouseEvent event) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseDragged(MouseEvent event) {}
    public void mouseMoved(MouseEvent event) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite, 0, 0, sizeX, sizeY, this);
    }
}
