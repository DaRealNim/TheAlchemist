import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class CustomPopup extends JPanel {

    public CustomPopup(String title, JButton changeOrRetryButton, JButton menuButton) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setLocation(200, 250);
        setSize(400, 500);
        setBackground(new Color(128, 128, 128));

        JLabel label = new JLabel(title);
        label.setLocation(10,0);
        label.setSize(800,50);
        label.setForeground(Color.WHITE);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 40));
        label.setVisible(true);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        changeOrRetryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(label);
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(changeOrRetryButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(menuButton);

        setVisible(true);

    }
}
