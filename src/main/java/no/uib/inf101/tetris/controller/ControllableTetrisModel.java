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
     * Holds the tetromino
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
     * Gets the current level
     * 
     * @return level
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
