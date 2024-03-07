package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

/** Objects in this class represents a tetris board. */
public class TetrisBoard extends Grid<Character> {

    /**
     * Creates a new TetrisBoard with the given number of rows and columns.
     * 
     * @param rows number of rows
     * @param cols number of columns
     */

    public TetrisBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

    /**
     * Method for displaying the values of the tetris board as a string.
     * 
     * @return a string representation of the board
     */

    public String prettyString() {
        String board = "";
        Integer count = 1;
        for (GridCell<Character> cell : this) {
            board += cell.value();
            if (count % this.cols() == 0) {
                board += "\n";
            }
            count += 1;
        }
        return board.substring(0, board.length() - 1);
    }

    boolean isRowFull(int row) {
        for (int col = 0; col < this.cols(); col++) {
            if (this.get(new CellPosition(row, col)) == '-') {
                return false;
            }
        }
        return true;
    }

    Integer removeFullRows() {
        TetrisBoard newBoard = this;
        // Opprett tellevariabel for å telle hvor mange rader som blir forkastet
        int a = this.rows() - 1;
        int b = this.rows() - 1;
        int lineCount = 0;

        // så lenge a er en rad på brettet
        while (a >= 0) {

            // så lenge b er en rad på brettet og er full
            while (b >= 0 && newBoard.isRowFull(b)) {
                b -= 1;
                lineCount++;
            }

            // hvis b fremdeles er på brettet
            if (b >= 0) {
                // kopier rekken b står ved inn i rekken a står ved
                for (int col = 0; col < newBoard.cols(); col++) {
                    newBoard.set(new CellPosition(a, col), newBoard.get(new CellPosition(b, col)));
                }
                // viss ingen b, fyll rekken a står ved med blanke ruter
            } else {
                for (int col = 0; col < newBoard.cols(); col++) {
                    newBoard.set(new CellPosition(a, col), '-');
                }
            }
            a -= 1;
            b -= 1;
        }

        return lineCount;
    }
}
