package no.uib.inf101.tetris.model.tetromino;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.TetrisBoard;

public class TetrominoTest {
    @Test
    public void testHashCodeAndEquals() {
        Tetromino t1 = Tetromino.newTetromino('T');
        Tetromino t2 = Tetromino.newTetromino('T');
        Tetromino t3 = Tetromino.newTetromino('T').shiftedBy(1, 0);
        Tetromino s1 = Tetromino.newTetromino('S');
        Tetromino s2 = Tetromino.newTetromino('S').shiftedBy(0, 0);

        assertEquals(t1, t2);
        assertEquals(s1, s2);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(t1, t3);
        assertNotEquals(t1, s1);
    }

    @Test
    public void tetrominoIterationOfT() {
        // Create a standard 'T' tetromino placed at (10, 100) to test
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.shiftedBy(10, 100);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 100), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'T')));
    }

    @Test
    public void tetrominoIterationOfS() {
        // Create a standard 'T' tetromino placed at (10, 100) to test
        Tetromino tetro = Tetromino.newTetromino('S');
        tetro = tetro.shiftedBy(10, 100);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 100), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'S')));
    }

    @Test
    public void testDoubleMovement() {
        // Create a standard 'T' tetromino placed at (10, 100) to test
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.shiftedBy(10, 100);

        // Move the Tetromino once
        Tetromino movedOnce = tetro.shiftedBy(1, 2);

        // Move the original Tetromino twice
        Tetromino movedTwice = tetro.shiftedBy(2, 4);

        // Check that the overall movement is doubled
        assertEquals(movedOnce.shiftedBy(1, 2), movedTwice);
    }

    // Test for 3x3 Tetromino with an even number of columns
    @Test
    public void testShiftedToTopCenterOf1() {
        // Create a standard 'T' tetromino placed at (0, 0)
        TetrisBoard board = new TetrisBoard(4, 4);
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.shiftedToTopCenterOf(board);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 0), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 1), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 2), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 1), 'T')));
    }

    // Test for 3x3 Tetromino with an odd number of columns
    @Test
    public void testShiftedToTopCenterOf2() {
        // Create a standard 'T' tetromino placed at (0, 0)
        TetrisBoard board = new TetrisBoard(5, 5);
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.shiftedToTopCenterOf(board);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 1), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 2), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 3), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 2), 'T')));
    }

    // Test for 4x4 Tetromino with an even number of columns
    @Test
    public void testShiftedToTopCenterOf3() {
        // Create a standard 'T' tetromino placed at (0, 0)
        TetrisBoard board = new TetrisBoard(4, 8);
        Tetromino tetro = Tetromino.newTetromino('I');
        tetro = tetro.shiftedToTopCenterOf(board);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 2), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 3), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
    }

    @Test
    public void testShiftedToTopCenterOf4() {
        // Create a standard 'T' tetromino placed at (0, 0)
        TetrisBoard board = new TetrisBoard(4, 7);
        Tetromino tetro = Tetromino.newTetromino('I');
        tetro = tetro.shiftedToTopCenterOf(board);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 1), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 2), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 3), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
    }

    @Test
    public void testRotateTetrominoI() {

        // Create a standard 'I' tetromino placed at (0, 0)
        Tetromino tetro = Tetromino.newTetromino('I');
        tetro = tetro.rotate();

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 2), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 2), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 2), 'I')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(3, 2), 'I')));

    }

    @Test
    public void testRotateTetrominoT() {

        // Create a standard 'T' tetromino placed at (0, 0)
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.rotate();

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 1), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 1), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 1), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 0), 'T')));

    }
};
