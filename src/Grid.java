import java.util.Arrays;

public class Grid {
    private int width;
    private int height;
    private Block[][] blockGrid;
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Grid(String[] boardStringRepresentationLines) throws InvalidGridStringException {
        /*  Visual representation of the grid
            blockGrid[height][width] =
            [
                    <--width--->
                [Block, ... , Block],
                        .
                        .          ^
                        .          | height
                        .          v
                        .
                [Block, ... , Block],

            ]
        */

        if (boardStringRepresentationLines.length == 0)
            throw new InvalidGridStringException("Array is empty");

        width = boardStringRepresentationLines[0].length();
        height = boardStringRepresentationLines.length;
        blockGrid = new Block[height][width];

        for (int row = 0; row < height; row++) {
            if (boardStringRepresentationLines[row].length() != width)
                throw new InvalidGridStringException("Line "+row+"'s length does not match the first");
            for (int column = 0; column < width; column++) {
                char blockTypeChar = boardStringRepresentationLines[row].charAt(column);
                if (blockTypeChar != ' ')
                  blockGrid[row][column] = new Block(blockTypeChar);
            }
        }
    }



    public void display(int from, int to) {
        System.out.print("  ");
        for(int i = 0; i < blockGrid[0].length; i++) {
            System.out.print((i < 10) ? i : Character.toString(ALPHABET.charAt(i - 10)));
        }
        int counter = from;
        System.out.println();
        for(Block[] row : Arrays.copyOfRange(blockGrid, from, to+1)) {
            System.out.print((counter < 10) ? counter : Character.toString(ALPHABET.charAt(counter - 10)));
            System.out.print(" ");
            for(Block block : row) {
                if (block == null)
                    System.out.print(" ");
                else
                    System.out.print(block);
            }
            System.out.println();
            counter++;
        }
    }

    public void display() {
        display(0, height);
    }


    public Block getBlock(int xPos, int yPos) {
        try {
            return blockGrid[yPos][xPos];
        } catch(ArrayIndexOutOfBoundsException e) {
            // System.out.println("Error: selected coordinates aren't in array");
            // e.printStackTrace();
            return null;
        }
    }

    private void destroyBlock(int xPos, int yPos) {
        try {
            blockGrid[yPos][xPos] = null;
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: selected coordinates aren't in array");
            e.printStackTrace();
        }
    }

    private void insertBlock(int xPos, int yPos, Block block) {
        try {
            blockGrid[yPos][xPos] = block;
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: selected coordinates aren't in array");
            e.printStackTrace();
        }
    }

    /**
     * Destroys selected block if it matches `type`, and recursively does the same
     * for adjacent blocks
     */
    public int searchAndDestroyAdjacentBlocks(int xPos, int yPos, char type) {
        if (type == ' ' || type == '#' || type == 'P')
            return 0; //can't destroy air, walls or packets
        Block currentBlock = getBlock(xPos, yPos);
        if (currentBlock == null)
            return 0;
        int score = 0;
        if (currentBlock.getType() == type) {
            destroyBlock(xPos, yPos);
            score += 10;
            score += searchAndDestroyAdjacentBlocks(xPos - 1, yPos, type);
            score += searchAndDestroyAdjacentBlocks(xPos + 1, yPos, type);
            score += searchAndDestroyAdjacentBlocks(xPos, yPos - 1, type);
            score += searchAndDestroyAdjacentBlocks(xPos, yPos + 1, type);
        }
        return score;
    }

    private void shiftUpFromBlock(int column, int row) {
        //move all blocks one step to left until there's no more or we hit a wall above
        //if there's a wall blocking the shift at some point, we quit
        for(int row2 = row - 1; row2 >= 0; row2--) {
            Block block = getBlock(column, row2);
            if(block == null) {
                break;
            } else if(block.isWall()) {
                break;
            } else {
                if(getBlock(column - 1, row2) != null)
                    return;
                insertBlock(column - 1, row2, block);
                destroyBlock(column, row2);
            }
        }

    }

