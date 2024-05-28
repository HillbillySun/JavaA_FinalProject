package util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Font {
    public static java.awt.Font creatFont(String path,float size)
    {
        java.awt.Font font = null;
        try {
            File fontFile = new File(path);
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,fontFile);
            font = font.deriveFont(size);
        }
        catch (FontFormatException | IOException e)
        {
            System.out.println(e);
        }
        return font;
    }
}
