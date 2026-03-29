package sharosphere.ui;

import sharosphere.util.Theme;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterScreen {

    private JFrame frame;

    public RegisterScreen(JFrame frame) {
        this.frame = frame;
    }

    public void show() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BG_DARK);

        // ── Header bar ────────────────────────────────────────────
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 14));
        header.setBackground(Theme.BG_CARD);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER));
        JLabel logoLabel = new JLabel("\uD83D\uDD17 Sharosphere"); // 🔗
        logoLabel.setFont(Theme.fontBold(18));
        logoLabel.setForeground(Theme.TEXT_PRIMARY);
        header.add(logoLabel);
        root.add(header, BorderLayout.NORTH);

        // ── Center scrollable card ─────────────────────────────────
        Theme.CardPanel card = new Theme.CardPanel(14);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(700, 600));
        card.setMaximumSize(new Dimension(740, Integer.MAX_VALUE));

        // Title
        JPanel titleSection = new JPanel();
        titleSection.setLayout(new BoxLayout(titleSection, BoxLayout.Y_AXIS));
        titleSection.setOpaque(false);
        titleSection.setBorder(BorderFactory.createEmptyBorder(32, 40, 20, 40));
        titleSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Join Sharosphere");
        title.setFont(Theme.fontBold(26));
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Create your campus account to start sharing resources");
        sub.setFont(Theme.fontPlain(13));
        sub.setForeground(Theme.TEXT_SECONDARY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        titleSection.add(title);
        titleSection.add(Theme.vSpacer(4));
        titleSection.add(sub);
        card.add(titleSection);

        // Form grid
        JPanel formWrapper = new JPanel();
        formWrapper.setOpaque(false);
        formWrapper.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        formWrapper.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 6, 4, 6);

        String[] labels = {
            "Full Name", "Student ID", "College Name", "Department",
            "Year of Study", "Email Address", "Phone Number", "Password", "Confirm Password"
        };
        String[] hints = {
            "Your full legal name", "e.g. 2021CSE001", "Your institution name", "e.g. Computer Science",
            "Select year", "college@email.com", "10-digit mobile number", "Min 8 characters", "Re-enter password"
        };

        for (int i = 0; i < labels.length; i++) {
            int col = (i % 2) * 2;
            int row = i / 2;

            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(Theme.fontBold(11));
            lbl.setForeground(Theme.TEXT_SECONDARY);
            gbc.gridx = col; gbc.gridy = row * 2; gbc.weightx = 0;
            formWrapper.add(lbl, gbc);

            JComponent field;
            if (labels[i].contains("Password")) {
                JPasswordField pf = LoginScreen.makePasswordField(hints[i]);
                pf.setPreferredSize(new Dimension(260, 36));
                field = pf;
            } else if (labels[i].equals("Year of Study")) {
                JComboBox<String> cb = new JComboBox<>(new String[]{
                    "Select year", "1st Year", "2nd Year", "3rd Year", "4th Year", "5th Year"
                });
                Theme.styleCombo(cb);
                cb.setPreferredSize(new Dimension(260, 36));
                field = cb;
            } else {
                JTextField tf = LoginScreen.makeField(hints[i]);
                tf.setPreferredSize(new Dimension(260, 36));
                field = tf;
            }

            gbc.gridx = col + 1; gbc.gridy = row * 2; gbc.weightx = 1;
            formWrapper.add(field, gbc);
        }

        card.add(formWrapper);

        // Buttons
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));
        bottom.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel backLink = new JLabel("← Back to Login");
        backLink.setFont(Theme.fontPlain(12));
        backLink.setForeground(Theme.ACCENT);
        backLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { new LoginScreen(frame).show(); }
        });

        Theme.GradientButton regBtn = Theme.gradientButton("Create Account");
        regBtn.setPreferredSize(new Dimension(180, 40));
        regBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Account created! Please login.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            new LoginScreen(frame).show();
        });

        bottom.add(backLink, BorderLayout.WEST);
        bottom.add(regBtn, BorderLayout.EAST);
        card.add(bottom);

        // Wrap card in centered panel
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Theme.BG_DARK);
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        center.add(card);

        JScrollPane scroll = new JScrollPane(center);
        scroll.setBackground(Theme.BG_DARK);
        scroll.getViewport().setBackground(Theme.BG_DARK);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        root.add(scroll, BorderLayout.CENTER);

        frame.add(root, BorderLayout.CENTER);
        frame.setTitle("Sharosphere — Register");
        frame.revalidate();
        frame.repaint();
    }
}
