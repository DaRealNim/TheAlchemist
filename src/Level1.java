public class Level1 extends Level {

    public Level1(Window w, Progression prog, Inventory inv) {
        super(w, prog, inv);
        packetGoal = 4;
        scoreGoal = 100;
        super.play();
    }

    public Level1(Progression prog, Inventory inv) {
        super(prog, inv);
        packetGoal = 4;
        scoreGoal = 100;
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
        prog.unlockLevel(2);
    }

}
