package no.uib.inf101.tetris.model.tetromino;

/**
 * A factory for creating tetrominos.
 */
public interface TetrominoFactory {
    /**
     * Get the next tetromino.
     * 
     * @return Tetromino
     */
    Tetromino getNext();
}
