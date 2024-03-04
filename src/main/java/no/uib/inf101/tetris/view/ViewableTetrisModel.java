package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

/** This interface has all the methods that a tetrismodel should have */
public interface ViewableTetrisModel {
    /**
     * Gives us the size of the tetrisboard,
     * and GridDimension consist of rows and cols
     * 
     * @return a gridDimension object
     */
    GridDimension getDimension();

    /**
     * Gives us all the posisions of the tiles on the board
     * 
     * @return Iterable<GridCell<Character>> which iterates over all the tiles on
     *         the board
     */
    Iterable<GridCell<Character>> getTilesOnBoard();

    /**
     * Gives us all the positions of the tiles in the falling tetromino
     * 
     * @return Iterable<GridCell<Character>> which iterates over all the tiles in
     *         the falling tetromino
     */

    Iterable<GridCell<Character>> fallingTetromino();
}