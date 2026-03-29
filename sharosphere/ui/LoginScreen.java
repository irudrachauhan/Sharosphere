package sharosphere.ui;

import sharosphere.util.Theme;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen {

    private JFrame frame;

    public LoginScreen(JFrame frame) {
        this.frame = frame;
    }

    public void show() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(Theme.BG_DARK);

        // ── LEFT PANEL (branding) ─────────────────────────────────
        JPanel leftPanel = buildLeftPanel();
        leftPanel.setPreferredSize(new Dimension(400, 0));

        // ── RIGHT PANEL (login form) ──────────────────────────────
        JPanel rightPanel = buildRightPanel();

        root.add(leftPanel);
        root.add(rightPanel);

        frame.add(root, BorderLayout.CENTER);
        frame.setTitle("Sharosphere — Login");
        frame.revalidate();
        frame.repaint();
    }

    private JPanel buildLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Theme.BG_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER),
            BorderFactory.createEmptyBorder(60, 50, 30, 50)
        ));

        // Logo + app name
        JLabel logo = new JLabel("\uD83D\uDD17"); // 🔗
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
        logo.setForeground(Theme.TEXT_PRIMARY);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel appName = new JLabel("Sharosphere");
        appName.setFont(Theme.fontBold(36));
        appName.setForeground(Theme.TEXT_PRIMARY);
        appName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tagline = new JLabel("Campus Resource Exchange");
        tagline.setFont(Theme.fontItalic(14));
        tagline.setForeground(Theme.ACCENT);
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(logo);
        panel.add(Theme.vSpacer(16));
        panel.add(appName);
        panel.add(Theme.vSpacer(6));
        panel.add(tagline);
        panel.add(Theme.vSpacer(40));

        // Feature bullets
        String[][] feats = {
            {"\uD83D\uDCDA", "Textbooks & Study Materials"},   // 📚
            {"\uD83D\uDCD0", "CAD Tools & Engineering Kits"},  // 📐
            {"\uD83D\uDD2C", "Lab Equipment & Calculators"},   // 🔬
            {"\uD83E\uDD1D", "Free & Paid Listings"},           // 🤝
        };
        for (String[] f : feats) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            row.setBackground(Theme.BG_CARD);
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

            JLabel icon = new JLabel(f[0]);
            icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            JLabel text = new JLabel(f[1]);
            text.setFont(Theme.fontPlain(13));
            text.setForeground(Theme.TEXT_SECONDARY);
            row.add(icon);
            row.add(text);
            panel.add(row);
            panel.add(Theme.vSpacer(6));
        }

        // push version to bottom
        panel.add(Box.createVerticalGlue());

        JLabel ver = new JLabel("v1.0.0  ·  Made for Students");
        ver.setFont(Theme.fontPlain(11));
        ver.setForeground(Theme.TEXT_MUTED);
        ver.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(ver);

        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Theme.BG_DARK);

        Theme.CardPanel formCard = new Theme.CardPanel(14);
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formCard.setPreferredSize(new Dimension(380, 480));
        formCard.setMaximumSize(new Dimension(420, Integer.MAX_VALUE));

        JLabel title = new JLabel("Welcome Back");
        title.setFont(Theme.fontBold(24));
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Sign in to your campus account");
        sub.setFont(Theme.fontPlain(13));
        sub.setForeground(Theme.TEXT_SECONDARY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        formCard.add(title);
        formCard.add(Theme.vSpacer(6));
        formCard.add(sub);
        formCard.add(Theme.vSpacer(28));

        // Email
        JLabel emailLbl = new JLabel("College Email");
        emailLbl.setFont(Theme.fontBold(12));
        emailLbl.setForeground(Theme.TEXT_SECONDARY);
        emailLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField emailField = new JTextField();
        setPlaceholder(emailField, "you@college.edu");
        Theme.styleField(emailField);
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        // Password
        JLabel passLbl = new JLabel("Password");
        passLbl.setFont(Theme.fontBold(12));
        passLbl.setForeground(Theme.TEXT_SECONDARY);
        passLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField passField = new JPasswordField();
        setPlaceholderPwd(passField, "Enter password");
        Theme.styleField(passField);
        passField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        // Forgot password (right aligned)
        JPanel forgotRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        forgotRow.setBackground(Theme.BG_CARD);
        forgotRow.setOpaque(false);
        forgotRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        forgotRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        JLabel forgot = new JLabel("Forgot password?");
        forgot.setFont(Theme.fontPlain(12));
        forgot.setForeground(Theme.ACCENT);
        forgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotRow.add(forgot);

        // Login button
        Theme.GradientButton loginBtn = Theme.gradientButton("Sign In");
        loginBtn.setPreferredSize(new Dimension(300, 42));
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        loginBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginBtn.addActionListener(e -> handleLogin(emailField, passField));

        // Separator
        JSeparator sep = new JSeparator();
        sep.setForeground(Theme.BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // Sign up row
        JPanel signupRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        signupRow.setOpaque(false);
        signupRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel newUser = new JLabel("New student?");
        newUser.setFont(Theme.fontPlain(12));
        newUser.setForeground(Theme.TEXT_SECONDARY);
        JLabel createAcc = new JLabel("Create Account");
        createAcc.setFont(Theme.fontBold(12));
        createAcc.setForeground(Theme.ACCENT);
        createAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createAcc.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { new RegisterScreen(frame).show(); }
        });
        signupRow.add(newUser);
        signupRow.add(createAcc);

        formCard.add(emailLbl);
        formCard.add(Theme.vSpacer(6));
        formCard.add(emailField);
        formCard.add(Theme.vSpacer(16));
        formCard.add(passLbl);
        formCard.add(Theme.vSpacer(6));
        formCard.add(passField);
        formCard.add(Theme.vSpacer(6));
        formCard.add(forgotRow);
        formCard.add(Theme.vSpacer(24));
        formCard.add(loginBtn);
        formCard.add(Theme.vSpacer(20));
        formCard.add(sep);
        formCard.add(Theme.vSpacer(20));
        formCard.add(signupRow);

        wrapper.add(formCard);
        return wrapper;
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
    private void handleLogin(JTextField email, JPasswordField pass) {
        boolean emailIsPlaceholder = Boolean.TRUE.equals(email.getClientProperty("placeholderActive"));
        boolean passIsPlaceholder  = Boolean.TRUE.equals(pass.getClientProperty("placeholderActive"));
        String e = emailIsPlaceholder ? "" : email.getText().trim();
        String p = passIsPlaceholder  ? "" : new String(pass.getPassword());
        if (e.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.",
                "Login Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!isValidEmail(e)) {
            JOptionPane.showMessageDialog(frame,
                    "Enter a valid email (must contain @ and .)",
                    "Invalid Email",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        new MainDashboard(frame, e).show();
    }

    // ── Static helper styles (used by other classes) ──────────────

    public static void setPlaceholder(JTextField tf, String placeholder) {
        // Use client property as flag so text comparison never interferes with real input
        tf.putClientProperty("placeholder", placeholder);
        tf.putClientProperty("placeholderActive", Boolean.TRUE);
        tf.setText(placeholder);
        tf.setForeground(Theme.TEXT_MUTED);
        tf.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (Boolean.TRUE.equals(tf.getClientProperty("placeholderActive"))) {
                    tf.putClientProperty("placeholderActive", Boolean.FALSE);
                    tf.setText("");
                    tf.setForeground(Theme.TEXT_PRIMARY);
                }
            }
            public void focusLost(FocusEvent e) {
                if (tf.getText().isEmpty()) {
                    tf.putClientProperty("placeholderActive", Boolean.TRUE);
                    tf.setText(placeholder);
                    tf.setForeground(Theme.TEXT_MUTED);
                }
            }
        });
    }

    public static void setPlaceholderPwd(JPasswordField pf, String placeholder) {
        pf.putClientProperty("placeholder", placeholder);
        pf.putClientProperty("placeholderActive", Boolean.TRUE);
        pf.setEchoChar((char) 0);
        pf.setText(placeholder);
        pf.setForeground(Theme.TEXT_MUTED);
        pf.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (Boolean.TRUE.equals(pf.getClientProperty("placeholderActive"))) {
                    pf.putClientProperty("placeholderActive", Boolean.FALSE);
                    pf.setText("");
                    pf.setForeground(Theme.TEXT_PRIMARY);
                    pf.setEchoChar('\u2022');
                }
            }
            public void focusLost(FocusEvent e) {
                if (new String(pf.getPassword()).isEmpty()) {
                    pf.putClientProperty("placeholderActive", Boolean.TRUE);
                    pf.setEchoChar((char) 0);
                    pf.setText(placeholder);
                    pf.setForeground(Theme.TEXT_MUTED);
                }
            }
        });
    }

    /** Styled field for other screens */
    public static JTextField makeField(String placeholder) {
        JTextField tf = new JTextField();
        Theme.styleField(tf);
        setPlaceholder(tf, placeholder);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        return tf;
    }

    public static JPasswordField makePasswordField(String placeholder) {
        JPasswordField pf = new JPasswordField();
        Theme.styleField(pf);
        setPlaceholderPwd(pf, placeholder);
        pf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        return pf;
    }
}
