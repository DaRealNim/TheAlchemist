public class Level1 extends Level {

    public Level1(Window w, Menu m, Progression prog, Inventory inv, Integer id) {
        super(w, m, prog, inv, id);
        packetGoal = 2;
        scoreGoal = 180;
        super.play();
    }

    public Level1(Progression prog, Inventory inv) {
        super(prog, inv);
        packetGoal = 2;
        scoreGoal = 200;
        super.playText();
    }

    public String[] getGridStrings() {
        return new String[]{"P  P  ",
                            "BAAACA",
                            "CCADCA",
                            "CBADAA",
                            "######"};
        }

    public void changeLevel() {
        Thread thread = new Thread() {
            public void run() {
                    menu.instanciateLevel(2);
                }
        };
        thread.start();
    }

    public void unlockLevel() {
        prog.unlockLevel(2);
    }

    public String getBackGroundPath() {
        return "./res/images/bg_level1.png";
    }

}
