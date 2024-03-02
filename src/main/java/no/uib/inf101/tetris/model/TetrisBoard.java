package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.Grid;

public class TetrisBoard extends Grid<Character> {

    public TetrisBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

}
