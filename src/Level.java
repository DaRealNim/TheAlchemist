public abstract class Level {
    protected int packetGoal;
    protected int scoreGoal;
    private Grid grid;

    public Level() {
        grid = new Grid(getGridStrings());
    }

    public abstract String[] getGridStrings();

}