    /**
     * Shift columns that need to be shifted to the left:
     * If there is a wall with a block on top and empty space or wall on the lower left, shift everything
     * on top of it until the next wall or when we reach another empty space or the top of the grid
     */
    public boolean shiftToLeft() {
        boolean done = true;
        for(int row = height - 1; row >= 0; row--) {
            for(int column = 0; column < width; column++){
                Block block = getBlock(column, row);
                if (block != null) {
                    if (block.isWall()) {
                        int offset = 0;
                        // if(row == height - 1)
                        //     offset = 1
                        if (getBlock(column, row - 1) != null) {
                            if (getBlock(column, row - 1).getType() != '#') {
                                if (getBlock(column - 1, row) == null) {
                                    if (column != 0) {
                                        //SHIFT!
                                        shiftUpFromBlock(column, row);
                                        done = false;
                                    }
                                } else if (getBlock(column - 1, row).isWall()) {
                                    if(getBlock(column - 1, row - 1) == null) {
                                        //SHIFT!
                                        shiftUpFromBlock(column, row);
                                        done = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return done;
    }

    /**
     * Makes all blocks that have nothing below them fall one step
     * Returns false if some block still need to fall, true if we're done
     */
    public boolean applyGravityStep() {
        boolean gravityCycleDone = true;
        //We go through the grid from bottom to top
        for(int row = height - 2; row >= 0; row--) {
            for(int column = 0; column < width; column++) {
                Block block = getBlock(column, row);
                if (block != null) {
                    if (block.getType() != '#') {
                        if (getBlock(column, row + 1) == null) {
                            insertBlock(column, row + 1, block);
                            destroyBlock(column, row);
                            if (gravityCycleDone) {
                                Block blockBelowBelow = getBlock(column, row + 2);
                                if (blockBelowBelow == null)
                                    gravityCycleDone = false;
                                else if (blockBelowBelow.isWall())
                                    gravityCycleDone = false;

                            }
                        }
                    }
                }
            }
        }
        return gravityCycleDone;
    }

    /**
     * Applies gravity until all blocks are in place
     */
    public void applyGravity() {
        while (!applyGravityStep());
    }

    /**
     * Remove packets on last line and returns the number of removed packets
     */
    public int removePacketsOnLastLine() {
        int counter = 0;
        for (int column = 0; column < width; column++) {
            Block currentBlock = getBlock(column, height-2);
            System.out.println(column + ", " + (height-2));
            if (currentBlock != null) {
                if (currentBlock.getType() == 'P') {
                    System.out.println("PACKET");
                    counter++;
                    destroyBlock(column, height-2);
                }
            }
        }
        return counter;
    }

    public boolean lineHasEmptyBlocs(int lineNumber) {
        for(Block block : blockGrid[lineNumber]) {
            if(block == null) return true;
        }
        return false;
    }

    /**
     * Checks if current line is fully devoid of blocs that aren't walls
     */
    public boolean isLineFullyEmpty(int lineNumber) {
        for(Block block : blockGrid[lineNumber]) {
            if(block != null) {
                if(block.getType() != '#')
                    return false;
            }
        }
        return true;
    }

    public int destroyColumn(int column, int from, int to) {
        int score = 0;
        for(int i=from; i<=to; i++) {
            Block block = getBlock(column, i);

            if (block != null) {
                if (block.getType() != 'P' && block.getType() != '#') {
                    destroyBlock(column, i);
                    score += 10;
                }
            }
        }
        return score;
    }

    public String getBoardString() {
        String ret = "";
        for(Block[] row : blockGrid) {
            for(Block block : row) {
                if (block == null)
                    ret += " ";
                else
                    ret += block;
            }
        }
        return ret;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }



    public class InvalidGridStringException extends RuntimeException {
        public InvalidGridStringException(String message) {
            super(message);
        }
    }
}
