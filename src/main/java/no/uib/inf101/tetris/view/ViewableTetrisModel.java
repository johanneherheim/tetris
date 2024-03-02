package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public interface ViewableTetrisModel {
    GridDimension getDimension();

    Iterable<GridCell<Character>> getTilesOnBoard();
}
