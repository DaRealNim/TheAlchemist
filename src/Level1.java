public class Level1 extends Level {

    public Level1() {
        super();
        packetGoal = 2;
        scoreGoal = 1000;
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
                            "ABCAAD#DABCCAA"};
    }

    public void changeLevel() {
        //Goto level 2
    }

    public void unlockLevel() {
        //Unlock level 2
    }

}
