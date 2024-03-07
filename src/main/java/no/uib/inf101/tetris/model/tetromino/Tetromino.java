package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

/** Objects in this class represents tetromino-bricks on the board. */
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
        return shiftedBy(-1, col);
    }

    /**
     * Method for checking if a tetromino is movable to a given position.
     * 
     * @param grid     the tetris-board
     * @param deltaRow move in y-direction
     * @param deltaCol move in x-direction
     * @return True or false
     */
    public boolean isLegalMove(Grid<Character> grid, Tetromino tetrominoCandidate) {
        int startRow = tetrominoCandidate.cellPosition.row();
        int startCol = tetrominoCandidate.cellPosition.col();

        // saves all the positions to the tetromino relative to the grid
        ArrayList<CellPosition> tetrominoPositions = new ArrayList<>();
        for (int row = 0; row < tetrominoCandidate.shape.length; row++) {
            for (int col = 0; col < tetrominoCandidate.shape[0].length; col++) {
                if (tetrominoCandidate.shape[row][col]) {
                    tetrominoPositions.add(new CellPosition(row + startRow, col + startCol));
                }
            }
        }

        // for all positions in the grid
        for (CellPosition tetrominoPosition : tetrominoPositions) {
            int gridRow = tetrominoPosition.row();
            int gridCol = tetrominoPosition.col();

            // if the position is outside the grid or overlaps with an occupied cell
            if (gridRow < 0 || gridRow >= grid.rows() || gridCol < 0 || gridCol >= grid.cols()
                    || !grid.get(tetrominoPosition).equals('-')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<GridCell<Character>> iterator() {
        ArrayList<GridCell<Character>> cells = new ArrayList<GridCell<Character>>();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    cells.add(new GridCell<Character>(new CellPosition(cellPosition.row() + i,
                            cellPosition.col() + j), type));
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

    /**
     * A method for checking if a tetromino is rotatable.
     * 
     * @param grid The tetris-board.
     * @return True or false
     */

    public boolean isRotatable(Grid<Character> grid) {
        ArrayList<CellPosition> availablePositions = new ArrayList<CellPosition>();

        // saves all the available positions in the grid in a list
        for (int gridRow = 0; gridRow < grid.rows(); gridRow++) {
            for (int gridCol = 0; gridCol < grid.cols(); gridCol++) {
                if (grid.get(new CellPosition(gridRow, gridCol)).equals('-')) {
                    availablePositions.add(new CellPosition(gridRow, gridCol));
                }
            }
        }

        Tetromino candidate = rotate();
        int startRow = candidate.cellPosition.row();
        int startCol = candidate.cellPosition.col();

        // check if the positions of the rotated tetromino is available
        for (int row = 0; row < candidate.shape.length; row++) {
            for (int col = 0; col < candidate.shape[0].length; col++) {
                if (!availablePositions.contains(new CellPosition(startRow + row, startCol + col))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Rotate the tetromino 90 degrees clockwise.
     * 
     * @return a new tetromino and rotated tetromino.
     */
    public Tetromino rotate() {
        boolean[][] newShape = new boolean[shape[0].length][shape.length];
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                newShape[col][shape.length - 1 - row] = shape[row][col];
            }
        }
        return new Tetromino(type, newShape, cellPosition);
    }

    /**
     * Get the position of the tetromino.
     * 
     * @return the position of the tetromino.
     */
    public CellPosition getCellPosition() {
        return cellPosition;
    }

    /**
     * Get the type of the tetromino.
     * 
     * @return the type of the tetromino.
     */
    public char getType() {
        return type;
    }

    /**
     * Get the shape of the tetromino.
     * 
     * @return the shape of the tetromino.
     */
    public boolean[][] getShape() {
        return shape;
    }
}
