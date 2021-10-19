import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Level3 extends Level {

    public Level3(Window w, Menu m, Progression prog, Inventory inv, Integer id) {
        super(w, m, prog, inv, id);
        packetGoal = 3;
        scoreGoal = 1150;
        super.play();
    }

    public Level3(Progression prog, Inventory inv) {
        super(prog, inv);
        packetGoal = 3;
        scoreGoal = 1150;
        super.playText();
    }

    public String[] getGridStrings() {
        return new String[] {
                                " #  A C   ",
                                "## PAPCPDD",
                                "AAAAABCAAB",
                                "AAACCCBAAA",
                                "ABBBAACDDD",
                                "DDAABCCACD",
                                "CABBBDDAAA",
                                "BAACAADAAB",
                                "ABCCCDDDAB",
                                "CBAAAACCAA",
                                "DDAAABAAAA",
                                "ABBAACACA#",
                                "BACAAAAC##",
                                "DABCADD###",
                                "DABCAD####",
                                "##########"

                            };
        }

    public void changeLevel() {
        Thread thread = new Thread() {
            public void run() {
                    menu.instanciateLevel(4);
                }
        };
        thread.start();
    }

    public void unlockLevel() {
        prog.unlockLevel(4);
    }

    public String getBackGroundIdentifier() {
        return "bg_level3";
    }

    public String getMusicIdentifier() {
        return "level3music";
    }

}
