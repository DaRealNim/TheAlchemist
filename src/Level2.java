import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Level2 extends Level {

    public Level2(Window w, Menu m, Progression prog, Inventory inv, Integer id) {
        super(w, m, prog, inv, id);
        packetGoal = 3;
        scoreGoal = 450;
        super.play();
    }

    public Level2(Progression prog, Inventory inv) {
        super(prog, inv);
        packetGoal = 3;
        scoreGoal = 450;
        super.playText();
    }

    public String[] getGridStrings() {
        return new String[] {
                                "  P P P",
                                "  ABCBC",
                                "  ABCBC",
                                "  BCABA",
                                "BCBCABA",
                                "CBCACAC",
                                "CACACAC",
                                "AABCB##",
                                "AABCB##",
                                "#######"
                            };
        }

    public void changeLevel() {
        Thread thread = new Thread() {
            public void run() {
                    menu.instanciateLevel(3);
                }
        };
        thread.start();
    }

    public void unlockLevel() {
        prog.unlockLevel(3);
    }

    public String getBackGroundIdentifier() {
        return "bg_level2";
    }

    public String getMusicIdentifier() {
        return "level2music";
    }

}
