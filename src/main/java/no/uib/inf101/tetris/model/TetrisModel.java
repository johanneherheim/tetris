package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel {

    TetrisBoard tetrisBoard;

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
