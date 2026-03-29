package sharosphere;

import sharosphere.ui.SplashScreen;
import sharosphere.util.Theme;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // Apply system look but override with our dark theme
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Global UI defaults to suppress unwanted borders/colors
        UIManager.put("Panel.background", Theme.BG_DARK);
        UIManager.put("OptionPane.background", Theme.BG_CARD);
        UIManager.put("OptionPane.messageForeground", Theme.TEXT_PRIMARY);
        UIManager.put("Button.background", Theme.BG_CARD2);
        UIManager.put("Button.foreground", Theme.TEXT_PRIMARY);
        UIManager.put("ComboBox.background", Theme.BG_CARD2);
        UIManager.put("ComboBox.foreground", Theme.TEXT_PRIMARY);
        UIManager.put("ComboBox.selectionBackground", Theme.BG_CARD);
        UIManager.put("ComboBox.selectionForeground", Theme.TEXT_PRIMARY);
        UIManager.put("List.background", Theme.BG_CARD2);
        UIManager.put("List.foreground", Theme.TEXT_PRIMARY);
        UIManager.put("List.selectionBackground", Theme.BG_CARD);
        UIManager.put("List.selectionForeground", Theme.TEXT_PRIMARY);
        UIManager.put("ScrollBar.background", Theme.BG_CARD);
        UIManager.put("ScrollBar.thumb", Theme.BG_CARD2);
        UIManager.put("ScrollBar.track", Theme.BG_DARK);
        UIManager.put("ScrollPane.background", Theme.BG_DARK);
        UIManager.put("Viewport.background", Theme.BG_DARK);
        // CRITICAL: force text fields to use dark bg and light text
        UIManager.put("TextField.background", Theme.BG_CARD2);
        UIManager.put("TextField.foreground", Theme.TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground", Theme.TEXT_PRIMARY);
        UIManager.put("TextField.selectionBackground", Theme.ACCENT2);
        UIManager.put("TextField.selectionForeground", Theme.TEXT_PRIMARY);
        UIManager.put("TextField.inactiveForeground", Theme.TEXT_MUTED);
        UIManager.put("FormattedTextField.background", Theme.BG_CARD2);
        UIManager.put("FormattedTextField.foreground", Theme.TEXT_PRIMARY);
        UIManager.put("TextArea.background", Theme.BG_CARD2);
        UIManager.put("TextArea.foreground", Theme.TEXT_PRIMARY);
        UIManager.put("TextArea.caretForeground", Theme.TEXT_PRIMARY);
        UIManager.put("TextArea.selectionBackground", Theme.ACCENT2);
        UIManager.put("TextArea.selectionForeground", Theme.TEXT_PRIMARY);
        UIManager.put("PasswordField.background", Theme.BG_CARD2);
        UIManager.put("PasswordField.foreground", Theme.TEXT_PRIMARY);
        UIManager.put("PasswordField.caretForeground", Theme.TEXT_PRIMARY);
        UIManager.put("PasswordField.selectionBackground", Theme.ACCENT2);
        UIManager.put("PasswordField.selectionForeground", Theme.TEXT_PRIMARY);
        UIManager.put("PasswordField.inactiveForeground", Theme.TEXT_MUTED);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sharosphere");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBackground(Theme.BG_DARK);

            // Fullscreen
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setMinimumSize(new Dimension(900, 620));

            frame.setVisible(true);

            // Show splash, then login
            SplashScreen splash = new SplashScreen();
            splash.showAndProceed(frame);
        });
    }
}
