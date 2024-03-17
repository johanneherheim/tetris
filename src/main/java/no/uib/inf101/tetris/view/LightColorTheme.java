package no.uib.inf101.tetris.view;

import java.awt.Color;

public class LightColorTheme implements ColorTheme {

    @Override
    public Color getCellColor(char c) {
        Color color = switch (c) {
            case '-' -> Color.GRAY;
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
        return Color.LIGHT_GRAY;
    }

    @Override
    public Color getGridColor() {
        return Color.DARK_GRAY;
    }

    @Override
    public Color getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public Color getTextColor() {
        return Color.DARK_GRAY;
    }
}
