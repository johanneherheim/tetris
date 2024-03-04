package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

    /**
     * Moves the tetromino
     * 
     * @param deltaRow the change in row
     * @param deltaCol the change in column
     * @return true if the tetromino was moved, false if not
     */
    boolean moveTetromino(int deltaRow, int deltaCol);

    /**
     * Rotates the tetromino clockwise
     * 
     * @return true if the tetromino was rotated, false if not
     */
    boolean rotateTetromino();

    /**
     * Drops the tetromino to the bottom of the board
     */
    void dropTetromino();

    /**
     * Gives us the current gamestate
     * 
     * @return the current gamestate
     */

    GameState getGameState();
}
