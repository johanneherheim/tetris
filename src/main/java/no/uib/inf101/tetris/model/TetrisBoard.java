package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.Grid;

/**
 * Objects in this class represents a tetris board.
 */

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

}
