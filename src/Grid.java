public class Grid {
    private int width;
    private int height;
    private Bloc[][] blocGrid;

    public Grid(String[] boardStringRepresentationLines) throws InvalidGridStringException {
        /*  Représentation visuelle de la grille
            blocGrid[height][width] =
            [
                    <--width--->
                [Bloc, ... , Bloc],
                        .
                        .          ^
                        .          | height
                        .          v
                        .
                [Bloc, ... , Bloc],

            ]
        */
        if (boardStringRepresentationLines.length == 0) throw new InvalidGridStringException("Array is empty");
        width = boardStringRepresentationLines[0].length();
        height = boardStringRepresentationLines.length;
        System.out.println(width + ", "+height);
        blocGrid = new Bloc[height][width];
        for (int row=0; row<height; row++) {
            // System.out.println("row="+row);
            if (boardStringRepresentationLines[row].length() != width) throw new InvalidGridStringException("Line "+row+"'s length does not match the first");
            for (int column=0; column<width; column++) {
                // System.out.println("column="+column);
                blocGrid[row][column] = new Bloc(boardStringRepresentationLines[row].charAt(column));
                System.out.print(boardStringRepresentationLines[row].charAt(column));
            }
            System.out.println();
        }
    }


    public class InvalidGridStringException extends Exception {
        public InvalidGridStringException(String message) {
            super(message);
        }
    }
}
