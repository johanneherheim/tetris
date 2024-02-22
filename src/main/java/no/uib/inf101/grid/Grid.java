package no.uib.inf101.grid;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid<E> implements IGrid<E> {
    private int rows;
    private int cols;
    private ArrayList<GridCell<E>> cells = new ArrayList<GridCell<E>>();

    public Grid(int rows, int cols, E defaultValue) {
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells.add(new GridCell<E>(new CellPosition(i, j), defaultValue));
            }
        }
    }

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells.add(new GridCell<E>(new CellPosition(i, j), null));
            }
        }
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }

    @Override
    public Iterator<GridCell<E>> iterator() {
        ArrayList<GridCell<E>> grid = new ArrayList<GridCell<E>>(cells);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells.add(new GridCell<E>(new CellPosition(i, j), null));
            }
        }

        return grid.iterator();
    }

    @Override
    public void set(CellPosition pos, E value) {
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).pos().equals(pos)) {
                cells.set(i, new GridCell<E>(pos, value));
            }
        }
    }

    @Override
    public E get(CellPosition pos) {
        return cells.get(pos.row() * cols + pos.col()).value();
    }

    @Override
    public boolean positionIsOnGrid(CellPosition pos) {
        for (GridCell<E> cell : cells) {
            if (cell.pos().equals(pos)) {
                return true;
            }
        }
        return false;
    }

}
