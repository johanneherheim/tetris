package no.uib.inf101.tetris.view;

import java.awt.Color;

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
        // You can choose a frame color here
        // For example, return Color.GRAY for a gray frame, or use a transparent color.
        return new Color(0, 0, 0, 0); // Fully transparent black
    }

    @Override
    public Color getBackgroundColor() {
        // You can choose a background color here
        // For example, return Color.WHITE for a white background.
        return Color.WHITE;
    }
}