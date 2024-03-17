package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used to generate a random tetromino, using the seven-bag
 * algorithm. The seven-bag algorithm is a way to ensure that the player will
 * not get the same tetromino multiple times in a row. The algorithm works by
 * having a bag with all the tetromino types, and then randomly picking one
 * tetromino from the bag. When the bag is empty, it is refilled with all the
 * tetromino types, and the process is repeated.
 */
public class SevenBagTetrominoFactory implements TetrominoFactory {

    /**
     * The string containing all the tetromino types
     */
    private static final String TETROMINOS = "LJSZTIO";

    /**
     * The list containing the tetromino types. It is emptied and refilled when the
     * bag is empty.
     */
    private List<Character> tetrominoBag = new ArrayList<>();

    @Override
    public Tetromino getNext() {
        if (tetrominoBag.isEmpty()) {
            for (char c : TETROMINOS.toCharArray()) {
                tetrominoBag.add(c);
            }
        }
        char randomType = tetrominoBag.remove(new Random().nextInt(tetrominoBag.size()));
        return Tetromino.newTetromino(randomType);
    }
}
