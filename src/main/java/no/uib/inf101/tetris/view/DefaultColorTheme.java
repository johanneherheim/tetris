package no.uib.inf101.tetris.view;

import java.awt.Color;

/**
 * The default color theme for the tetris game.
 */
public class DefaultColorTheme implements ColorTheme {

    @Override
    public Color getCellColor(char c) {
        Color color = switch (c) {
            case '-' -> new Color(31, 31, 31);
            case 'T' -> new Color(255, 76, 97); // red
            case 'O' -> new Color(255, 167, 96); // orange
            case 'I' -> new Color(255, 237, 100); // yellow
            case 'J' -> new Color(90, 192, 158); // green-blue
            case 'L' -> new Color(0, 194, 255); // blue
            case 'S' -> new Color(165, 122, 220); // purple
            case 'Z' -> new Color(255, 133, 186); // pink

            default -> throw new IllegalArgumentException("No available color for '" + c + "'");
        };
        return color;

    }

    @Override
    public Color getFrameColor() {
        return Color.DARK_GRAY;
    }

    @Override
    public Color getGridColor() {
        return Color.BLACK;
    }

    @Override
    public Color getBackgroundColor() {
        return new Color(31, 31, 31);
    }

    @Override
    public Color getTextColor() {
        return Color.WHITE;
    }
}