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
    ColorTheme colorTheme;
    private static final int TETRISINNERMARGIN = 1;

    public TetrisView(ViewableTetrisModel tetrisModel) {
        this.tetrisModel = tetrisModel;
        this.colorTheme = new DefaultColorTheme();
        this.setBackground(colorTheme.getBackgroundColor());
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(300, 400));
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
        Rectangle2D background = new Rectangle(0, 0, getWidth(), getHeight());
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(background);

        CellPositionToPixelConverter cellInfo = new CellPositionToPixelConverter(background,
                tetrisModel.getDimension(), TETRISINNERMARGIN);
        drawCells(g2, tetrisModel.getTilesOnBoard(), cellInfo, colorTheme);
    }

    private static void drawCells(Graphics2D g2, Iterable<GridCell<Character>> grid,
            CellPositionToPixelConverter cellInfo, ColorTheme colorTheme) {
        for (GridCell<Character> gridCell : grid) {
            Rectangle2D tile = cellInfo.getBoundsForCell(gridCell.pos());
            // Color color = colorTheme.getCellColor(gridCell.value());
            // g2.setColor(color);
            g2.setColor(Color.PINK);
            g2.fill(tile);

        }
    }
}
