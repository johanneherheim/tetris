package no.uib.inf101.tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.GameState;

/**
 * This class has the methods that draws what the user sees in the game-window.
 */
public class TetrisView extends JPanel {

    /** The model for the Tetris-game */
    ViewableTetrisModel tetrisModel;

    /** The colortheme for the Tetris-game */
    private ColorTheme colorTheme;

    /** The margin between the cells on the board */
    private static final double TETRISINNERMARGIN = 1;

    /** The margin around all the elements in the game */
    private static final double MARGIN = 15;

    /**
     * Constructor for TetrisView.
     * It saves the tetrismodel in a variable and sets the colortheme to default.
     * The background-color and windowsize is set, and the window is also set to
     * listen to keyboard.
     * 
     * @param tetrisModel The Tetris-model
     */
    public TetrisView(ViewableTetrisModel tetrisModel) {
        this.tetrisModel = tetrisModel;
        this.colorTheme = new DefaultColorTheme();
        this.setBackground(colorTheme.getFrameColor());
        if (tetrisModel.getGameState() == GameState.ACTIVE_GAME) {
            this.setFocusable(true);
        }
        this.setPreferredSize(new Dimension(1000, 800));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (tetrisModel.getGameState() == GameState.GAME_OVER) {
            drawGameOver(g2);
        } else if (tetrisModel.getGameState() == GameState.WELCOME_SCREEN) {
            drawWelcomeScreen(g2);
        } else if (tetrisModel.getGameState() == GameState.CHOOSE_DIFFICULTY) {
            drawChooseDifficulty(g2);
        } else {
            drawGame(g2);
        }
    }

    private Rectangle2D getCanvas() {
        double maxSize = Math.min(this.getWidth(), this.getHeight() / 2);

        double width = maxSize;
        double height = maxSize * 2;

        double x = (this.getWidth() - width) / 2;
        double y = (this.getHeight() - height) / 2 + MARGIN;

        double boxX = x + MARGIN;
        double boxY = y + MARGIN;
        double boxWidth = width - 2 * MARGIN;
        double boxHeight = height - 4 * MARGIN;

        return new Rectangle2D.Double(boxX, boxY, boxWidth, boxHeight);
    }

    /**
     * Draws the game-elements.
     * 
     * @param g2 The graphics object
     */

    private void drawGame(Graphics2D g2) {

        Rectangle2D canvas = getCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(canvas);

        CellPositionToPixelConverter cellInfo = new CellPositionToPixelConverter(canvas,
                tetrisModel.getDimension(), TETRISINNERMARGIN);
        drawCells(g2, tetrisModel.getTilesOnBoard(), cellInfo, colorTheme, true);
        drawCells(g2, tetrisModel.fallingTetromino(), cellInfo, colorTheme, true);
        drawCells(g2, tetrisModel.getShadowPosition(), cellInfo, colorTheme, false);
    }

    /**
     * Draws the cells in a given grid.
     * 
     * @param g2         The graphics object
     * @param grid       The grid
     * @param converter  The cellPosition -> pixel converter
     * @param colorTheme The color theme
     * @param isShadow   If the cells are the main cells or just the shadow
     */
    private static void drawCells(Graphics2D g2, Iterable<GridCell<Character>> grid,
            CellPositionToPixelConverter converter, ColorTheme colorTheme, boolean isShadow) {
        for (GridCell<Character> gridCell : grid) {

            Rectangle2D tile = converter.getBoundsForCell(gridCell.pos());
            Color color = colorTheme.getCellColor(gridCell.value());
            if (!isShadow) {
                g2.setColor(color);
                g2.draw(tile);
                g2.setColor(new Color(255, 255, 255, 50));
                g2.fill(tile);
            } else {
                g2.setColor(color);
                g2.fill(tile);
            }
        }
    }

    private void drawGameOver(Graphics2D g2) {
        Rectangle2D canvas = getCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(canvas);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        Inf101Graphics.drawCenteredString(g2, "Game over!", canvas);
    }

    private void drawWelcomeScreen(Graphics2D g2) {
        double width = Math.min(getWidth(), getHeight() / 2);
        Rectangle2D canvas = getCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(canvas);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (width / 15)));
        Inf101Graphics.drawCenteredString(g2, "Velkommen til TETRIS", getWidth() / 2, getHeight() / 2);
        Inf101Graphics.drawCenteredString(g2, "press s for å starte", getWidth() / 2, getHeight() / 2 + 50);
    }

    private void drawChooseDifficulty(Graphics2D g2) {
        double width = Math.min(getWidth(), getHeight() / 2);
        Rectangle2D canvas = getCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(canvas);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (width / 15)));
        Inf101Graphics.drawCenteredString(g2, "press 1 for lett, 2 for medium", getWidth() / 2, getHeight() / 2);
        Inf101Graphics.drawCenteredString(g2, "OG 3 FOR VANSKELIG", getWidth() / 2, getHeight() / 2 + 50);
    }

}
