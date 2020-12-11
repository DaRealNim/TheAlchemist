public abstract class Level {
    private int packetGoal;
    private int scoreGoal;

    public abstract void play();

    public abstract Grid generateGrid(String[] boardStringRepresentationLines);

}
