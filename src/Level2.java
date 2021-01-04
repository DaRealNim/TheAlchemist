import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Level2 extends Level {

    public Level2(Window w, Menu m, Progression prog, Inventory inv, Integer id) {
        super(w, m, prog, inv, id);
        packetGoal = 3;
        scoreGoal = 950;
        super.play();
    }

    public Level2(Progression prog, Inventory inv) {
        super(prog, inv);
        packetGoal = 3;
        scoreGoal = 950;
        super.playText();
    }

    public String[] getGridStrings() {
        return new String[]{"P    P   P",
                            "BAAACAABAC",
                            "CCADCAAABA",
                            "ADDDDDDDA#",
                            "DDBAD#####",
                            "DACDDDDADA",
                            "ACCA#DDDCA",
                            "BAAC#CDCAA",
                            "DACDDDDADA",
                            "DACD#DDDAD",
                            "DACD#DDDAD",
                            "DACD#DDADA",
                            "CAADAABCCA",
                            "##########"};
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

    public String getBackGroundPath() {
        return "./res/images/bg_level2.png";
    }

    public Clip getLevelMusic() {
        String musicPath;
        File musicFile;
        AudioInputStream audioInputStream;

        try {
            musicPath = "./res/sounds/music/EgyptianCrawl.wav";
            musicFile = new File(musicPath);
            audioInputStream = AudioSystem.getAudioInputStream(musicFile.toURI().toURL());

            Clip clipMusic = AudioSystem.getClip();
            clipMusic.open(audioInputStream);
            return clipMusic;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
