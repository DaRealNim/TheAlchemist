import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Level1 extends Level {

    Clip level1Music;

    public Level1(Window w, Menu m, Progression prog, Inventory inv, Integer id) {
        super(w, m, prog, inv, id);
        packetGoal = 2;
        scoreGoal = 180;

        String musicPath;
        File musicFile;
        AudioInputStream audioInputStream;

        try {
            musicPath = "./res/sounds/music/DestinationUnknown.wav";
            musicFile = new File(musicPath);
            audioInputStream = AudioSystem.getAudioInputStream(musicFile.toURI().toURL());

            System.out.println(level1Music);
            level1Music = AudioSystem.getClip();
            level1Music.open(audioInputStream);
            level1Music.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        super.play();
    }

    public Level1(Progression prog, Inventory inv) {
        super(prog, inv);
        packetGoal = 2;
        scoreGoal = 200;
        super.playText();
    }

    public String[] getGridStrings() {
        return new String[]{"P  P  ",
                            "BAAACA",
                            "CCADCA",
                            "CBADAA",
                            "######"};
        }

    public void changeLevel() {
        Thread thread = new Thread() {
            public void run() {
                    menu.instanciateLevel(2);
                }
        };
        thread.start();
    }

    public void unlockLevel() {
        prog.unlockLevel(2);
        level1Music.close();
    }

    public String getBackGroundPath() {
        return "./res/images/bg_level1.png";
    }

}
