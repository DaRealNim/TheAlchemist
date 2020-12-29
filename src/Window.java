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
        setSize(800, 1000);
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
    }

    public void paintGrid(Grid grid, int from, int to) {
        // System.out.println("paintGrid!");
        getContentPane().removeAll();
        for(int row=0; row<to+1; row++) {
            for(int column=0; column<grid.getWidth(); column++) {
                Block block = grid.getBlock(column, row+from);
                if (block == null)
                    continue;
                block.setSize(80,80);
                block.setLocation((column*80), 150+(row*80));
                getContentPane().add(block);
            }
        }
    }



}
