package no.uib.inf101.tetris.view;

import java.awt.Color;

public interface ColorTheme {
    Color getCellColor(char c);

    Color getFrameColor();

    Color getBackgroundColor();
}
