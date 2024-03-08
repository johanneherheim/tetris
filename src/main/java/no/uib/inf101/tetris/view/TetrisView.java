package no.uib.inf101.tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.GameState;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

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

    /** The game over message */
    ArrayList<String> gameOverMessage = new ArrayList<>(Arrays.asList("Game over"));

    /** The welcome message */
    ArrayList<String> welcomeMessage = new ArrayList<>(Arrays.asList("VELKOMMEN TIL TETRIS!", "Trykk s for å starte"));

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
            drawGameOver(g2, getThreeHighestScores(), tetrisModel.getScore());
        } else if (tetrisModel.getGameState() == GameState.WELCOME_SCREEN) {
            drawCanvasWithText(g2, welcomeMessage, colorTheme.getTextColor(), 20);
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
        drawCells(g2, tetrisModel.getTilesOnBoard(), cellInfo, colorTheme, false);
        drawCells(g2, tetrisModel.fallingTetromino(), cellInfo, colorTheme, false);
        drawCells(g2, tetrisModel.getShadowPosition(), cellInfo, colorTheme, true);
        drawInfo(g2);
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
            if (isShadow) {
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
    private void drawCanvasWithText(Graphics2D g2, ArrayList<String> linesOfText, Color textColor, int textSize) {
        int lineSpace = 50;
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        Rectangle2D canvas = getCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(canvas);
        g2.setColor(textColor);
        g2.setFont(new Font("Arial", Font.BOLD, textSize));
        int count = 1;
        for (String line : linesOfText) {
            Inf101Graphics.drawCenteredString(g2, line, x, y + lineSpace * count);
            count++;

        }
    }

    ArrayList<Integer> getThreeHighestScores() {
        int firstPlace = 0;
        int secondPlace = 0;
        int thirdPlace = 0;
        ArrayList<Integer> allScores = new ArrayList<>();
        // w3 schools https://www.w3schools.com/java/java_files_read.asp 7. mars 2024
        try {
            File myObj = new File("db/highscores.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                allScores.add(Integer.parseInt(data));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error occrued while trying to read highscores");
        }
        for (int score : allScores) {
            if (score > firstPlace) {
                thirdPlace = secondPlace;
                secondPlace = firstPlace;
                firstPlace = score;
            } else if (score > secondPlace) {
                thirdPlace = secondPlace;
                secondPlace = score;
            } else if (score > thirdPlace) {
                thirdPlace = score;
            }
        }

        return new ArrayList<Integer>(Arrays.asList(firstPlace, secondPlace, thirdPlace));
    }

    private void drawGameOver(Graphics2D g2, ArrayList<Integer> highestScores, Integer score) {
        Rectangle2D canvas = getCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(canvas);
        g2.setColor(colorTheme.getTextColor());
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        Inf101Graphics.drawCenteredString(g2, "Game over!", getWidth() / 2, getHeight() * 1 / 5);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        Inf101Graphics.drawCenteredString(g2, "Your score: " + score, getWidth() / 2, getHeight() / 3);
        Inf101Graphics.drawCenteredString(g2, "Highscores", getWidth() / 2, getHeight() / 2);

        Inf101Graphics.drawCenteredString(g2, "1. " + highestScores.get(0), getWidth() / 2, getHeight() / 2 + 50);
        Inf101Graphics.drawCenteredString(g2, "2. " + highestScores.get(1), getWidth() / 2, getHeight() / 2 + 100);
        Inf101Graphics.drawCenteredString(g2, "3. " + highestScores.get(2), getWidth() / 2, getHeight() / 2 + 150);
        Inf101Graphics.drawCenteredString(g2, "Press Enter to start a new game", getWidth() / 2, getHeight() / 2 + 300);
    }

    private void drawInfo(Graphics2D g2) {
        Rectangle2D box = new Rectangle2D.Double(MARGIN * 2, MARGIN * 2, this.getWidth() / 6,
                this.getWidth() / 6);
        g2.setColor(Color.WHITE);
        g2.fill(box);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        Inf101Graphics.drawCenteredString(g2, "Lines: " + tetrisModel.getLines(), box.getCenterX(),
                box.getCenterY() - box.getWidth() / 4);
        Inf101Graphics.drawCenteredString(g2, "Score: " + tetrisModel.getScore(), box.getCenterX(), box.getCenterY());
        Inf101Graphics.drawCenteredString(g2, "Level: " + tetrisModel.getLevel(), box.getCenterX(),
                box.getCenterY() + box.getWidth() / 4);
    }

}
