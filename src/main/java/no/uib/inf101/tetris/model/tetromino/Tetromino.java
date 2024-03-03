package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public class Tetromino implements Iterable<GridCell<Character>> {
    /**
     * The type of tetromino saved as a character.
     * It can be L, J, S, Z, T, I or O.
     */
    char type;

    /**
     * The shape of the tetromino saved as a 2D boolean array.
     * True means that the cell is part of the tetromino.
     */
    boolean[][] shape;

    /**
     * The position of the tetromino saved as a CellPosition.
     */
    CellPosition cellPosition;

    /**
     * The constructor for the Tetromino class.
     * It is private and can only be called from the newTetromino method,
     * because it should only be possible to make 7 different tetrominos.
     * 
     * @param type         the type of the tetromino
     * @param shape        the shape of the tetromino
     * @param cellPosition the position of the tetromino
     */
    private Tetromino(char type, boolean[][] shape, CellPosition cellPosition) {
        this.type = type;
        this.shape = shape;
        this.cellPosition = cellPosition;
    }

    /**
     * Get a new tetromino based on the parameter.
     * 
     * @param type the type of the tetromino as a character.
     * @return New tetromino object based on the parameter.
     */
    static Tetromino newTetromino(char type) {
        boolean[][] shape;
        switch (type) {
            case 'L':
                shape = new boolean[][] { { false, false, false },
                        { true, true, true },
                        { true, false, false } };
                break;
            case 'J':
                shape = new boolean[][] { { false, false, false },
                        { true, true, true },
                        { false, false, true } };
                break;
            case 'S':
                shape = new boolean[][] { { false, false, false },
                        { false, true, true },
                        { true, true, false } };
                break;
            case 'Z':
                shape = new boolean[][] { { false, false, false },
                        { true, true, false },
                        { false, true, true } };

                break;
            case 'T':
                shape = new boolean[][] { { false, false, false },
                        { true, true, true },
                        { false, true, false } };

                break;
            case 'I':
                shape = new boolean[][] { { false, false, false, false },
                        { true, true, true, true },
                        { false, false, false, false },
                        { false, false, false, false } };

                break;
            case 'O':
                shape = new boolean[][] { { false, false, false, false },
                        { false, true, true, false },
                        { false, true, true, false },
                        { false, false, false, false } };

                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }

        return new Tetromino(type, shape, new CellPosition(0, 0));

    }

    /**
     * Move the tetromino to a new position.
     * 
     * @param deltaRow number of rows the tetromino should be moved.
     * @param deltaCol number of columns the tetromino should be moved.
     * @return a new tetromino with the same type and shape, but with a new
     *         position.
     */
    public Tetromino shiftedBy(int deltaRow, int deltaCol) {
        CellPosition newPosition = new CellPosition(cellPosition.row() + deltaRow, cellPosition.col() + deltaCol);
        return new Tetromino(type, shape, newPosition);
    }

    /**
     * Move the tetromino to the top center of the grid.
     * 
     * @param gridDimension the dimension of the grid.
     * @return Tetromino with the same type and shape,
     *         but position in the middle.
     */
    public Tetromino shiftedToTopCenterOf(GridDimension gridDimension) {
        int col = (gridDimension.cols() - shape[0].length) / 2;
        return shiftedBy(0, col);
    }

    @Override
    public Iterator<GridCell<Character>> iterator() {
        ArrayList<GridCell<Character>> cells = new ArrayList<GridCell<Character>>();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    cells.add(new GridCell<Character>(new CellPosition(cellPosition.row() + i, cellPosition.col() + j),
                            type));
                }
            }
        }
        return cells.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Tetromino tetromino = (Tetromino) obj;
        return type == tetromino.type &&
                Arrays.deepEquals(shape, tetromino.shape) &&
                Objects.equals(cellPosition, tetromino.cellPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, Arrays.deepHashCode(shape), cellPosition);
    }

}
