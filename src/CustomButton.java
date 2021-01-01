import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class CustomButton extends JPanel implements MouseInputListener {
    JButton button;

    public CustomButton(JButton button) {
        this.button = button;
        // setLocation(200, 250);
        setSize(200, 50);
        setBackground(new Color(128, 128, 128));

        JLabel label = new JLabel(button.getText());
        label.setLocation(10,0);
        label.setSize(800,50);
        label.setForeground(Color.WHITE);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 30));
        label.setVisible(true);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(label);
        setVisible(true);
        addMouseListener(this);
    }

    public void mouseClicked(MouseEvent event) {
        System.out.println("clicked!");
        button.doClick();
    }
    public void mouseEntered(MouseEvent event) {

    }
    public void mouseExited(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseDragged(MouseEvent event) {}
    public void mouseMoved(MouseEvent event) {}
}
