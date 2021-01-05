import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Level4 extends Level {

    public Level4(Window w, Menu m, Progression prog, Inventory inv, Integer id) {
        super(w, m, prog, inv, id);
        packetGoal = 4;
        scoreGoal = 1400;
        super.play();
    }

    public Level4(Progression prog, Inventory inv) {
        super(prog, inv);
        packetGoal = 4;
        scoreGoal = 1400;
        super.playText();
    }

    public String[] getGridStrings() {
        return new String[] {
                                "B  CAAC  B",
                                "AAB####AAB",
                                "ABA#PP#BAB",
                                "ABAAPP#AAB",
                                "CCC####CCC",
                                "CCCDDDDDCC",
                                "CCDDDDDDDC",
                                "AAABAAABAC",
                                "CBAA###BAC",
                                "BAACABAACD",
                                "ABCCABAADD",
                                "C##D##D##C",
                                "CCDDDDDDDC",
                                "AAABAAABAC",
                                "CBAA###BAC",
                                "DABAADABDD",
                                "CCCCCCCCCC",
                                "CCCCCCCCCC",
                                "##########"

                            };
        }

    public void changeLevel() {
        Thread thread = new Thread() {
            public void run() {
                    menu.instanciateLevel(5);
                }
        };
        thread.start();
    }

    public void unlockLevel() {
        prog.unlockLevel(5);
    }

    public String getBackGroundIdentifier() {
        return "bg_level4";
    }

    public String getMusicIdentifier() {
        return "level4music";
    }

}
