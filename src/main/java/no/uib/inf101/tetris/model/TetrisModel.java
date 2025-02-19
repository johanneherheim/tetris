package no.uib.inf101.tetris.model;

import java.util.ArrayList;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class implements the ViewableTetrisModel interface, and is used to give
 * the view access to the model.
 */
public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {
    /** The tetromino object saved in the model */
    Tetromino tetromino;

    /** The tetrisBoard object saved in the model */
    private TetrisBoard tetrisBoard;

    /** The randomTetromino object saved in the model */
    private TetrominoFactory randomTetromino;

    /** The holdingTetromino object saved in the model */
    private Tetromino holdingTetromino;

    /** The next tetrominos saved in an arraylist */
    private ArrayList<Tetromino> tetrominoQueue;

    /** The gamestate of the game */
    private GameState gameState;

    /** The number of lines removed */
    private Integer lineCount;

    /** The score of the game */
    private Integer score;

    /** The level of the game */
    private Integer level;

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
        lineCount = 0;
        score = 0;
        level = 1;

        tetrominoQueue = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tetrominoQueue.add(randomTetromino.getNext());
        }
    }

    @Override
    public GridDimension getDimension() {
        return tetrisBoard;
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
    public Tetromino getHoldingTetromino() {
        return holdingTetromino;
    }

    @Override
    public ArrayList<Tetromino> getNextTetrominos() {
        return tetrominoQueue;
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
        Tetromino candidate = tetromino.shiftedBy(deltaRow, deltaCol);
        if (tetromino.isLegalMove(tetrisBoard, candidate)) {
            tetromino = candidate;
            return true;
        }
        return false;
    }

    @Override
    public boolean rotateTetromino() {
        Tetromino candidate = tetromino;
        // if a wall-kick is needed, the new position is saved in candidate:
        if (tetromino.getCellPosition().row() < 0) {
            candidate = candidate.shiftedBy(1, 0);
        } else if (tetromino.getCellPosition().col() < 0) {
            candidate = candidate.shiftedBy(0, 1);
        } else if (tetromino.getCellPosition().col() + tetromino.getShape()[0].length > tetrisBoard.cols()) {
            candidate = candidate.shiftedBy(0, -1);
        }
        // if the tetromino is rotatable, it is rotated:
        if (tetromino.isRotatable(tetrisBoard)) {
            tetromino = tetromino.rotate();
            return true;
        }
        // if the tetromino is not rotatable, try wall-kick:
        else if (candidate.isRotatable(tetrisBoard)) {
            tetromino = candidate.rotate();
            return true;
        }
        return false;

    }

    /**
     * This method is used to get a new falling tetromino. If it is not possible to
     * move the tetromino, the game is over and the score is saved in a file.
     */
    private void getNewFallingTetromino() {
        Tetromino newTetromino = tetrominoQueue.get(0).shiftedToTopCenterOf(tetrisBoard);
        tetrominoQueue.remove(0);
        tetromino = newTetromino;
        tetrominoQueue.add(randomTetromino.getNext());
        if (!tetromino.isLegalMove(tetrisBoard, tetromino)) {
            gameState = GameState.GAME_OVER;
            // stack overflow
            // https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
            // 7. mars 2024
            try (FileWriter fw = new FileWriter("db/highscores.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println(score);
            } catch (IOException e) {
                System.err.println("An error occurred while saving your score.");
            }
        }
    }

    /**
     * This method is used to glue the tetromino to the board.
     * 
     * @param tetromino   the tetromino to be glued
     * @param tetrisBoard the tetrisBoard
     */
    private void glueTetrominoToBoard(Tetromino tetromino, TetrisBoard tetrisBoard) {
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
        Integer linesRemoved = tetrisBoard.removeFullRows();
        if (linesRemoved > 0) {
            score += getPoints(linesRemoved) * level;
            for (int i = 1; i < linesRemoved + 1; i++) {
                lineCount++;
                if (lineCount % 10 == 0) {
                    level++;
                }
            }
        }
        getNewFallingTetromino();
    }

    @Override
    public void dropTetromino() {
        while (moveTetromino(1, 0)) {
        }
        glueTetrominoToBoard(tetromino, tetrisBoard);
    }

    @Override
    public void holdTetromino() {
        if (holdingTetromino == null) {
            holdingTetromino = tetromino;
            getNewFallingTetromino();
        } else {
            Tetromino temp = tetromino;
            tetromino = holdingTetromino;
            holdingTetromino = temp;
        }
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
    public void setGameState(GameState gameState) {
        this.gameState = gameState;

    }

    @Override
    public Integer getDelay(int level) {
        level = Math.max(1, Math.min(level, 8));
        return 1000 - (level - 1) * 100;
    }

    @Override
    public String getLines() {
        return lineCount.toString();
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public Integer getPoints(int numberOfLines) {
        if (numberOfLines == 0) {
            return 0;
        } else if (numberOfLines == 1) {
            return 100;
        } else if (numberOfLines == 2) {
            return 300;
        } else if (numberOfLines == 3) {
            return 500;
        } else {
            return 800;
        }
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void resetGame() {
        gameState = GameState.WELCOME_SCREEN;

        tetrisBoard.clearBoard();

        score = 0;
        lineCount = 0;
        level = 1;

        getNewFallingTetromino();
    }
}
