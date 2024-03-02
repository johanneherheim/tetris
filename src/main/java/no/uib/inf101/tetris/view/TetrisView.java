package no.uib.inf101.tetris.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.text.View;

public class TetrisView extends JPanel {
    ViewableTetrisModel model;

    public TetrisView(ViewableTetrisModel model) {
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(300, 400));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

}
