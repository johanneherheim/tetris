package no.uib.inf101.tetris.view;

import java.awt.Color;

/** Interface for all colorThemes. */
public interface ColorTheme {
    /**
     * Get the color for a cell with the given character.
     * 
     * @param c
     * @return Color
     */
    Color getCellColor(char c);

    /**
     * Get the color for the frame from the theme.
     * 
     * @return Color
     */
    Color getFrameColor();

    /**
     * Get the background color from the theme.
     * 
     * @return Color
     */
    Color getBackgroundColor();
}
