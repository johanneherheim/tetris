package no.uib.inf101.tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import no.uib.inf101.grid.GridCell;

public class TetrisView extends JPanel {
    ViewableTetrisModel tetrisModel;
    private ColorTheme colorTheme;
    private static final int TETRISINNERMARGIN = 1;
    private static final int MARGIN = 15;

    public TetrisView(ViewableTetrisModel tetrisModel) {
        this.tetrisModel = tetrisModel;
        this.colorTheme = new DefaultColorTheme();
        this.setBackground(colorTheme.getFrameColor());
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(400, 800));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    /**
     * Draws the game-elements
     * 
     * @param g2 the graphics object
     */

    private void drawGame(Graphics2D g2) {
        Rectangle2D background = new Rectangle(MARGIN, MARGIN, getWidth() - 2 * MARGIN, getHeight() - 2 * MARGIN);
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(background);

        CellPositionToPixelConverter cellInfo = new CellPositionToPixelConverter(background,
                tetrisModel.getDimension(), TETRISINNERMARGIN);
        drawCells(g2, tetrisModel.getTilesOnBoard(), cellInfo, colorTheme);
    }

    /**
     * Draws the cells
     * 
     * @param g2         the graphics object
     * @param grid       the grid
     * @param converter  the cell position to pixel converter
     * @param colorTheme the color theme
     */
    private static void drawCells(Graphics2D g2, Iterable<GridCell<Character>> grid,
            CellPositionToPixelConverter converter, ColorTheme ct) {
        for (GridCell<Character> gridCell : grid) {
            Rectangle2D tile = converter.getBoundsForCell(gridCell.pos());
            // Color color = ct.getCellColor(gridCell.value());
            // g2.setColor(color);
            g2.setColor(Color.PINK);
            g2.fill(tile);

        }
    }
}
