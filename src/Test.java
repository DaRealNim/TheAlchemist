import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        System.out.println("\n");
        Grid g = new Grid(new String[]{ "  P    P   P  ",
                                        "AABAAACAABACCA",
                                        "ACCCADCAAABACC",
                                        "ABADDDDDDDA###",
                                        "DDDDBAD#######",
                                        "DDDACDDDDADAAA",
                                        "ABACCA#DDDCAAA",
                                        "DABAAC#CDCAA##",
                                        "ABCAAD#DABCCAA"}
        );
        g.display();
        System.out.println();

        g.searchAndDestroyAdjacentBlocks(6, 4, 'D');

        while(true) {
            System.out.println("\n");
            g.display();
            boolean state = g.applyGravityStep();
            System.out.println(state);
            try {
                System.in.read();
            } catch(IOException e) {

            }

        }
    }
}
