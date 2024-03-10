package no.uib.inf101.tetris.model.tetromino;

import java.util.Random;

/** Generates a random tetromino */
public class RandomTetrominoFactory implements TetrominoFactory {

    @Override
    public Tetromino getNext() {
        String tetrominoTypes = "LJSZTIO";
        char randomType = tetrominoTypes.charAt(new Random().nextInt(tetrominoTypes.length()));

        return Tetromino.newTetromino(randomType);
    }
}
