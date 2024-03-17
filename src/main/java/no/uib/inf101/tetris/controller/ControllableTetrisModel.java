package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

/**
 * The ControllableTetrisModel interface is used to control the tetris game.
 */
public interface ControllableTetrisModel {

    /**
     * Moves the tetromino in the given direction
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
     * Holds the current tetromino on the board, and if there already is a tetromino
     * on hold, it swaps the two
     */
    void holdTetromino();

    /**
     * Gives us the current gamestate
     * 
     * @return the current gamestate
     */
    GameState getGameState();

    /**
     * Sets the gamestate
     * 
     * @param gameState the new gamestate
     */
    void setGameState(GameState gameState);

    /**
     * Gives us the current level
     * 
     * @return the current level
     */
    Integer getLevel();

    /**
     * Sets the level
     * 
     * @param level the new level
     */
    void setLevel(int level);

    /**
     * Gives us the time between each tick
     * 
     * @return milliseconds between each tick
     */
    Integer getDelay(int level);

    /**
     * Gets called each time the clock ticks
     */
    void clockTick();

    /**
     * Resets the game
     */
    void resetGame();

}
