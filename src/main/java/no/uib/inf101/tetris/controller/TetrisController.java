package no.uib.inf101.tetris.controller;

import java.awt.event.KeyEvent;

import no.uib.inf101.tetris.view.TetrisView;

public class TetrisController implements java.awt.event.KeyListener {
    ControllableTetrisModel controllableTetrisModel;
    TetrisView tetrisView;

    public TetrisController(ControllableTetrisModel controllableTetrisModel, TetrisView tetrisView) {
        this.controllableTetrisModel = controllableTetrisModel;
        this.tetrisView = tetrisView;
        tetrisView.addKeyListener(this);
        tetrisView.setFocusable(true);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            controllableTetrisModel.moveTetromino(0, -1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            controllableTetrisModel.moveTetromino(0, 1);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            controllableTetrisModel.moveTetromino(1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            controllableTetrisModel.moveTetromino(-1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Spacebar was pressed
        }
        tetrisView.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
