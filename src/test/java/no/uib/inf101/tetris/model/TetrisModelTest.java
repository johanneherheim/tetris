package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.tetromino.PatternedTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModelTest {
    @Test
    public void initialPositionOfO() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        ViewableTetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.fallingTetromino()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
    }

    @Test
    public void initialPositionOfI() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("I");
        ViewableTetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.fallingTetromino()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 3), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 6), 'I')));
    }

    @Test
    public void successfulMove() {
        TetrisBoard board = new TetrisBoard(3, 6);
        TetrominoFactory tetrominoFactory = new PatternedTetrominoFactory("I");
        TetrisModel tetrisModel = new TetrisModel(board, tetrominoFactory);

        assertTrue(tetrisModel.moveTetromino(0, 1));
    }

    @Test
    public void unSuccessfulMove() {
        TetrisBoard board = new TetrisBoard(3, 6);
        TetrominoFactory tetrominoFactory = new PatternedTetrominoFactory("I");
        TetrisModel tetrisModel = new TetrisModel(board, tetrominoFactory);
        board.set(new CellPosition(0, 0), 'I');

        List<GridCell<Character>> initialPositions = new ArrayList<>();
        for (GridCell<Character> position : tetrisModel.fallingTetromino()) {
            initialPositions.add(position);
        }
        assertFalse(tetrisModel.moveTetromino(0, -1));

        List<GridCell<Character>> newPositions = new ArrayList<>();
        for (GridCell<Character> position : tetrisModel.fallingTetromino()) {
            newPositions.add(position);
        }

        assertEquals(initialPositions, newPositions);
    }

    @Test
    public void successfulMoveChangesIteration() {
        TetrisBoard board = new TetrisBoard(3, 6);
        TetrominoFactory tetrominoFactory = new PatternedTetrominoFactory("I");
        TetrisModel tetrisModel = new TetrisModel(board, tetrominoFactory);

        List<GridCell<Character>> initialPositions = new ArrayList<>();
        for (GridCell<Character> position : tetrisModel.fallingTetromino()) {
            initialPositions.add(position);
        }

        tetrisModel.moveTetromino(0, -1);

        List<GridCell<Character>> newPositions = new ArrayList<>();
        for (GridCell<Character> position : tetrisModel.fallingTetromino()) {
            newPositions.add(position);
        }

        assertNotEquals(initialPositions, newPositions);
    }

    @Test
    public void moveOutsideBoard() {
        TetrisBoard tetrisBoard = new TetrisBoard(4, 4);
        TetrominoFactory tetrominoFactory = new PatternedTetrominoFactory("I");
        TetrisModel tetrisModel = new TetrisModel(tetrisBoard, tetrominoFactory);

        assertFalse(tetrisModel.moveTetromino(0, -1));
    }

    @Test
    public void moveToOccupiedCell() {
        TetrisBoard board = new TetrisBoard(3, 6);
        TetrominoFactory tetrominoFactory = new PatternedTetrominoFactory("I");
        TetrisModel tetrisModel = new TetrisModel(board, tetrominoFactory);
        board.set(new CellPosition(0, 0), 'I');

        List<GridCell<Character>> initialPositions = new ArrayList<>();
        for (GridCell<Character> position : tetrisModel.fallingTetromino()) {
            initialPositions.add(position);
        }

        tetrisModel.moveTetromino(0, -1);

        List<GridCell<Character>> newPositions = new ArrayList<>();
        for (GridCell<Character> position : tetrisModel.fallingTetromino()) {
            newPositions.add(position);
        }

        assertEquals(initialPositions, newPositions);

    }

    @Test
    public void testRotateTetromino() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory tetrominoFactory = new PatternedTetrominoFactory("I");
        TetrisModel tetrisModel = new TetrisModel(board, tetrominoFactory);

        tetrisModel.moveTetromino(2, 0);

        Tetromino startTetromino = tetrisModel.tetromino;

        assertTrue(tetrisModel.rotateTetromino());

        assertNotEquals(startTetromino, tetrisModel.tetromino);
    }

    @Test
    public void testRotateTetrominoWithWallKick() {
        TetrisBoard board = new TetrisBoard(3, 6);
        TetrominoFactory tetrominoFactory = new PatternedTetrominoFactory("L");
        TetrisModel tetrisModel = new TetrisModel(board, tetrominoFactory);

        Tetromino startTetromino = tetrisModel.tetromino;

        assertTrue(tetrisModel.rotateTetromino());

        assertNotEquals(startTetromino, tetrisModel.tetromino);
    }

    @Test
    public void testDropTetromino() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory tetrominoFactory = new PatternedTetrominoFactory("I");
        TetrisModel tetrisModel = new TetrisModel(board, tetrominoFactory);

        tetrisModel.moveTetromino(2, 0);

        Tetromino startTetromino = tetrisModel.tetromino;

        tetrisModel.dropTetromino();

        assertNotEquals(startTetromino, tetrisModel.tetromino);
    }

    @Test
    public void testClockTick() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory tetrominoFactory = new PatternedTetrominoFactory("I");
        TetrisModel tetrisModel = new TetrisModel(board, tetrominoFactory);

        Tetromino startTetromino = tetrisModel.tetromino;

        tetrisModel.clockTick();

        assertNotEquals(startTetromino, tetrisModel.tetromino);
    }
}
