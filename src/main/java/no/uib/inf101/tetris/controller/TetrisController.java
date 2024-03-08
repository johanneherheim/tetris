package no.uib.inf101.tetris.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

public class TetrisController implements java.awt.event.KeyListener {
    ControllableTetrisModel controllableTetrisModel;
    TetrisView tetrisView;
    Timer timer;

    public TetrisController(ControllableTetrisModel controllableTetrisModel, TetrisView tetrisView) {
        this.controllableTetrisModel = controllableTetrisModel;
        this.tetrisView = tetrisView;
        tetrisView.addKeyListener(this);
        tetrisView.setFocusable(true);
        this.timer = new Timer(controllableTetrisModel.delay(1), this::clockTick);
        timer.start();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
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
            } else if (e.getKeyCode() == KeyEvent.VK_UP
                    && controllableTetrisModel.getGameState() == GameState.ACTIVE_GAME) {
                controllableTetrisModel.rotateTetromino();
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                controllableTetrisModel.dropTetromino();
            }
        } else if (controllableTetrisModel.getGameState() == GameState.WELCOME_SCREEN
                && e.getKeyCode() == KeyEvent.VK_S) {
            controllableTetrisModel.setGameState(GameState.ACTIVE_GAME);

        } else if (controllableTetrisModel.getGameState() == GameState.GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                controllableTetrisModel.setGameState(GameState.WELCOME_SCREEN);
                // TODO: implement restart method
            }
        }
        tetrisView.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    void clockTick(ActionEvent e) {
        if (controllableTetrisModel.getGameState() == GameState.ACTIVE_GAME) {
            controllableTetrisModel.clockTick();
            getDelay();
            tetrisView.repaint();
        }
    }

    void getDelay() {
        timer.setDelay(controllableTetrisModel.delay(controllableTetrisModel.getLevel()));
        timer.setInitialDelay(controllableTetrisModel.delay(controllableTetrisModel.getLevel()));
    }
}
