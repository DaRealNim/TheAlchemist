import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class CustomButton extends JPanel implements MouseInputListener {
    JButton button;
    JLabel label;
    Runnable action;

    public CustomButton(String text, int sizeX, int sizeY, Runnable action) {
        this.button = button;
        this.action = action;
        // setLocation(200, 250);
        // setSize(200, 50);
        setSize(sizeX, sizeY);
        setBackground(new Color(128, 128, 128));

        JLabel label = new JLabel(text);
        label.setLocation(10,0);
        label.setSize(800,50);
        label.setForeground(Color.WHITE);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 30));
        label.setVisible(true);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.label = label;

        add(label);
        setVisible(true);
        addMouseListener(this);
    }

    public void setFontSize(int n) {
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, n));
    }

    public void mouseClicked(MouseEvent event) {
        action.run();
    }
    public void mouseEntered(MouseEvent event) {

    }
    public void mouseExited(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseDragged(MouseEvent event) {}
    public void mouseMoved(MouseEvent event) {}
}
