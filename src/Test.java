import java.io.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("\n");
        // Grid g = new Grid(new String[]{ "  P    P   P  ",
        //                                 "AABAAACAABACCA",
        //                                 "ACCCADCAAABACC",
        //                                 "ABADDDDDDDA###",
        //                                 "DDDDBAD#######",
        //                                 "DDDACDDDDADAAA",
        //                                 "ABACCA#DDDCAAA",
        //                                 "DABAAC#CDCAA##",
        //                                 "ABCAAD#DABCCAA"}
        // );
        // g.display();
        // System.out.println();
        //
        // g.searchAndDestroyAdjacentBlocks(6, 4, 'D');
        //
        // while(true) {
        //     System.out.println("\n");
        //     g.display();
        //     boolean state = g.applyGravityStep();
        //     System.out.println(state);
        //     try {
        //         System.in.read();
        //     } catch(IOException e) {
        //
        //     }
        //
        // }

        Level1 level = new Level1();
        level.play();

        // Progression p = new Progression();
        // Inventory i = new Inventory();
        //
        // try {
        //     FileOutputStream fileOut = new FileOutputStream("PRS_save.dat");
        //     ObjectOutputStream out = new ObjectOutputStream(fileOut);
        //     out.writeObject(p);
        //     out.writeObject(i);
        //     out.close();
        //     fileOut.close();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }



    }
}
