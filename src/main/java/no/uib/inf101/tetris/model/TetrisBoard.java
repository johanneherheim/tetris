package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

/** Objects in this class represents a tetris board. */
public class TetrisBoard extends Grid<Character> {

    /**
     * Creates a new TetrisBoard with the given number of rows and columns.
     * 
     * @param rows number of rows
     * @param cols number of columns
     */

    public TetrisBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

    /**
     * Method for displaying the values of the tetris board as a string.
     * 
     * @return a string representation of the board
     */

    public String prettyString() {
        String board = "";
        Integer count = 1;
        for (GridCell<Character> cell : this) {
            board += cell.value();
            if (count % this.cols() == 0) {
                board += "\n";
            }
            count += 1;
        }
        return board.substring(0, board.length() - 1);
    }
}
