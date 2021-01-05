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

    public static Color rgbToColor(String rgbString) {
        return new Color( Integer.valueOf(rgbString.substring(1, 3), 16),
                          Integer.valueOf(rgbString.substring(3, 5), 16),
                          Integer.valueOf(rgbString.substring(5, 7), 16)
        );
    }


    public Window() {
        setTitle("The Alchemist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        setContentPane(new JPanel(null));
        getContentPane().setBackground(rgbToColor("#120458"));
        getContentPane().setPreferredSize(new Dimension(800, 1000));
        pack();

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

        mouseIcon = new MouseIcon();
        // addMouseMotionListener(new MouseMotionAdapter() {
        //     public void mouseMoved(MouseEvent e) {
        //         mouseIcon.setLocation(e.getX(), e.getY());
        //     }
        // });
        getContentPane().add(mouseIcon);

    }

    public void paintGrid(Grid grid, int from, int to) {
        // System.out.println("paintGrid!");
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

    public void paintBackground(String path) {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(path));
        } catch (IOException ex) { System.out.println("INVALID PATH");}
        BackgroundPane bgp = new BackgroundPane(myPicture);
        bgp.setBounds(0, 0, 800, 1000);
        bgp.setVisible(true);
        getContentPane().add(bgp);
    }

    public void paintArrow(int remainingLines) {
        BufferedImage arrow = null;
        try {
            arrow = ImageIO.read(new File("./res/images/arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel linesLabel = new JLabel(remainingLines + " lines left", new ImageIcon(arrow), SwingConstants.LEADING);
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
            try {
                redPotionIcon = ImageIO.read(new File("./res/images/potion_red.png"));
            } catch (IOException ex) { System.out.println("INVALID PATH");}
            try {
                bluePotionIcon = ImageIO.read(new File("./res/images/potion_blue.png"));
            } catch (IOException ex) { System.out.println("INVALID PATH");}
            try {
                greenPotionIcon = ImageIO.read(new File("./res/images/potion_green.png"));
            } catch (IOException ex) { System.out.println("INVALID PATH");}
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // System.out.println("test");
            switch(icon) {
                case "redpotion":
                    g.drawImage(redPotionIcon, 0, 0, 46, 75, this);
                    break;
                case "bluepotion":
                    g.drawImage(bluePotionIcon, 0, 0, 46, 75, this);
                    break;
                case "greenpotion":
                    g.drawImage(greenPotionIcon, 0, 0, 46, 75, this);
                    break;
            }
        }
    }



}
