package no.uib.inf101.tetris.view;

import java.util.ArrayList;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

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

    /**
     * Gives us a tetromino for where the current tetromino will land
     * 
     * @return the future tetromino
     */
    Iterable<GridCell<Character>> getShadowPosition();

    /**
     * Gives us the tetromino on hold
     * 
     * @return the tetroino on hold, or null if there is none
     */
    Tetromino getHoldingTetromino();

    /**
     * Gives us the current gamestate
     * 
     * @return the current gamestate
     */

    GameState getGameState();

    /**
     * Gives us the number of lines removed
     * 
     * @return number of lines
     */
    String getLines();

    /**
     * Gives us the current score
     * 
     * @return score
     */
    Integer getScore();

    /**
     * Gives us the current level
     * 
     * @return level
     */
    Integer getLevel();

    /**
     * Gives us the number of points for the number of lines removed
     * 
     * @return points to be added to the score
     */
    Integer getPoints(int numberOfLines);

    /**
     * Gives us the three next tetrominos
     * 
     * @return the three next tetrominos in an arraylist
     */
    ArrayList<Tetromino> getNextTetrominos();

}