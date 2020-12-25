import java.util.Scanner;
import java.io.*;


public class Launcher {

    public static void main(String[] args) {

      File saveFile = new File("SavedGame.dat");
      Inventory inv;
      Progression prog;

      try {
        if (saveFile.createNewFile()) {
          FileOutputStream savedFileOutputStream = new FileOutputStream(saveFile);
          ObjectOutputStream savedObjectOutputStream = new ObjectOutputStream(savedFileOutputStream);

          inv = new Inventory();
          prog = new Progression();

          savedObjectOutputStream.writeObject(inv);
          savedObjectOutputStream.writeObject(prog);

          savedObjectOutputStream.close();

          mainMenu.displayMenu();
        }
        else {
          FileInputStream savedFileInputStream = new FileInputStream(saveFile);
          ObjectInputStream savedObjectInputStream = new ObjectInputStream(savedFileInputStream);

          inv = (Inventory) savedObjectInputStream.readObject();
          prog = (Progression) savedObjectInputStream.readObject();

          savedObjectInputStream.close();

          mainMenu.displayMenu();
        }
      }
      catch (Exception e) {
        System.out.println("Error loading save file, exiting...");
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
