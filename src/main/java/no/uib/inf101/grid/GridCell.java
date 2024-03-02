package no.uib.inf101.grid;

/**
 * A GridCell consists of a Cellposition and a value.
 * 
 * @param <E>   the type of the value
 * @param pos   the position of the cell
 * @param value the value of the cell
 */
public record GridCell<E>(CellPosition pos, E value) {
}
