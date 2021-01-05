import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;


public class AudioManager {
    private static HashMap<String, Clip> sounds = new HashMap<String, Clip>();

    public static boolean registerSound(String identifier, String path) {
        if (sounds.containsKey(identifier))
            return false;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).toURI().toURL());
            Clip sound = AudioSystem.getClip();
            sound.open(audioInputStream);
            sounds.put(identifier, sound);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean playSound(String identifier, boolean loop) {
        if (!sounds.containsKey(identifier))
            return false;
        Clip sound = sounds.get(identifier);
        AudioListener listener = new AudioListener(sound);
        if (loop) {
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            sound.loop(0);
            sound.addLineListener(listener);
        }
        sound.start();
        return true;
    }

    public static boolean stopSound(String identifier) {
        if (!sounds.containsKey(identifier))
            return false;
        Clip sound = sounds.get(identifier);
        sound.stop();
        sound.setFramePosition(0);
        return true;
    }



    public static class AudioListener implements LineListener {
        Clip sound;

        public AudioListener(Clip sound) {
            this.sound = sound;
        }

        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
                sound.stop();
                sound.setFramePosition(0);
                sound.removeLineListener(this);
            }
        }
    }
}
