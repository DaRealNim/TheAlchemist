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

    public CustomButton(String text, String spritePath, int sizeX, int sizeY, Runnable action) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.button = button;
        this.action = action;
        setSize(sizeX, sizeY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        // setBackground(new Color(128, 128, 128));

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
        font = font.deriveFont(30f);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        // label.setLocation(0,20);
        label.setSize(800,50);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        label.setVisible(true);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setAlignmentY(CENTER_ALIGNMENT);
        this.label = label;

        try {
            sprite = ImageIO.read(new File(spritePath));
        } catch (IOException ex) { System.out.println("INVALID PATH");}

        add(Box.createRigidArea(new Dimension(0, (sizeY > 50) ? 30 : 10)));
        add(label);
        setOpaque(false);
        setVisible(true);
        addMouseListener(this);
    }

    public void setFontSize(int n) {
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, n));
    }

    public void mouseClicked(MouseEvent event) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./res/sounds/click.wav").toURI().toURL());
            Clip sound = AudioSystem.getClip();
            sound.open(audioInputStream);
            sound.start();
            System.out.println("played!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
