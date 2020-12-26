public class Level1 extends Level {

    public Level1() {
        super();
        packetGoal = 3;
        scoreGoal = 1500;
    }

    public String[] getGridStrings() {
        return new String[]{"  P    P   P  ",
                            "AABAAACAABACCA",
                            "ACCCADCAAABACC",
                            "ABADDDDDDDA###",
                            "DDDDBAD#######",
                            "DDDACDDDDADAAA",
                            "ABACCA#DDDCAAA",
                            "DABAAC#CDCAA##",
                            "DDDACDDDDADAAA",
                            "DDDACD#DDDADAA",
                            "DDDACD#DDDADAA",
                            "DDDACD#DDADAAA",
                            "ABCAADAABCCAAD",
                            "##############"};
    }

    public void changeLevel() {
        //Goto level 2
    }

    public void unlockLevel() {
        //Unlock level 2
    }

}
