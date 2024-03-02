package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

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
}
