package no.uib.inf101.tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class has the methods that draws what the user sees in the game-window.
 */
public class TetrisView extends JPanel {

    /** The model for the Tetris-game */
    private ViewableTetrisModel tetrisModel;

    /** The colortheme for the Tetris-game */
    private ColorTheme colorTheme;

    /** The margin between the cells on the board */
    private static final double INNERMARGIN = 1;

    /** The margin around all the elements in the game */
    private static final double OUTERMARGIN = 15;

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
        if (tetrisModel.getGameState() == GameState.ACTIVE_GAME) {
            this.setFocusable(true);
        }
        this.setPreferredSize(new Dimension(1000, 800));
    }

    @Override
    public void paintComponent(Graphics g) {
        this.setBackground(colorTheme.getFrameColor());
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (tetrisModel.getGameState() == GameState.GAME_OVER) {
            drawGameOver(g2);
        } else if (tetrisModel.getGameState() == GameState.WELCOME_SCREEN) {
            drawWelcomeScreen(g2);
        } else if (tetrisModel.getGameState() == GameState.PAUSED) {
            drawPauseScreen(g2);
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
        drawBoard(g2);

        if (getHeight() * 4 / 5 < getWidth()) {
            drawRightSidebar(g2);
            drawLeftSidebar(g2);
        }
    }

    /**
     * Draws the tetris-board.
     * 
     * @param g2 The graphics object
     */
    private void drawBoard(Graphics2D g2) {
        Rectangle2D boardCanvas = getBoardCanvas();
        g2.setColor(colorTheme.getGridColor());
        g2.fill(boardCanvas);
        CellPositionToPixelConverter tetrisBoardConverter = new CellPositionToPixelConverter(boardCanvas,
                tetrisModel.getDimension(), INNERMARGIN);
        drawCells(g2, tetrisModel.getTilesOnBoard(), tetrisBoardConverter, colorTheme, false);
        drawCells(g2, tetrisModel.fallingTetromino(), tetrisBoardConverter, colorTheme, false);
        drawCells(g2, tetrisModel.getShadowPosition(), tetrisBoardConverter, colorTheme, true);
    }

    /**
     * Draws the left sidebar.
     * 
     * @param g2 The graphics object
     */
    private void drawLeftSidebar(Graphics2D g2) {
        drawInfoBox(g2);
    }

    /**
     * Draws the right sidebar.
     * 
     * @param g2 The graphics object
     */
    private void drawRightSidebar(Graphics2D g2) {
        drawQueuedTetrominos(g2);
        g2.setColor(colorTheme.getTextColor());
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        Inf101Graphics.drawCenteredString(g2, "Hold:", OUTERMARGIN * 4, OUTERMARGIN);
        if (tetrisModel.getHoldingTetromino() != null) {
            drawHoldingTetromino(g2, tetrisModel.getHoldingTetromino(), colorTheme);
        }
        g2.setFont(new Font("Arial", Font.PLAIN, 15));
        g2.setColor(colorTheme.getTextColor());
        // stack overflow
        // https://stackoverflow.com/questions/19582502/how-to-get-the-correct-string-width-from-fontmetrics-in-java
        // 17. mars 2024
        FontMetrics metrics = g2.getFontMetrics(new Font("Arial", Font.PLAIN, 15));
        int stringWidth = metrics.stringWidth("Trykk 'esc' for å pause");
        Inf101Graphics.drawCenteredString(g2, "Trykk 'esc' for å pause",
                getWidth() - OUTERMARGIN * 2 - stringWidth / 2, getHeight() - OUTERMARGIN * 2);
        stringWidth = metrics.stringWidth("Trykk 'c' for å holde");
        Inf101Graphics.drawCenteredString(g2, "Trykk 'c' for å holde",
                getWidth() - OUTERMARGIN * 2 - stringWidth / 2, getHeight() - OUTERMARGIN * 4);
    }

    /**
     * Draws the upcoming tetrominos on the side of the board.
     * 
     * @param g2 The graphics object
     */
    private void drawQueuedTetrominos(Graphics2D g2) {
        int startY = (int) OUTERMARGIN;
        int startX = (int) (getBoardCanvas().getMaxX() + OUTERMARGIN * 4);

        g2.setColor(colorTheme.getTextColor());
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Neste:", startX, (int) (startY + OUTERMARGIN));

        int tetrominoSize = this.getWidth() / 6;
        int spacing = 10;

        for (int i = 0; i < 3; i++) {
            Tetromino upcomingTetromino = tetrisModel.getNextTetrominos().get(i);
            drawSingleTetromino(g2, upcomingTetromino, startX, startY + i * (tetrominoSize + spacing), tetrominoSize);
        }
    }

    /**
     * Draws the holding tetromino on the side of the board.
     * 
     * @param g2         The graphics object
     * @param tetromino  The tetromino to draw
     * @param colorTheme The color theme
     */
    private void drawHoldingTetromino(Graphics2D g2, Tetromino tetromino,
            ColorTheme colorTheme) {
        drawSingleTetromino(g2, tetromino, (int) OUTERMARGIN * 2, (int) OUTERMARGIN * 2, this.getWidth() / 6);
    }

    /**
     * Draws a single tetromino.
     * 
     * @param g2            The graphics object
     * @param tetromino     The tetromino to draw
     * @param x             The x-coordinate
     * @param y             The y-coordinate
     * @param tetrominoSize The size of the tetromino
     */
    private void drawSingleTetromino(Graphics2D g2, Tetromino tetromino, int x, int y, int tetrominoSize) {
        boolean[][] shape = tetromino.getShape();
        // if the shape is 3x3, we need to add a row and a col of false, so the size is
        // constant
        if (shape.length == 3) {
            boolean[][] newShape = new boolean[4][4];
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    newShape[i][j] = shape[i][j];
                }
            }
            shape = newShape;
        }
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(x, y, tetrominoSize, tetrominoSize), new Grid<>(shape.length, shape[0].length),
                INNERMARGIN);
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    Rectangle2D tile = converter.getBoundsForCell(new CellPosition(i, j));
                    g2.setColor(colorTheme.getCellColor(tetromino.getType()));
                    g2.fill(tile);
                }
            }
        }
    }

    /**
     * Draws the info-box with the score, lines and level.
     * 
     * @param g2 The graphics object
     */
    private void drawInfoBox(Graphics2D g2) {
        Rectangle2D box = new Rectangle2D.Double(OUTERMARGIN * 2, this.getHeight() / 2, this.getWidth() / 6,
                this.getWidth() / 6);
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(box);
        g2.setColor(colorTheme.getTextColor());
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        Inf101Graphics.drawCenteredString(g2, "Lines: " + tetrisModel.getLines(), box.getCenterX(),
                box.getCenterY() - box.getWidth() / 4);
        Inf101Graphics.drawCenteredString(g2, "Score: " + tetrisModel.getScore(), box.getCenterX(), box.getCenterY());
        Inf101Graphics.drawCenteredString(g2, "Level: " + tetrisModel.getLevel(), box.getCenterX(),
                box.getCenterY() + box.getWidth() / 4);
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
     * Returns the canvas for the board.
     * 
     * @return The canvas for the board
     */
    private Rectangle2D getBoardCanvas() {
        double maxSize = Math.min(this.getWidth(), this.getHeight() / 2);

        double width = maxSize;
        double height = maxSize * 2;

        double x = (this.getWidth() - width) / 2;
        double y = (this.getHeight() - height) / 2 + OUTERMARGIN;

        double boxX = x + OUTERMARGIN;
        double boxY = y + OUTERMARGIN;
        double boxWidth = width - 2 * OUTERMARGIN;
        double boxHeight = height - 4 * OUTERMARGIN;

        return new Rectangle2D.Double(boxX, boxY, boxWidth, boxHeight);
    }

    /**
     * Returns the canvas for the whole screen.
     * 
     * @return The canvas for the whole screen
     */
    private Rectangle2D getScreenCanvas() {
        double x0 = OUTERMARGIN;
        double y0 = OUTERMARGIN;
        double width = this.getWidth() - 2 * OUTERMARGIN;
        double height = this.getHeight() - 2 * OUTERMARGIN;
        return new Rectangle2D.Double(x0, y0, width, height);
    }

    /**
     * Returns the three highest scores from the highscore-file.
     * 
     * @return List of the three highest scores
     */
    private ArrayList<Integer> getThreeHighestScores() {
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
            System.err.println("Error occrued while trying to read the highscores");
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

    /**
     * Draws the game over screen.
     * 
     * @param g2 The graphics object
     */
    private void drawGameOver(Graphics2D g2) {
        ArrayList<Integer> highestScores = getThreeHighestScores();
        Integer score = tetrisModel.getScore();
        Rectangle2D boardCanvas = getScreenCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(boardCanvas);
        g2.setColor(colorTheme.getTextColor());
        g2.setFont(new Font("Arial", Font.BOLD, getWidth() / 20));
        Inf101Graphics.drawCenteredString(g2, "Game over!", getWidth() / 2, getHeight() * 1 / 5);
        g2.setFont(new Font("Arial", Font.BOLD, getWidth() / 35));
        if (score >= highestScores.get(0) && score > 0) {
            Inf101Graphics.drawCenteredString(g2, "🎉 Ny rekord med " + score + " poeng! 🎉", getWidth() / 2,
                    getHeight() / 3);
        } else if (score == 0) {
            Inf101Graphics.drawCenteredString(g2, "Du fikk bare " + score + " poeng. Så trist! 😢", getWidth() / 2,
                    getHeight() / 3);
        } else {
            Inf101Graphics.drawCenteredString(g2, "Du fikk " + score + " poeng 🦾", getWidth() / 2, getHeight() / 3);
        }
        Inf101Graphics.drawCenteredString(g2, "Rekorder:", getWidth() / 2, getHeight() / 2);
        g2.setFont(new Font("Arial", Font.PLAIN, getWidth() / 35));
        Inf101Graphics.drawCenteredString(g2, "1. " + highestScores.get(0), getWidth() / 2, getHeight() / 2 + 50);
        Inf101Graphics.drawCenteredString(g2, "2. " + highestScores.get(1), getWidth() / 2, getHeight() / 2 + 100);
        Inf101Graphics.drawCenteredString(g2, "3. " + highestScores.get(2), getWidth() / 2, getHeight() / 2 + 150);
        g2.setFont(new Font("Arial", Font.BOLD, getWidth() / 35));
        Inf101Graphics.drawCenteredString(g2, "Trykk 'enter' for å starte på nytt", getWidth() / 2,
                getHeight() / 2 + 300);
    }

    /**
     * Draws the welcome screen.
     * 
     * @param g2 The graphics object
     */
    private void drawWelcomeScreen(Graphics2D g2) {
        Rectangle2D screenCanvas = getScreenCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(screenCanvas);
        g2.setColor(colorTheme.getTextColor());
        g2.setFont(new Font("Arial", Font.BOLD, getWidth() / 20));
        Inf101Graphics.drawCenteredString(g2, "VELKOMMEN TIL TETRIS!", getWidth() / 2, getHeight() * 2 / 5);
        g2.setFont(new Font("Arial", Font.PLAIN, getWidth() / 35));
        Inf101Graphics.drawCenteredString(g2, "👉 Trykk 's' for å starte", getWidth() / 2, getHeight() * 4 / 5);

        // Toggle color theme button
        g2.setFont(new Font("Arial", Font.PLAIN, getWidth() / 60));
        Inf101Graphics.drawCenteredString(g2, "trykk 'l' for lightmode, og 'd' for darkmode", getWidth() / 2,
                getHeight() * 19 / 20);
    }

    /**
     * Draws the pause screen.
     * 
     * @param g2 The graphics object
     */
    private void drawPauseScreen(Graphics2D g2) {
        Rectangle2D screenCanvas = getScreenCanvas();
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(screenCanvas);
        g2.setColor(colorTheme.getTextColor());
        g2.setFont(new Font("Arial", Font.BOLD, getWidth() / 20));
        Inf101Graphics.drawCenteredString(g2, "PAUSE", getWidth() / 2, getHeight() * 1 / 3);
        g2.setFont(new Font("Arial", Font.BOLD, getWidth() / 35));
        Inf101Graphics.drawCenteredString(g2, "👉 Trykk 's' for å fortsette", getWidth() / 2, getHeight() * 3 / 5);
        g2.setFont(new Font("Arial", Font.PLAIN, getWidth() / 50));
        Inf101Graphics.drawCenteredString(g2, "Trykk 'enter' for å starte på nytt", getWidth() / 2,
                getHeight() * 7 / 8);
    }

    /**
     * Sets the colortheme for the game.
     * 
     * @param colorTheme The colortheme
     */
    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

}
