public class Test {
    public static void main(String[] args) {
        try {
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
        } catch (Grid.InvalidGridStringException e) {
            e.printStackTrace();
        }

    }
}
