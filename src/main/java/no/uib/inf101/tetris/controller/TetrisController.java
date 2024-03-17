package no.uib.inf101.tetris.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import no.uib.inf101.tetris.midi.TetrisSong;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.DefaultColorTheme;
import no.uib.inf101.tetris.view.LightColorTheme;
import no.uib.inf101.tetris.view.TetrisView;

/**
 * The TetrisController class handles user input.
 */
public class TetrisController implements java.awt.event.KeyListener {

    /** The model */
    ControllableTetrisModel controllableTetrisModel;

    /** The view */
    TetrisView tetrisView;

    /** The timer */
    Timer timer;

    /** The tetris-music */
    TetrisSong music = new TetrisSong();

    /**
     * Constructor for the TetrisController class, specifying the
     * controllableTetrisModel and the tetrisView.
     * 
     * @param controllableTetrisModel the controllableTetrisModel to control
     * @param tetrisView              the tetrisView to update
     */
    public TetrisController(ControllableTetrisModel controllableTetrisModel, TetrisView tetrisView) {
        this.controllableTetrisModel = controllableTetrisModel;
        this.tetrisView = tetrisView;
        tetrisView.addKeyListener(this);
        tetrisView.setFocusable(true);
        this.timer = new Timer(controllableTetrisModel.getDelay(1), this::clockTick);
        timer.start();
        music.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (controllableTetrisModel.getGameState() == GameState.ACTIVE_GAME) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                controllableTetrisModel.moveTetromino(0, -1);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                controllableTetrisModel.moveTetromino(0, 1);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                controllableTetrisModel.moveTetromino(1, 0);
                timer.restart();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                controllableTetrisModel.rotateTetromino();
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                controllableTetrisModel.dropTetromino();
            } else if (e.getKeyCode() == KeyEvent.VK_C) {
                controllableTetrisModel.holdTetromino();
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                controllableTetrisModel.setGameState(GameState.PAUSED);
            }
        } else if (controllableTetrisModel.getGameState() == GameState.WELCOME_SCREEN) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                controllableTetrisModel.setGameState(GameState.ACTIVE_GAME);
            } else if (e.getKeyCode() == KeyEvent.VK_L) {
                tetrisView.setColorTheme(new LightColorTheme());
            } else if (e.getKeyCode() == KeyEvent.VK_L) {
                tetrisView.setColorTheme(new LightColorTheme());
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                tetrisView.setColorTheme(new DefaultColorTheme());
            }
        } else if (controllableTetrisModel.getGameState() == GameState.PAUSED
                && e.getKeyCode() == KeyEvent.VK_S) {
            controllableTetrisModel.setGameState(GameState.ACTIVE_GAME);

        } else if (controllableTetrisModel.getGameState() == GameState.GAME_OVER
                || controllableTetrisModel.getGameState() == GameState.PAUSED) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                controllableTetrisModel.resetGame();
            }
        }
        tetrisView.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Method for the clockTick
     * 
     * @param e the action event
     */
    private void clockTick(ActionEvent e) {
        if (controllableTetrisModel.getGameState() == GameState.ACTIVE_GAME) {
            controllableTetrisModel.clockTick();
            getDelay();
            tetrisView.repaint();
        }
    }

    /**
     * Method for getting the delay
     */
    private void getDelay() {
        timer.setDelay(controllableTetrisModel.getDelay(controllableTetrisModel.getLevel()));
        timer.setInitialDelay(controllableTetrisModel.getDelay(controllableTetrisModel.getLevel()));
    }
}
