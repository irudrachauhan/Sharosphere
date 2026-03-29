package sharosphere.ui;

import sharosphere.model.Item;
import sharosphere.util.Theme;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class ListItemPanel {

    private List<Item> items;
    private Runnable onSuccess;

    public ListItemPanel(List<Item> items, Runnable onSuccess) {
        this.items = items;
        this.onSuccess = onSuccess;
    }

    public JPanel build() {
        // Outer wrapper
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Theme.BG_DARK);
        wrapper.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Card
        Theme.CardPanel card = new Theme.CardPanel(14);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(700, 500));
        card.setMaximumSize(new Dimension(720, Integer.MAX_VALUE));

        // Title bar
        JPanel titleBar = new JPanel();
        titleBar.setLayout(new BoxLayout(titleBar, BoxLayout.Y_AXIS));
        titleBar.setOpaque(false);
        titleBar.setBorder(BorderFactory.createEmptyBorder(28, 36, 16, 36));
        titleBar.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("List a New Item");
        title.setFont(Theme.fontBold(22));
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Share or sell your academic resources");
        sub.setFont(Theme.fontPlain(13));
        sub.setForeground(Theme.TEXT_SECONDARY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        titleBar.add(title);
        titleBar.add(Theme.vSpacer(4));
        titleBar.add(sub);
        card.add(titleBar);

        // Form
        JPanel formWrapper = new JPanel(new GridBagLayout());
        formWrapper.setOpaque(false);
        formWrapper.setBorder(BorderFactory.createEmptyBorder(0, 36, 20, 36));
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0: Item Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formWrapper.add(fieldLabel("Item Title *"), gbc);

        JTextField titleField = LoginScreen.makeField("e.g. Thermodynamics Textbook by Cengel");
        titleField.setPreferredSize(new Dimension(380, 36));
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 3; gbc.weightx = 1;
        formWrapper.add(titleField, gbc);
        gbc.gridwidth = 1;

        // Row 1: Category + Type
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formWrapper.add(fieldLabel("Category *"), gbc);

        JComboBox<String> catBox = new JComboBox<>(new String[]{
            "Textbooks", "CAD Tools", "Lab Equipment", "Calculators", "Stationery", "Other"
        });
        Theme.styleCombo(catBox);
        catBox.setPreferredSize(new Dimension(180, 36));
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1;
        formWrapper.add(catBox, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0;
        formWrapper.add(fieldLabel("Type *"), gbc);

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Paid", "Free"});
        Theme.styleCombo(typeBox);
        typeBox.setPreferredSize(new Dimension(120, 36));
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 1;
        formWrapper.add(typeBox, gbc);

        // Row 2: Price + Condition
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formWrapper.add(fieldLabel("Price (\u20B9) *"), gbc); // ₹

        JTextField priceField = LoginScreen.makeField("Enter price (0 for free)");
        priceField.setPreferredSize(new Dimension(180, 36));
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1;
        formWrapper.add(priceField, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0;
        formWrapper.add(fieldLabel("Condition *"), gbc);

        JComboBox<String> condBox = new JComboBox<>(new String[]{"New", "Like New", "Good", "Fair", "Poor"});
        Theme.styleCombo(condBox);
        condBox.setPreferredSize(new Dimension(120, 36));
        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 1;
        formWrapper.add(condBox, gbc);

        // Toggle price field
        typeBox.addActionListener(e -> {
            boolean isFree = "Free".equals(typeBox.getSelectedItem());
            priceField.setEnabled(!isFree);
            if (isFree) priceField.setText("0");
        });

        // Row 3: College + Contact
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formWrapper.add(fieldLabel("Your College *"), gbc);

        JTextField collegeField = LoginScreen.makeField("Enter your college name");
        collegeField.setPreferredSize(new Dimension(180, 36));
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1;
        formWrapper.add(collegeField, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0;
        formWrapper.add(fieldLabel("Contact No. *"), gbc);

        JTextField contactField = LoginScreen.makeField("10-digit number");
        contactField.setPreferredSize(new Dimension(120, 36));
        gbc.gridx = 3; gbc.gridy = 3; gbc.weightx = 1;
        formWrapper.add(contactField, gbc);

        // Row 4: Description
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0; gbc.anchor = GridBagConstraints.NORTHWEST;
        formWrapper.add(fieldLabel("Description *"), gbc);
        gbc.anchor = GridBagConstraints.WEST;

        JTextArea descArea = new JTextArea(4, 30);
        Theme.styleTextArea(descArea);
        descArea.setRows(3);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBorder(new Theme.RoundedBorder(6, Theme.BORDER, Theme.BG_CARD2));
        descScroll.setBackground(Theme.BG_CARD2);
        descScroll.getViewport().setBackground(Theme.BG_CARD2);
        descScroll.setPreferredSize(new Dimension(380, 80));
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 3; gbc.weightx = 1;
        formWrapper.add(descScroll, gbc);
        gbc.gridwidth = 1;

        card.add(formWrapper);

        // Bottom buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 36, 20, 36));
        bottom.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(Theme.fontPlain(13));
        cancelBtn.setForeground(Theme.TEXT_SECONDARY);
        cancelBtn.setBackground(Theme.BORDER);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorder(new Theme.RoundedBorder(8, Theme.BORDER, Theme.BG_CARD2));
        cancelBtn.setPreferredSize(new Dimension(100, 38));
        cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelBtn.setBackground(new Color(60, 70, 90)); // ⭐ visible color
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setOpaque(true);
        cancelBtn.setContentAreaFilled(true);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(e -> onSuccess.run());

        Theme.GradientButton submitBtn = Theme.gradientButton("\uD83D\uDCCB  List Item");
        submitBtn.setPreferredSize(new Dimension(150, 38));
        submitBtn.addActionListener(e -> {
            String t = getFieldText(titleField, "e.g. Thermodynamics Textbook by Cengel");
            String d = descArea.getText().trim();
            String collegeName = getFieldText(collegeField, "Enter your college name");
            String ct = getFieldText(contactField, "10-digit number");

            if (t.isEmpty() || d.isEmpty() || collegeName.isEmpty() || ct.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all required fields.",
                    "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price = 0;
            String priceText = getFieldText(priceField, "Enter price (0 for free)");
            try { price = Double.parseDouble(priceText); } catch (NumberFormatException ex) { /* 0 */ }

            String catSel = (String) catBox.getSelectedItem();
            Item.Category category;
            switch (catSel != null ? catSel : "Other") {
                case "Textbooks":     category = Item.Category.TEXTBOOKS; break;
                case "CAD Tools":     category = Item.Category.CAD_TOOLS; break;
                case "Lab Equipment": category = Item.Category.LAB_EQUIPMENT; break;
                case "Calculators":   category = Item.Category.CALCULATORS; break;
                case "Stationery":    category = Item.Category.STATIONERY; break;
                default:              category = Item.Category.OTHER;
            }

            Item.Type tp = "Free".equals(typeBox.getSelectedItem()) ? Item.Type.FREE : Item.Type.PAID;
            String cond = condBox.getSelectedItem() != null ? (String) condBox.getSelectedItem() : "Good";

            items.add(new Item(t, d, category, tp, price, "You", collegeName, ct, cond));

            JOptionPane.showMessageDialog(null, "Item listed successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            onSuccess.run();
        });

        bottom.add(cancelBtn);
        bottom.add(submitBtn);
        card.add(bottom);

        wrapper.add(card);
        return wrapper;
    }

    private String getFieldText(JTextField tf, String placeholder) {
        if (Boolean.TRUE.equals(tf.getClientProperty("placeholderActive"))) return "";
        return tf.getText().trim();
    }

    private JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.fontBold(11));
        l.setForeground(Theme.TEXT_SECONDARY);
        return l;
    }
}
