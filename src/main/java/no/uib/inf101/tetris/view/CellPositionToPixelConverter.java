package no.uib.inf101.tetris.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

public class CellPositionToPixelConverter {

    /** The rectangle that the grid is drawn in. */
    Rectangle2D box;

    /** The grid dimension of the grid (number of rows and cols). */
    GridDimension gd;

    /** The width of the grid lines. */
    double MARGIN;

    /**
     * Constructor for the CellPositionToPixelConverter
     * 
     * @param box    the rectangle that the grid is drawn in
     * @param gd     the grid dimension of the grid (number of rows and cols)
     * @param margin the width of the grid lines
     */
    public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
        this.box = box;
        this.gd = gd;
        this.MARGIN = margin;
    }

    /**
     * Method for getting the bounds for a cell
     * 
     * @param cp the position of the cell
     * @return the cell as a Rectangle2D object
     */

    public Rectangle2D getBoundsForCell(CellPosition cp) {
        double boxTopY = this.box.getY();
        double boxTopX = this.box.getX();
        double boxWidth = this.box.getWidth();
        double boxHeight = this.box.getHeight();
        double row = cp.row();
        double col = cp.col();
        double rows = gd.rows();
        double cols = gd.cols();

        double cellHeight = (boxHeight - (rows + 1) * MARGIN) / rows;
        double cellWidth = (boxWidth - (cols + 1) * MARGIN) / cols;

        double x = boxTopX + MARGIN * (col + 1) + cellWidth * col;
        ;
        double y = boxTopY + MARGIN * (row + 1) + cellHeight * row;
        return new Rectangle2D.Double(x, y, cellWidth, cellHeight);
    }
}
