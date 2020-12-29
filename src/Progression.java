import java.io.Serializable;
import java.io.*;

public class Progression implements Serializable {
    public boolean[] unlockedLevels = {true, false, false, false};

    public void unlockLevel(int i) {
        try {
            unlockedLevels[i - 1] = true;
        } catch (RuntimeException e) {
            System.out.println("ERROR: LEVEL "+i+" DOESN'T EXIST");
        }
    }

}
