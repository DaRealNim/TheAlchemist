public class Level1 extends Level {

    public Level1(Window w, Menu m, Progression prog, Inventory inv, Integer id) {
        super(w, m, prog, inv, id);
        packetGoal = 3;
        scoreGoal = 950;
        super.play();
    }

    public Level1(Progression prog, Inventory inv) {
        super(prog, inv);
        packetGoal = 3;
        scoreGoal = 950;
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

    public String getBackGroundPath() {
        return "./res/images/bg_level1.png";
    }

}
