package no.uib.inf101.tetris.view;

import java.awt.Color;

/**
 * The default color theme for the tetris game.
 */
public class DefaultColorTheme implements ColorTheme {

    @Override
    public Color getCellColor(char c) {
        Color color = switch (c) {
            case '-' -> Color.DARK_GRAY;
            case 'T' -> new Color(255, 76, 97); // Lighter red
            case 'O' -> new Color(255, 167, 96); // Lighter orange
            case 'I' -> new Color(255, 237, 100); // Lighter yellow
            case 'J' -> new Color(90, 192, 158); // Lighter greenish-blue
            case 'L' -> new Color(0, 194, 255); // Lighter sky blue
            case 'S' -> new Color(165, 122, 220); // Lighter purple
            case 'Z' -> new Color(255, 133, 186); // Lighter pink

            default -> throw new IllegalArgumentException("No available color for '" + c + "'");
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