public class Level1 extends Level {

    public Level1(Window w) {
        super(w);
        packetGoal = 3;
        scoreGoal = 1500;
        super.play();
    }

    public Level1() {
      super();
      packetGoal = 3;
      scoreGoal = 1500;
      super.playText();
    }

    public String[] getGridStrings() {
        return new String[]{"P    P   P",
                            "BAAACAABAC",
                            "CCADCAAABA",
                            "ADDDDDDDA#",
                            "DDBAD#####",
                            "DACDDDDADA",
                            "ACCA#DDDCA",
                            "BAAC#CDCAA",
                            "DACDDDDADA",
                            "DACD#DDDAD",
                            "DACD#DDDAD",
                            "DACD#DDADA",
                            "CAADAABCCA",
                            "##########"};
    }

    public void changeLevel() {
        //Goto level 2
    }

    public void unlockLevel() {
        //Unlock level 2
    }

}
