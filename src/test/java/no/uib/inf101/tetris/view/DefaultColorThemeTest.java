package no.uib.inf101.tetris.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class DefaultColorThemeTest {
    @Test
    public void sanityDefaultColorThemeTest() {
        ColorTheme colors = new DefaultColorTheme();
        assertEquals(new Color(31, 31, 31), colors.getBackgroundColor());
        assertEquals(Color.DARK_GRAY, colors.getFrameColor());
        assertEquals(new Color(31, 31, 31), colors.getCellColor('-'));
        assertEquals(new Color(255, 76, 97), colors.getCellColor('T'));
        assertThrows(IllegalArgumentException.class, () -> colors.getCellColor('\n'));
    }

}
