package sharosphere.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Theme {

    // Colors matching the JavaFX originals exactly
    public static final Color BG_DARK        = new Color(13, 17, 27);
    public static final Color BG_CARD        = new Color(22, 28, 44);
    public static final Color BG_CARD2       = new Color(28, 36, 56);
    public static final Color ACCENT         = new Color(56, 189, 248);   // sky blue
    public static final Color ACCENT2        = new Color(99, 102, 241);   // indigo
    public static final Color SUCCESS        = new Color(52, 211, 153);
    public static final Color WARNING        = new Color(251, 191, 36);
    public static final Color DANGER         = new Color(248, 113, 113);
    public static final Color TEXT_PRIMARY   = new Color(241, 245, 249);
    public static final Color TEXT_SECONDARY = new Color(148, 163, 184);
    public static final Color TEXT_MUTED     = new Color(71, 85, 105);
    public static final Color BORDER         = new Color(30, 41, 59);

    // Gradient accent start/end
    public static final Color GRADIENT_START = ACCENT2; // indigo #6366f1
    public static final Color GRADIENT_END   = ACCENT;  // sky   #38bdf8

    // Fonts
    public static Font fontBold(int size) {
        return new Font("Segoe UI", Font.BOLD, size);
    }
    public static Font fontPlain(int size) {
        return new Font("Segoe UI", Font.PLAIN, size);
    }
    public static Font fontItalic(int size) {
        return new Font("Segoe UI", Font.ITALIC, size);
    }

    /** Rounded card border */
    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
            new RoundedBorder(14, BORDER, BG_CARD),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        );
    }

    /** Apply common dark background to any panel */
    public static void applyDarkBg(JComponent c) {
        c.setBackground(BG_DARK);
        c.setOpaque(true);
    }

    /** Styled text field */
    public static void styleField(JTextField tf) {
        tf.setBackground(BG_CARD2);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(TEXT_PRIMARY);
        tf.setSelectionColor(ACCENT2);
        tf.setSelectedTextColor(TEXT_PRIMARY);
        tf.setFont(fontPlain(13));
        tf.setOpaque(true);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(7, 11, 7, 11)
        ));
    }

    /** Styled text area */
    public static void styleTextArea(JTextArea ta) {
        ta.setBackground(BG_CARD2);
        ta.setForeground(TEXT_PRIMARY);
        ta.setCaretColor(TEXT_PRIMARY);
        ta.setSelectionColor(ACCENT2);
        ta.setSelectedTextColor(TEXT_PRIMARY);
        ta.setFont(fontPlain(12));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setOpaque(true);
        ta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(7, 11, 7, 11)
        ));
    }

    /** Styled combo box */
    public static void styleCombo(JComboBox<?> cb) {
        cb.setBackground(BG_CARD2);
        cb.setForeground(TEXT_PRIMARY);
        cb.setFont(fontPlain(12));
        cb.setBorder(new RoundedBorder(6, BORDER, BG_CARD2));
        cb.setOpaque(true);
        cb.setFocusable(false);

        // Renderer (dropdown list)
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                setFont(fontPlain(12));
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

                if (isSelected) {
                    setBackground(BG_CARD);
                    setForeground(TEXT_PRIMARY);
                } else {
                    setBackground(BG_CARD2);
                    setForeground(TEXT_PRIMARY);
                }

                if (index == -1) {
                    setBackground(BG_CARD2);
                    setForeground(TEXT_PRIMARY);
                }

                return this;
            }
        });
        cb.setEditable(true);
        Component editor = cb.getEditor().getEditorComponent();
        if (editor instanceof JTextField) {
            JTextField tf = (JTextField) editor;
            tf.setBackground(BG_CARD2);
            tf.setForeground(TEXT_PRIMARY);
            tf.setCaretColor(TEXT_PRIMARY);
            tf.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        }
    }
    /** Gradient button painter helper — returns a panel acting as a button */
    public static GradientButton gradientButton(String text) {
        return new GradientButton(text);
    }

    // ── Inner helper classes ──────────────────────────────────────

    /** Rounded border with fill color */
    public static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color borderColor;
        private final Color fillColor;

        public RoundedBorder(int radius, Color borderColor, Color fillColor) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.fillColor = fillColor;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // fill background inside border
            if (fillColor != null) {
                g2.setColor(fillColor);
                g2.fillRoundRect(x, y, width - 1, height - 1, radius * 2, radius * 2);
            }
            g2.setColor(borderColor);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius * 2, radius * 2);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius, radius / 2, radius);
        }

        @Override
        public boolean isBorderOpaque() { return false; }
    }

    /** Card panel with rounded corners and card background */
    public static class CardPanel extends JPanel {
        private int radius;
        private Color bg;
        private Color border;

        public CardPanel(int radius) {
            this.radius = radius;
            this.bg = BG_CARD;
            this.border = BORDER;
            setOpaque(false);
        }

        public void setBgColor(Color c) { this.bg = c; }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius * 2, radius * 2);
            g2.setColor(border);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius * 2, radius * 2);
            g2.dispose();
        }
    }

    /** Gradient button (indigo → sky blue) */
    public static class GradientButton extends JButton {
        private boolean hovered = false;

        public GradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(fontBold(13));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { hovered = true; repaint(); }
                public void mouseExited(java.awt.event.MouseEvent e)  { hovered = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color c1 = hovered ? new Color(117, 120, 243) : GRADIENT_START;
            Color c2 = hovered ? new Color(96, 206, 249) : GRADIENT_END;
            GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), 0, c2);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Separator line */
    public static JPanel separator() {
        JPanel sep = new JPanel();
        sep.setBackground(BORDER);
        sep.setPreferredSize(new Dimension(0, 1));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    /** Vertical spacer */
    public static Component vSpacer(int height) {
        return Box.createRigidArea(new Dimension(0, height));
    }

    /** Horizontal spacer */
    public static Component hSpacer(int width) {
        return Box.createRigidArea(new Dimension(width, 0));
    }
}
