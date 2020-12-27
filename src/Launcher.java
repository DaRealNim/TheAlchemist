import java.util.Scanner;
import java.io.*;


public class Launcher {

    public static void main(String[] args) {

      File saveFile = new File("SavedGame.dat");
      Inventory inv = new Inventory();
      Progression prog = new Progression();


      try {
        if (saveFile.createNewFile()) {
          FileOutputStream savedFileOutputStream = new FileOutputStream(saveFile);
          ObjectOutputStream savedObjectOutputStream = new ObjectOutputStream(savedFileOutputStream);

          savedObjectOutputStream.writeObject(inv);
          savedObjectOutputStream.writeObject(prog);

          savedObjectOutputStream.close();
        }
        else {
          FileInputStream savedFileInputStream = new FileInputStream(saveFile);
          ObjectInputStream savedObjectInputStream = new ObjectInputStream(savedFileInputStream);

          inv = (Inventory) savedObjectInputStream.readObject();
          prog = (Progression) savedObjectInputStream.readObject();

          savedObjectInputStream.close();
        }
      }
      catch (Exception e) {
        System.out.println("Error loading save file, exiting...");
        System.exit(0);
      }

      Menu mainMenu;

      if (args.length == 0)
        mainMenu = new Menu(true, prog, inv);
      else if (args[0].equals("text"))
        mainMenu = new Menu(false, prog, inv);
      else
        mainMenu = new Menu(true, prog, inv);

      mainMenu.displayMenu();
    }
}
