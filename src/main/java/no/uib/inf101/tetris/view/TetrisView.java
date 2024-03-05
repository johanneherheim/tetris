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

    /** The welcome message */
    private static final String[] welcomeMessage = { "Velkommen til tetris", "trykk s for å starte" };

    /** The message for choosing difficulty */
    private static final String[] chooseDifficultyMessage = { "press 1 for lett, 2 for medium", "OG 3 FOR VANSKELIG" };

    /** The game over message */
    private static final String[] gameOverMessage = { "Game over!" };

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
            drawCanvasWithText(g2, gameOverMessage, colorTheme.getTextColor(), 40);
        } else if (tetrisModel.getGameState() == GameState.WELCOME_SCREEN) {
            drawCanvasWithText(g2, welcomeMessage, colorTheme.getTextColor(), 30);
        } else if (tetrisModel.getGameState() == GameState.CHOOSE_DIFFICULTY) {
            drawCanvasWithText(g2, chooseDifficultyMessage, colorTheme.getTextColor(), 20);
            ;
        } else {
            drawGame(g2);
        }
    }

    /**
     * Returns the canvas for the board.
     * 
     * @return The canvas
     */
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

    /**
     * Draws the canvas with the given text over the tetris-board.
     * 
     * @param g2          The graphics object
     * @param linesOfText The text to be drawn in a list of strings
     * @param textColor   The color of the text
     * @param textSize    The size of the text
     */
    private void drawCanvasWithText(Graphics2D g2, String[] linesOfText, Color textColor, int textSize) {
        int lineSpace = 50;
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        Rectangle2D canvas = getCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(canvas);
        g2.setColor(textColor);
        g2.setFont(new Font("Arial", Font.BOLD, textSize));
        for (int i = 0; i < linesOfText.length; i++) {
            Inf101Graphics.drawCenteredString(g2, linesOfText[i], x, y + lineSpace * i);

        }
    }

}
