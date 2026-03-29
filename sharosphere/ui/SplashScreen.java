package sharosphere.ui;

import sharosphere.util.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class SplashScreen {

    private JWindow window;
    private JProgressBar progressBar;
    private JLabel loadingLabel;

    public SplashScreen() {
        window = new JWindow();
    }

    public void showAndProceed(JFrame mainFrame) {
        // Root panel with dark bg + rounded border
        JPanel root = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.BG_DARK);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
                g2.setColor(Theme.ACCENT);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
                g2.dispose();
            }
        };
        root.setOpaque(false);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(40, 50, 36, 50));

        // Logo
        JLabel logo = new JLabel("\uD83D\uDD17", SwingConstants.CENTER); // 🔗
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logo.setForeground(Theme.TEXT_PRIMARY);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // App name
        JLabel appName = new JLabel("Sharosphere", SwingConstants.CENTER);
        appName.setFont(Theme.fontBold(30));
        appName.setForeground(Theme.TEXT_PRIMARY);
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tagline
        JLabel tagline = new JLabel("Campus Resource Exchange Platform", SwingConstants.CENTER);
        tagline.setFont(Theme.fontItalic(12));
        tagline.setForeground(Theme.TEXT_SECONDARY);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Progress bar
        progressBar = new JProgressBar(0, 100) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // track
                g2.setColor(Theme.BG_CARD2);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                // fill
                int fillW = (int) (getWidth() * getValue() / 100.0);
                if (fillW > 0) {
                    GradientPaint gp = new GradientPaint(0, 0, Theme.ACCENT2, fillW, 0, Theme.ACCENT);
                    g2.setPaint(gp);
                    g2.fillRoundRect(0, 0, fillW, getHeight(), 8, 8);
                }
                g2.dispose();
            }
        };
        progressBar.setPreferredSize(new Dimension(360, 6));
        progressBar.setMaximumSize(new Dimension(360, 6));
        progressBar.setOpaque(false);
        progressBar.setBorderPainted(false);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Loading label
        loadingLabel = new JLabel("Initializing...", SwingConstants.CENTER);
        loadingLabel.setFont(Theme.fontPlain(10));
        loadingLabel.setForeground(Theme.TEXT_MUTED);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(logo);
        root.add(Theme.vSpacer(12));
        root.add(appName);
        root.add(Theme.vSpacer(4));
        root.add(tagline);
        root.add(Theme.vSpacer(28));
        root.add(progressBar);
        root.add(Theme.vSpacer(8));
        root.add(loadingLabel);

        window.setContentPane(root);
        window.setSize(480, 300);
        window.setShape(new RoundRectangle2D.Double(0, 0, 480, 300, 40, 40));
        window.setLocationRelativeTo(null);
        window.setBackground(new Color(0, 0, 0, 0));
        window.setVisible(true);

        // Animate with Timer
        Timer timer = new Timer(25, null);
        final int[] step = {0};
        timer.addActionListener(e -> {
            step[0]++;
            progressBar.setValue(step[0]);
            if (step[0] < 40) loadingLabel.setText("Initializing...");
            else if (step[0] < 75) loadingLabel.setText("Loading resources...");
            else loadingLabel.setText("Almost ready...");

            if (step[0] >= 100) {
                timer.stop();
                Timer pause = new Timer(400, ev -> {
                    window.setVisible(false);
                    window.dispose();
                    new LoginScreen(mainFrame).show();
                });
                pause.setRepeats(false);
                pause.start();
            }
        });
        timer.start();
    }
}
