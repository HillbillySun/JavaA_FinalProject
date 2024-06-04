import view.LoadFrame;

import view.InitiaFrame;
import view.LoadFrame;
import javax.sound.sampled.*;
import javax.swing.*;

import static javax.swing.UIManager.setLookAndFeel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
                UIManager.setLookAndFeel(lookAndFeel);
                LoadFrame.OpenLoad();
            } catch (Exception e) {
                e.printStackTrace();
                LoadFrame.OpenLoad();
            }
        });
    }
}
