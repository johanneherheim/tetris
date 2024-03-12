package no.uib.inf101.tetris.model.tetromino;

public class PatternedTetrominoFactory implements TetrominoFactory {

    private String pattern;
    private int counter = 0;

    public PatternedTetrominoFactory(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Tetromino getNext() {
        char type = pattern.charAt(counter);
        counter = (counter + 1) % pattern.length();
        return Tetromino.newTetromino(type);
    }

}
