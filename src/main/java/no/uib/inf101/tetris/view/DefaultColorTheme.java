package no.uib.inf101.tetris.view;

import java.awt.Color;

/**
 * The default color theme for the tetris game.
 */
public class DefaultColorTheme implements ColorTheme {

    @Override
    public Color getCellColor(char c) {
        Color color = switch (c) {
            case 'r' -> Color.RED;
            case 'g' -> Color.GREEN;
            case 'y' -> Color.YELLOW;
            case 'b' -> Color.BLUE;
            case '-' -> Color.BLACK;
            default -> throw new IllegalArgumentException(
                    "No available color for '" + c + "'");
        };
        return color;

    }

    @Override
    public Color getFrameColor() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public Color getBackgroundColor() {
        return Color.WHITE;
    }
}