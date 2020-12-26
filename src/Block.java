public class Block {
    private final char type;

    public Block(char type) {
        this.type = type;
    }

    public boolean isSameType(Block block) {
        return (type == block.getType());
    }

    public boolean isWall() {
        return (type == '#');
    }


    @Override
    public String toString() {
        return Character.toString(type);
    }

	/**
	* Returns value of type
	* @return
	*/
	public char getType() {
		return type;
	}
}
