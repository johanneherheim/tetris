package no.uib.inf101.tetris.model.tetromino;

/** A interface for creating tetrominos. */
public interface TetrominoFactory {
    /**
     * Get the next tetromino.
     * 
     * @return Tetromino
     */
    Tetromino getNext();
}
