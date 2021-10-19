import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class Block extends JPanel implements MouseInputListener {
    private final char type;
    private Grid parentGrid;
    private BufferedImage image;

    public Block(char type, Grid parentGrid) {
        this.type = type;
        this.parentGrid = parentGrid;
        if(Launcher.GUI) {
            switch(type) {
                case 'A':
                    image = SpriteManager.getSprite("spr_forest");
                    break;
                case 'B':
                    image = SpriteManager.getSprite("spr_fire");
                    break;
                case 'C':
                    image = SpriteManager.getSprite("spr_air");
                    break;
                case 'D':
                    image = SpriteManager.getSprite("spr_water");
                    break;
                case 'P':
                    image = SpriteManager.getSprite("spr_spirit");
                    break;
                case '#':
                    image = SpriteManager.getSprite("spr_demon");
                    break;
            }
            setOpaque(false);
            addMouseListener(this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, Window.BLOCKSIZE, Window.BLOCKSIZE, this);
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
        System.out.println("[Event] Block clicked!");
        parentGrid.setBlockClicked(this);

    }
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseDragged(MouseEvent event) {}
    public void mouseMoved(MouseEvent event) {}
}
