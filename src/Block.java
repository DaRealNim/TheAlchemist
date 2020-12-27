import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;


public class Block extends JPanel implements MouseInputListener {
    private final char type;
    private Color color;
    private Grid parentGrid;

    public Block(char type, Grid parentGrid) {
        this.type = type;
        this.parentGrid = parentGrid;
        switch(type) {
            case 'A':
                color = Window.rgbToColor("#fe75fe");
                break;
            case 'B':
                color = Window.rgbToColor("#860029");
                break;
            case 'C':
                color = Window.rgbToColor("#de004e");
                break;
            case 'D':
                color = Window.rgbToColor("#0b468c");
                break;
            case 'P':
                color = Window.rgbToColor("#ff9760");
                break;
            case '#':
                color = Window.rgbToColor("#464646");
                break;
        }
        setBackground(color);
        addMouseListener(this);
    }

    public boolean isSameType(Block block) {
        return (type == block.getType());
    }

    public boolean isWall() {
        return (type == '#');
    }

    @Override
    public String toString() {
        return Character.toString(type);
    }

	public char getType() {
		return type;
	}

    public void mouseClicked(MouseEvent event) {
        System.out.println("Block clicked!");
        parentGrid.setBlockClicked(this);

    }
    public void mouseEntered(MouseEvent event) {

    }
    public void mouseExited(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseDragged(MouseEvent event) {}
    public void mouseMoved(MouseEvent event) {}
}
