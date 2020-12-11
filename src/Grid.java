public class Grid {
    private int width;
    private int height;
    private Block[][] blockGrid;

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

        if (boardStringRepresentationLines.length == 0) throw new InvalidGridStringException("Array is empty");
        width = boardStringRepresentationLines[0].length();
        height = boardStringRepresentationLines.length;
        blockGrid = new Block[height][width];
        for (int row=0; row<height; row++) {
            if (boardStringRepresentationLines[row].length() != width) throw new InvalidGridStringException("Line "+row+"'s length does not match the first");
            for (int column=0; column<width; column++) {
                char blockTypeChar = boardStringRepresentationLines[row].charAt(column);
                if (blockTypeChar != ' ') blockGrid[row][column] = new Block(blockTypeChar);
            }
        }
    }

    /**
     * Draws a debug visual representation of the grid in terminal
     */
    public void display() {
        for(Block[] row : blockGrid) {
            for(Block block : row) {
                if (block == null) System.out.print(" "); else System.out.print(block);
            }
            System.out.println();
        }
    }


    /**
     * Returns selected block
     */
    private Block getBlock(int xPos, int yPos) {
        try {
            return blockGrid[yPos][xPos];
        } catch(ArrayIndexOutOfBoundsException e) {
            // System.out.println("Error: selected coordinates aren't in array");
            // e.printStackTrace();
            return null;
        }
    }

    /**
     * Destroys selected block
     */
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
    public void searchAndDestroyAdjacentBlocks(int xPos, int yPos, char type) {
        if (type == ' ' || type == '#' || type == 'P') return; //can't destroy air, walls or packets
        Block currentBlock = getBlock(xPos, yPos);
        if (currentBlock == null) return;
        if (currentBlock.getType() == type) {
            destroyBlock(xPos, yPos);
            searchAndDestroyAdjacentBlocks(xPos-1, yPos, type);
            searchAndDestroyAdjacentBlocks(xPos+1, yPos, type);
            searchAndDestroyAdjacentBlocks(xPos, yPos-1, type);
            searchAndDestroyAdjacentBlocks(xPos, yPos+1, type);
        }
    }

    /**
     * Makes all blocks that have nothing below them fall one case
     * Returns false if some block still need to fall, true if we're done
     */
    public boolean applyGravityStep() {
        boolean gravityCycleDone = true;
        //We go through the grid from bottom to top
        for(int row=height-2; row>=0; row--) {
            for(int column=0; column<width; column++){
                Block block = getBlock(column, row);
                if (block != null) {
                    if (block.getType() != '#') {
                        if (getBlock(column, row+1) == null) {
                            insertBlock(column, row+1, block);
                            destroyBlock(column, row);
                            if (gravityCycleDone) {
                                Block blockBelowBelow = getBlock(column, row+2);
                                if (blockBelowBelow == null) gravityCycleDone = false;
                                else if (blockBelowBelow.getType() == '#') gravityCycleDone = false;

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


    public class InvalidGridStringException extends RuntimeException {
        public InvalidGridStringException(String message) {
            super(message);
        }
    }
}
