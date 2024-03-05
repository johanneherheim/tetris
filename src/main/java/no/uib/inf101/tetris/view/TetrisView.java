package no.uib.inf101.tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
        this.setPreferredSize(new Dimension(400, 800));
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

    /**
     * Draws the game-elements.
     * 
     * @param g2 The graphics object
     */

    private void drawGame(Graphics2D g2) {
        double width = Math.min(getWidth(), getHeight() / 2);
        Rectangle2D background = new Rectangle.Double(MARGIN, MARGIN, width - 2 * MARGIN, 2 * width - 2 * MARGIN);
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(background);

        CellPositionToPixelConverter cellInfo = new CellPositionToPixelConverter(background,
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
        double width = Math.min(getWidth(), getHeight() / 2);
        Rectangle2D background = new Rectangle.Double(MARGIN, MARGIN, width - 2 * MARGIN, 2 * width - 2 * MARGIN);
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(background);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        Inf101Graphics.drawCenteredString(g2, "Game over!", background);
    }

    private void drawWelcomeScreen(Graphics2D g2) {
        double width = Math.min(getWidth(), getHeight() / 2);
        Rectangle2D background = new Rectangle.Double(MARGIN, MARGIN, width - 2 * MARGIN, 2 * width - 2 * MARGIN);
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(background);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (width / 15)));
        Inf101Graphics.drawCenteredString(g2, "Velkommen til TETRIS", width / 2, width);
        Inf101Graphics.drawCenteredString(g2, "press s for å starte", width / 2, width + 50);
    }

    private void drawChooseDifficulty(Graphics2D g2) {
        double width = Math.min(getWidth(), getHeight() / 2);
        Rectangle2D background = new Rectangle.Double(MARGIN, MARGIN, width - 2 * MARGIN, 2 * width - 2 * MARGIN);
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(background);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (width / 15)));
        Inf101Graphics.drawCenteredString(g2, "press 1 for lett, 2 for medium", width / 2, width);
        Inf101Graphics.drawCenteredString(g2, "OG 3 FOR VANSKELIG", width / 2, width + 50);
    }

}
