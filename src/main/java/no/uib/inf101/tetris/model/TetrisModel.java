package no.uib.inf101.tetris.model;

import java.util.ArrayList;

import no.uib.inf101.grid.CellPosition;
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

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {
    /** The tetrisBoard object saved in the model */
    TetrisBoard tetrisBoard;

    TetrominoFactory randomTetromino;
    Tetromino tetromino;

    public GameState gameState;

    /**
     * Class constructor
     * 
     * @param tetrisBoard the tetrisBoard
     */

    public TetrisModel(TetrisBoard tetrisBoard, TetrominoFactory randomTetromino) {
        this.tetrisBoard = tetrisBoard;
        this.randomTetromino = randomTetromino;
        tetromino = randomTetromino.getNext().shiftedToTopCenterOf(tetrisBoard);
        gameState = GameState.WELCOME_SCREEN;
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

    @Override
    public Iterable<GridCell<Character>> getShadowPosition() {
        Tetromino futureTetromino = tetromino;
        while (true) {
            Tetromino newTetromino = futureTetromino.shiftedBy(1, 0);
            if (newTetromino.isLegalMove(tetrisBoard, newTetromino)) {
                futureTetromino = newTetromino;
            } else {
                break;
            }
        }
        return futureTetromino;
    }

    @Override
    public boolean moveTetromino(int deltaRow, int deltaCol) {
        Tetromino newTetromino = tetromino.shiftedBy(deltaRow, deltaCol);
        if (tetromino.isLegalMove(tetrisBoard, newTetromino)) {
            tetromino = tetromino.shiftedBy(deltaRow, deltaCol);
            return true;
        }
        return false;
    }

    @Override
    public boolean rotateTetromino() {
        if (tetromino.isRotatable(tetrisBoard)) {
            tetromino = tetromino.rotate();
            return true;
        }
        return false;

    }

    public boolean getNewFallingTetromino() {
        tetromino = randomTetromino.getNext().shiftedToTopCenterOf(tetrisBoard);
        if (!tetromino.isLegalMove(tetrisBoard, tetromino)) {
            gameState = GameState.GAME_OVER;
            return false;
        }
        tetromino = randomTetromino.getNext().shiftedToTopCenterOf(tetrisBoard);
        return true;
    }

    void glueTetrominoToBoard(Tetromino tetromino, TetrisBoard tetrisBoard) {
        int startRow = tetromino.getCellPosition().row();
        int startCol = tetromino.getCellPosition().col();

        // saves all the positions to the tetromino relative to the grid
        ArrayList<CellPosition> tetrominoPositions = new ArrayList<>();
        for (int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[0].length; col++) {
                if (tetromino.getShape()[row][col]) {
                    tetrominoPositions.add(new CellPosition(row + startRow, col + startCol));
                }
            }
        }
        for (CellPosition tetrominoPosition : tetrominoPositions) {
            tetrisBoard.set(tetrominoPosition, tetromino.getType());
        }
        tetrisBoard.removeFullRows();
        getNewFallingTetromino();
    }

    @Override
    public void dropTetromino() {
        while (moveTetromino(1, 0)) {
        }
        glueTetrominoToBoard(tetromino, tetrisBoard);
        tetrisBoard.removeFullRows();
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void clockTick() {
        Tetromino newTetromino = tetromino.shiftedBy(1, 0);
        if (tetromino.isLegalMove(tetrisBoard, newTetromino)) {
            tetromino = tetromino.shiftedBy(1, 0);
        } else {
            glueTetrominoToBoard(tetromino, tetrisBoard);
        }
    }

    @Override
    public void setGameState(GameState activeGame) {
        gameState = activeGame;

    }

    @Override
    public Integer delay(int difficulty) {
        if (difficulty == 2) {
            return 400;
        } else if (difficulty == 3) {
            return 200;
        } else {
            return 1000;
        }
    }
}
