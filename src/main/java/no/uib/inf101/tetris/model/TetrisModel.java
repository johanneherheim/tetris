package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

/**
 * This class implements the ViewableTetrisModel interface, and is used to give
 * the view access to the model.
 */

public class TetrisModel implements ViewableTetrisModel {
    /** The tetrisBoard object saved in the model */
    TetrisBoard tetrisBoard;

    /**
     * Class constructor
     * 
     * @param tetrisBoard the tetrisBoard
     */

    public TetrisModel(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard;
    }

    @Override
    public GridDimension getDimension() {
        return new Grid<>(tetrisBoard.rows(), tetrisBoard.cols());
    }

    @Override
    public Iterable<GridCell<Character>> getTilesOnBoard() {
        return tetrisBoard;
    }

}
