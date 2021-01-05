import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Window extends JFrame {

    Font font;
    MouseIcon mouseIcon;

    public Window() {
        setTitle("The Alchemist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        setContentPane(new JPanel(null));
        getContentPane().setBackground(rgbToColor("#120458"));
        getContentPane().setPreferredSize(new Dimension(800, 1000));
        pack();

        font = FontManager.getFont("fnt_planewalker");
        font = font.deriveFont(30f);

        mouseIcon = new MouseIcon();
        getContentPane().add(mouseIcon);

    }

    public void paintGrid(Grid grid, int from, int to) {
        getContentPane().removeAll();
        int startXPos = (800 - (grid.getWidth()*Block.BLOCKSIZE))/2;
        int startYPos = 180 + (80 - Block.BLOCKSIZE) + ((grid.getHeight() < 10) ? (Block.BLOCKSIZE * (11 - grid.getHeight())) : 0);
        for(int row=from; row<=to; row++) {
            for(int column=0; column<grid.getWidth(); column++) {
                Block block = grid.getBlock(column, row);
                if (block == null)
                    continue;
                block.setSize(Block.BLOCKSIZE,Block.BLOCKSIZE);
                block.setLocation(startXPos + (column * Block.BLOCKSIZE), startYPos + ((row-from) * Block.BLOCKSIZE));
                getContentPane().add(block);
            }
        }
    }

    public void paintScore(int score, int scoreGoal) {
        JLabel label = new JLabel("Score : " + score + " / " + scoreGoal);
        label.setLocation(10,0);
        label.setSize(800,50);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        label.setVisible(true);
        getContentPane().add(label);
    }

    public void paintPackets(int packets, int packetGoal) {
        JLabel label = new JLabel("Spirits saved : " + packets + " / " + packetGoal);
        label.setLocation(10,40);
        label.setSize(800,50);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        label.setVisible(true);
        getContentPane().add(label);
    }

    public void paintItems(int redPotions, int bluePotions, int greenPotions)  {
        JLabel label = new JLabel(""+redPotions);
        label.setLocation(460, 100);
        label.setSize(800,50);
        label.setForeground(Color.RED);
        label.setFont(font);
        label.setVisible(true);
        getContentPane().add(label);

        label = new JLabel(""+bluePotions);
        label.setLocation(580, 100);
        label.setSize(800,50);
        label.setForeground(Color.BLUE);
        label.setFont(font);
        label.setVisible(true);
        getContentPane().add(label);

        label = new JLabel(""+greenPotions);
        label.setLocation(700, 100);
        label.setSize(800,50);
        label.setForeground(Color.GREEN);
        label.setFont(font);
        label.setVisible(true);
        getContentPane().add(label);
    }

    public void paintBackground(String id) {
        BackgroundPane bgp = new BackgroundPane(SpriteManager.getSprite(id));
        bgp.setBounds(0, 0, 800, 1000);
        bgp.setVisible(true);
        getContentPane().add(bgp);
    }

    public void paintArrow(int remainingLines) {
        JLabel linesLabel = new JLabel(remainingLines + " lines left", new ImageIcon(SpriteManager.getSprite("spr_arrow")), SwingConstants.LEADING);
        linesLabel.setSize(253,71);
        linesLabel.setPreferredSize(new Dimension(253,71));
        linesLabel.setLocation(100,900);
        linesLabel.setForeground(Color.WHITE);
        linesLabel.setFont(font);
        add(linesLabel);
    }

    public void updateMouseIcon(String icon) {
        if(!mouseIcon.icon.equals(icon)) {
            System.out.println("Update!");
            mouseIcon.icon = icon;
            getContentPane().add(mouseIcon);
            repaintMouseIcon();
        }
    }

    public void repaintMouseIcon()  {
        Point p = getMousePosition();
        if (p != null)
            mouseIcon.setLocation((int)p.getX()-20, (int)p.getY()-70);
        mouseIcon.repaint();
    }

    public static Color rgbToColor(String rgbString) {
        return new Color( Integer.valueOf(rgbString.substring(1, 3), 16),
                          Integer.valueOf(rgbString.substring(3, 5), 16),
                          Integer.valueOf(rgbString.substring(5, 7), 16)
        );
    }


    public class BackgroundPane extends JPanel {
        private BufferedImage image;

        public BackgroundPane(BufferedImage image) {
            super();
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, 800, 1000, this);
        }
    }


    public class MouseIcon extends JPanel {
        private BufferedImage redPotionIcon;
        private BufferedImage bluePotionIcon;
        private BufferedImage greenPotionIcon;
        private String icon;

        public MouseIcon() {
            icon = "";
            setBackground(new Color(0,0,0,0));
            setVisible(true);
            setSize(46, 75);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            switch(icon) {
                case "redpotion":
                    g.drawImage(SpriteManager.getSprite("spr_potion_red"), 0, 0, 46, 75, this);
                    break;
                case "bluepotion":
                    g.drawImage(SpriteManager.getSprite("spr_potion_blue"), 0, 0, 46, 75, this);
                    break;
                case "greenpotion":
                    g.drawImage(SpriteManager.getSprite("spr_potion_green"), 0, 0, 46, 75, this);
                    break;
            }
        }
    }
}
