import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class Block extends JPanel implements MouseInputListener {
    private final char type;
    private Color color;
    private Grid parentGrid;
    private BufferedImage image;

    public Block(char type, Grid parentGrid) {
        this.type = type;
        this.parentGrid = parentGrid;
        switch(type) {
            case 'A':
                color = Window.rgbToColor("#fe75fe");
                try {
                    image = ImageIO.read(new File("./res/images/block_forest_jelly.png"));
              } catch (IOException ex) { System.out.println("INVALID PATH");}
                break;
            case 'B':
                color = Window.rgbToColor("#860029");
                try {
                    image = ImageIO.read(new File("./res/images/block_fire_jelly.png"));
                } catch (IOException ex) {}
                break;
            case 'C':
                color = Window.rgbToColor("#de004e");
                try {
                    image = ImageIO.read(new File("./res/images/block_air_jelly.png"));
                } catch (IOException ex) {}
                break;
            case 'D':
                color = Window.rgbToColor("#0b468c");
                try {
                    image = ImageIO.read(new File("./res/images/block_water_jelly.png"));
                } catch (IOException ex) {}
                break;
            case 'P':
                color = Window.rgbToColor("#ff9760");
                try {
                    image = ImageIO.read(new File("./res/images/block_spirit_jelly.png"));
                } catch (IOException ex) {}
                break;
            case '#':
                color = Window.rgbToColor("#464646");
                try {
                    image = ImageIO.read(new File("./res/images/block_demon_jelly.png"));
                } catch (IOException ex) {}
                break;
        }
        // setBackground(color);
        setOpaque(false);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 80, 80, this); // see javadoc for more info on the parameters
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
