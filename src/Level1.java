public class Level1 extends Level {

    public Level1(Window w, Menu m, Progression prog, Inventory inv, Integer id) {
        super(w, m, prog, inv, id);
        packetGoal = 1;
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
        "DACDDDDADA",
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
