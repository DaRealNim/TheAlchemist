public abstract class Level {
    protected int packetGoal;
    protected int scoreGoal;
    private Grid grid;
    protected Controller controller;
    protected Display display;

    public Level() {
        grid = new Grid(getGridStrings());
        controller = new TextBasedController();
    }

    public abstract String[] getGridStrings();

}
