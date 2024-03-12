package no.uib.inf101.tetris;

import java.awt.Toolkit;

import javax.swing.JFrame;

import no.uib.inf101.tetris.controller.TetrisController;
import no.uib.inf101.tetris.model.TetrisBoard;
import no.uib.inf101.tetris.model.TetrisModel;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.TetrisView;

public class TetrisMain {
  public static final String WINDOW_TITLE = "INF101 Tetris";

  public static void main(String[] args) {
    int rows = 20;
    int cols = 10;

    TetrisBoard tetrisBoard = new TetrisBoard(rows, cols);

    TetrominoFactory tetrominoFactory = new RandomTetrominoFactory();

    TetrisModel tetrisModel = new TetrisModel(tetrisBoard, tetrominoFactory);
    TetrisView view = new TetrisView(tetrisModel);

    @SuppressWarnings("unused")
    TetrisController controller = new TetrisController(tetrisModel, view);

    // The JFrame is the "root" application window.
    // We here set som properties of the main window,
    // and tell it to display our tetrisView
    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // stack overflow
    // https://stackoverflow.com/questions/21921135/using-setlocation-to-move-the-jframe-around-windows-java
    // 5. mars 2024
    int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    frame.setLocation((screenWidth - 1000) / 2, (screenHeight - 800) / 2);

    // Here we set which component to view in our window
    frame.setContentPane(view);

    // Call these methods to actually display the window
    frame.pack();
    frame.setVisible(true);
  }

}
