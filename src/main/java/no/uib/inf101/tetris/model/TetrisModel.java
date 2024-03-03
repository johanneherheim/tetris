package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

/**
 * This class implements the ViewableTetrisModel interface, and is used to give
 * the view access to the model.
 */

public class TetrisModel implements ViewableTetrisModel {
    /** The tetrisBoard object saved in the model */
    TetrisBoard tetrisBoard;

    TetrominoFactory randomTetromino;
    Tetromino tetromino;

    /**
     * Class constructor
     * 
     * @param tetrisBoard the tetrisBoard
     */

    public TetrisModel(TetrisBoard tetrisBoard, TetrominoFactory randomTetromino) {
        this.tetrisBoard = tetrisBoard;
        this.randomTetromino = randomTetromino;
        tetromino = randomTetromino.getNext().shiftedToTopCenterOf(tetrisBoard);
    }

    @Override
    public GridDimension getDimension() {
        return new Grid<>(tetrisBoard.rows(), tetrisBoard.cols());
    }

    @Override
    public Iterable<GridCell<Character>> getTilesOnBoard() {
        return tetrisBoard;
    }

    @Override
    public Iterable<GridCell<Character>> fallingTetromino() {
        return tetromino;
    }
}
