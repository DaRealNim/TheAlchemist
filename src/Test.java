public class Test {
    public static void main(String[] args) {
        System.out.println("\n");
        Grid g = new Grid(new String[]{ "  P    P   P  ",
                                        "AABAAACAABACCA",
                                        "ACCCADCAAABACC",
                                        "ABADDDDABAA###",
                                        "ABAABAA#######",
                                        "DABACDADBADAAA",
                                        "ABACCA#ABACAAA",
                                        "DABAAC#ABCAA##",
                                        "ABCAAD#DABCCAA"}
        );
        g.display();
        System.out.println();

        g.searchAndDestroyAdjacentBlocks(15, 1, 'A');
        g.display();
    }
}
