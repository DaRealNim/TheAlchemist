public class Grid {
    private int width;
    private int height;
    private Block[][] blockGrid;

    public Grid(String[] boardStringRepresentationLines) throws InvalidGridStringException {
        /*  Représentation visuelle de la grille
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

    public void display() {
        // int columnIndicator = 0;
        // int rowIndicator = 0;
        for(Block[] row : blockGrid) {
            for(Block block : row) {
                if (block == null) System.out.print(" "); else System.out.print(block);
            }
            System.out.println();
        }
    }

    private Block getBlock(int xPos, int yPos) {
        try {
            return blockGrid[yPos][xPos];
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: selected coordinates aren't in array");
            e.printStackTrace();
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


    public class InvalidGridStringException extends RuntimeException {
        public InvalidGridStringException(String message) {
            super(message);
        }
    }
}
