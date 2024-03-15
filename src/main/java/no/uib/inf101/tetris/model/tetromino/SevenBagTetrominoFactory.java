package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SevenBagTetrominoFactory implements TetrominoFactory {

    private static final String allTetrominoTypes = "LJSZTIO";

    private List<Character> tetrominoBag = new ArrayList<>();

    @Override
    public Tetromino getNext() {

        if (tetrominoBag.isEmpty()) {
            for (char c : allTetrominoTypes.toCharArray()) {
                tetrominoBag.add(c);
            }
        }

        char randomType = tetrominoBag.remove(new Random().nextInt(tetrominoBag.size()));

        return Tetromino.newTetromino(randomType);
    }
}
