import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {

    public static Color rgbToColor(String rgbString) {
        return new Color( Integer.valueOf(rgbString.substring(1, 3), 16),
                          Integer.valueOf(rgbString.substring(3, 5), 16),
                          Integer.valueOf(rgbString.substring(5, 7), 16)
        );
    }


    public Window() {
        setTitle("BORIS HAXX0R'S REVENGE 2077");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        setContentPane(new JPanel(null) //{
        //     protected void paintComponent(Graphics g) {
        //         super.paintComponent(g);
        //         int n = 9;
        //         g.setColor(rgbToColor("#7a04eb"));
        //         // for(int i=150; i<=950; i+=80) {
        //         //     g.drawLine(0, i, 800, i);
        //         // }
        //         // for(int i=0; i<=800; i+=80) {
        //         //     g.drawLine(i, 150, i, 950);
        //         // }
        //     };
        // }

        );
        getContentPane().setBackground(rgbToColor("#120458"));
        getContentPane().setPreferredSize(new Dimension(800, 1000));
        pack();

    }

    public void paintGrid(Grid grid, int from, int to) {
        // System.out.println("paintGrid!");
        getContentPane().removeAll();
        int blockSize = 80;
        int startXPos = (blockSize / 2) * (10 - grid.getWidth());
        int startYPos = 200 + ((grid.getHeight() < 10) ? (blockSize * (11 - grid.getHeight())) : 0);
        for(int row=0; row<to+1; row++) {
            for(int column=0; column<grid.getWidth(); column++) {
                Block block = grid.getBlock(column, row+from);
                if (block == null)
                    continue;
                block.setSize(blockSize,blockSize);
                block.setLocation(startXPos + (column * blockSize), startYPos + (row * blockSize));
                getContentPane().add(block);
            }
        }
    }

    public void paintScore(int score, int scoreGoal) {
        JLabel label = new JLabel("Score : " + score + " / " + scoreGoal);
        label.setLocation(10,0);
        label.setSize(800,50);
        label.setForeground(Color.WHITE);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 30));
        label.setVisible(true);
        getContentPane().add(label);
    }

    public void paintPackets(int packets, int packetGoal) {
        JLabel label = new JLabel("Delivered packets : " + packets + " / " + packetGoal);
        label.setLocation(10,40);
        label.setSize(800,50);
        label.setForeground(Color.WHITE);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 30));
        label.setVisible(true);
        getContentPane().add(label);
    }



}
