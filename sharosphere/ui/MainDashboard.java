package sharosphere.ui;

import sharosphere.model.Item;
import sharosphere.util.Theme;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MainDashboard {

    private JFrame frame;
    private String currentUser;
    private List<Item> items;

    private int activeNav = 0;
    private JPanel contentArea;
    private List<JPanel> navItems = new ArrayList<>();

    private static final String[] NAV_ICONS = {"\uD83C\uDFE0","\uD83D\uDED2","\uD83D\uDCCB","\u2795","\uD83D\uDC64","\u2699\uFE0F"};
    private static final String[] NAV_NAMES = {"Dashboard","Marketplace","My Listings","List Item","Profile","Settings"};

    public MainDashboard(JFrame frame, String userEmail) {
        this.frame = frame;
        this.currentUser = userEmail;
        this.items = generateSampleItems();
    }

    public void show() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BG_DARK);

        // Sidebar
        JPanel sidebar = buildSidebar();
        root.add(sidebar, BorderLayout.WEST);

        // Content area
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Theme.BG_DARK);
        root.add(contentArea, BorderLayout.CENTER);

        frame.add(root, BorderLayout.CENTER);
        frame.setTitle("Sharosphere");

        showPanel(0);

        frame.revalidate();
        frame.repaint();
    }

    // ── SIDEBAR ───────────────────────────────────────────────────

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Theme.BG_CARD);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER));
        sidebar.setPreferredSize(new Dimension(200, 0));

        // Logo row
        JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        logoRow.setBackground(Theme.BG_CARD);
        logoRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        JLabel logoIcon = new JLabel("\uD83D\uDD17"); // 🔗
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        JLabel appName = new JLabel("Sharosphere");
        appName.setFont(Theme.fontBold(16));
        appName.setForeground(Theme.TEXT_PRIMARY);
        logoRow.add(logoIcon);
        logoRow.add(appName);
        sidebar.add(logoRow);

        // Nav items
        for (int i = 0; i < NAV_NAMES.length; i++) {
            JPanel navItem = buildNavItem(i);
            navItems.add(navItem);
            final int idx = i;
            navItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            navItem.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) { showPanel(idx); }
            });
            sidebar.add(navItem);
        }

        // Spacer
        sidebar.add(Box.createVerticalGlue());

        // User chip at bottom
        JPanel userChip = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 14));
        userChip.setBackground(Theme.BG_CARD);
        userChip.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        userChip.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        JLabel avatar = new JLabel("\uD83D\uDC64"); // 👤
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        String display = currentUser.length() > 18 ? currentUser.substring(0, 16) + "…" : currentUser;
        JLabel uLabel = new JLabel(display);
        uLabel.setFont(Theme.fontPlain(11));
        uLabel.setForeground(Theme.TEXT_SECONDARY);
        userChip.add(avatar);
        userChip.add(uLabel);
        sidebar.add(userChip);

        return sidebar;
    }

    private JPanel buildNavItem(int idx) {
        boolean active = (idx == activeNav);

        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getBackground().equals(new Color(56, 189, 248, 20))) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(Theme.ACCENT);
                    g2.fillRect(0, 0, 3, getHeight());
                    g2.dispose();
                }
            }
        };
        wrapper.setOpaque(true);
        applyNavStyle(wrapper, active);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        row.setOpaque(false);

        JLabel icon = new JLabel(NAV_ICONS[idx]);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        JLabel name = new JLabel(NAV_NAMES[idx]);
        name.setFont(active ? Theme.fontBold(13) : Theme.fontPlain(13));
        name.setForeground(active ? Theme.ACCENT : Theme.TEXT_SECONDARY);

        row.add(icon);
        row.add(name);
        wrapper.add(row, BorderLayout.CENTER);

        // left accent bar panel
        JPanel accent = new JPanel();
        accent.setPreferredSize(new Dimension(3, 0));
        accent.setBackground(active ? Theme.ACCENT : new Color(0, 0, 0, 0));
        accent.setOpaque(active);
        wrapper.add(accent, BorderLayout.WEST);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        // Hover
        wrapper.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (idx != activeNav) {
                    wrapper.setBackground(new Color(56, 189, 248, 10));
                }
            }
            public void mouseExited(MouseEvent e) {
                if (idx != activeNav) applyNavStyle(wrapper, false);
            }
        });

        return wrapper;
    }

    private void applyNavStyle(JPanel wrapper, boolean active) {
        wrapper.setBackground(active ? new Color(56, 189, 248, 20) : Theme.BG_CARD);
    }

    private void refreshNavHighlights() {
        for (int i = 0; i < navItems.size(); i++) {
            JPanel wrapper = navItems.get(i);
            boolean active = (i == activeNav);
            applyNavStyle(wrapper, active);

            // accent bar
            Component west = ((BorderLayout)wrapper.getLayout()).getLayoutComponent(BorderLayout.WEST);
            if (west instanceof JPanel) {
                ((JPanel) west).setBackground(active ? Theme.ACCENT : Theme.BG_CARD);
                ((JPanel) west).setOpaque(active);
            }

            // name label
            JPanel row = (JPanel) ((BorderLayout)wrapper.getLayout()).getLayoutComponent(BorderLayout.CENTER);
            if (row != null) {
                for (Component c : row.getComponents()) {
                    if (c instanceof JLabel && !((JLabel)c).getText().equals(NAV_ICONS[i])) {
                        JLabel lbl = (JLabel) c;
                        lbl.setFont(active ? Theme.fontBold(13) : Theme.fontPlain(13));
                        lbl.setForeground(active ? Theme.ACCENT : Theme.TEXT_SECONDARY);
                    }
                }
            }
            wrapper.repaint();
        }
    }

    private void showPanel(int idx) {
        activeNav = idx;
        refreshNavHighlights();
        contentArea.removeAll();

        JComponent panel;
        switch (idx) {
            case 0: panel = buildDashboardPanel(); break;
            case 1: panel = buildMarketplacePanel(); break;
            case 2: panel = buildMyListingsPanel(); break;
            case 3: panel = new ListItemPanel(items, () -> showPanel(2)).build(); break;
            case 4: panel = buildProfilePanel(); break;
            case 5: panel = buildSettingsPanel(); break;
            default: panel = buildDashboardPanel();
        }

        contentArea.add(panel, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }

    // ── DASHBOARD PANEL ───────────────────────────────────────────

    private JComponent buildDashboardPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Theme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        // Header row
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel greetCol = new JPanel();
        greetCol.setLayout(new BoxLayout(greetCol, BoxLayout.Y_AXIS));
        greetCol.setOpaque(false);
        JLabel greet = new JLabel("Welcome back! \uD83D\uDC4B"); // 👋
        greet.setFont(Theme.fontBold(24));
        greet.setForeground(Theme.TEXT_PRIMARY);
        JLabel date = new JLabel("Campus Marketplace · Today");
        date.setFont(Theme.fontPlain(13));
        date.setForeground(Theme.TEXT_SECONDARY);
        greetCol.add(greet);
        greetCol.add(date);

        Theme.GradientButton listBtn = Theme.gradientButton("+ List Item");
        listBtn.setPreferredSize(new Dimension(140, 38));
        listBtn.addActionListener(e -> showPanel(3));

        header.add(greetCol, BorderLayout.WEST);
        header.add(listBtn, BorderLayout.EAST);
        p.add(header);
        p.add(Theme.vSpacer(24));

        // Stats row
        long free = items.stream().filter(Item::isFree).count();
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        statsRow.add(statCard("\uD83D\uDCE6", "Total Listings", String.valueOf(items.size()), Theme.ACCENT));
        statsRow.add(statCard("\uD83C\uDF81", "Free Items", String.valueOf(free), Theme.SUCCESS));
        statsRow.add(statCard("\uD83D\uDCB0", "Paid Items", String.valueOf(items.size() - free), Theme.WARNING));
        statsRow.add(statCard("\uD83C\uDFDB\uFE0F", "Colleges", "3", Theme.ACCENT2));
        p.add(statsRow);
        p.add(Theme.vSpacer(20));

        // Two-column body
        JPanel body = new JPanel(new GridLayout(1, 2, 20, 0));
        body.setOpaque(false);

        // Recent listings card
        Theme.CardPanel recentCard = new Theme.CardPanel(14);
        recentCard.setLayout(new BoxLayout(recentCard, BoxLayout.Y_AXIS));
        recentCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel recentTitle = new JLabel("Recent Listings");
        recentTitle.setFont(Theme.fontBold(15));
        recentTitle.setForeground(Theme.TEXT_PRIMARY);
        recentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentCard.add(recentTitle);
        recentCard.add(Theme.vSpacer(12));
        int count = Math.min(4, items.size());
        for (int i = 0; i < count; i++) {
            recentCard.add(buildMiniItemRow(items.get(i)));
            recentCard.add(Theme.vSpacer(8));
        }

        // Categories card
        Theme.CardPanel catCard = new Theme.CardPanel(14);
        catCard.setLayout(new BoxLayout(catCard, BoxLayout.Y_AXIS));
        catCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel catTitle = new JLabel("Browse Categories");
        catTitle.setFont(Theme.fontBold(15));
        catTitle.setForeground(Theme.TEXT_PRIMARY);
        catTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        catCard.add(catTitle);
        catCard.add(Theme.vSpacer(12));

        String[][] cats = {
            {"\uD83D\uDCDA","Textbooks"},{"\uD83D\uDCD0","CAD Tools"},
            {"\uD83D\uDD2C","Lab Equipment"},{"\uD83E\uDDF2","Calculators"},
            {"\u270F\uFE0F","Stationery"},{"\uD83D\uDCE6","Other"}
        };
        JPanel catGrid = new JPanel(new GridLayout(3, 2, 12, 12));
        catGrid.setOpaque(false);
        catGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        for (String[] cat : cats) {
            catGrid.add(buildCategoryChip(cat[0], cat[1]));
        }
        catCard.add(catGrid);

        body.add(recentCard);
        body.add(catCard);
        p.add(body);

        JScrollPane scroll = new JScrollPane(p);
        scroll.setBackground(Theme.BG_DARK);
        scroll.getViewport().setBackground(Theme.BG_DARK);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private JPanel statCard(String icon, String label, String value, Color accentColor) {
        Theme.CardPanel card = new Theme.CardPanel(12);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        row.setOpaque(false);
        JLabel ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));

        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);
        JLabel val = new JLabel(value);
        val.setFont(Theme.fontBold(26));
        val.setForeground(accentColor);
        JLabel lbl = new JLabel(label);
        lbl.setFont(Theme.fontPlain(11));
        lbl.setForeground(Theme.TEXT_SECONDARY);
        col.add(val);
        col.add(lbl);

        row.add(ico);
        row.add(col);
        card.add(row, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildMiniItemRow(Item item) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel(item.getEmoji());
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        JLabel t = new JLabel(item.getTitle());
        t.setFont(Theme.fontBold(12));
        t.setForeground(Theme.TEXT_PRIMARY);
        JLabel s = new JLabel(item.getSeller());
        s.setFont(Theme.fontPlain(11));
        s.setForeground(Theme.TEXT_MUTED);
        info.add(t);
        info.add(s);

        JLabel price = new JLabel(item.isFree() ? "FREE" : "\u20B9 " + String.format("%.0f", item.getPrice()));
        price.setFont(Theme.fontBold(12));
        price.setForeground(item.isFree() ? Theme.SUCCESS : Theme.WARNING);

        row.add(icon, BorderLayout.WEST);
        row.add(info, BorderLayout.CENTER);
        row.add(price, BorderLayout.EAST);
        return row;
    }

    private JPanel buildCategoryChip(String icon, String name) {
        JPanel chip = new JPanel();
        chip.setLayout(new BoxLayout(chip, BoxLayout.Y_AXIS));
        chip.setBackground(Theme.BG_CARD2);
        chip.setBorder(BorderFactory.createCompoundBorder(
            new Theme.RoundedBorder(10, Theme.BORDER, Theme.BG_CARD2),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        chip.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel ico = new JLabel(icon, SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        ico.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel nm = new JLabel(name, SwingConstants.CENTER);
        nm.setFont(Theme.fontBold(11));
        nm.setForeground(Theme.TEXT_SECONDARY);
        nm.setAlignmentX(Component.CENTER_ALIGNMENT);
        chip.add(ico);
        chip.add(Theme.vSpacer(4));
        chip.add(nm);

        chip.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                chip.setBorder(BorderFactory.createCompoundBorder(
                    new Theme.RoundedBorder(10, Theme.ACCENT, Theme.BG_CARD2),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
                ));
                chip.repaint();
            }
            public void mouseExited(MouseEvent e) {
                chip.setBorder(BorderFactory.createCompoundBorder(
                    new Theme.RoundedBorder(10, Theme.BORDER, Theme.BG_CARD2),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
                ));
                chip.repaint();
            }
            public void mouseClicked(MouseEvent e) { showPanel(1); }
        });
        return chip;
    }

    // ── MARKETPLACE PANEL ─────────────────────────────────────────

    private JComponent buildMarketplacePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Theme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Top bar
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        topBar.setOpaque(false);
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        JLabel title = new JLabel("Marketplace");
        title.setFont(Theme.fontBold(22));
        title.setForeground(Theme.TEXT_PRIMARY);

        JTextField search = LoginScreen.makeField("\uD83D\uDD0D  Search items, books, tools...");
        search.setPreferredSize(new Dimension(280, 36));

        JComboBox<String> catFilter = new JComboBox<>(new String[]{
            "All Categories","Textbooks","CAD Tools","Lab Equipment","Calculators","Stationery","Other"
        });
        Theme.styleCombo(catFilter);
        catFilter.setPreferredSize(new Dimension(160, 36));

        JComboBox<String> typeFilter = new JComboBox<>(new String[]{"All Types","Free","Paid"});
        Theme.styleCombo(typeFilter);
        typeFilter.setPreferredSize(new Dimension(110, 36));

        topBar.add(title);
        topBar.add(Box.createHorizontalGlue());
        topBar.add(search);
        topBar.add(catFilter);
        topBar.add(typeFilter);
        p.add(topBar);
        p.add(Theme.vSpacer(20));

        // Items grid - using WrapLayout (custom FlowLayout that wraps)
        JPanel grid = new JPanel(new WrapLayout(FlowLayout.LEFT, 16, 16));
        grid.setBackground(Theme.BG_DARK);
        for (Item item : items) {
            grid.add(buildItemCard(item));
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBackground(Theme.BG_DARK);
        scroll.getViewport().setBackground(Theme.BG_DARK);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        p.add(scroll);

        JScrollPane outer = new JScrollPane(p);
        outer.setBackground(Theme.BG_DARK);
        outer.getViewport().setBackground(Theme.BG_DARK);
        outer.setBorder(null);
        outer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return outer;
    }

    private JPanel buildItemCard(Item item) {
        Theme.CardPanel card = new Theme.CardPanel(14);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        card.setPreferredSize(new Dimension(220, 200));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBgColor(Theme.BG_CARD2);
                card.repaint();
            }
            public void mouseExited(MouseEvent e) {
                card.setBgColor(Theme.BG_CARD);
                card.repaint();
            }
            public void mouseClicked(MouseEvent e) { showItemDetail(item); }
        });

        // Top: emoji + price badge
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        top.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel emojiLbl = new JLabel(item.getEmoji());
        emojiLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        JLabel badge = new JLabel(item.isFree() ? "FREE" : "\u20B9 " + String.format("%.0f", item.getPrice()));
        badge.setFont(Theme.fontBold(11));
        badge.setForeground(item.isFree() ? Theme.SUCCESS : Theme.WARNING);
        badge.setBackground(item.isFree() ? new Color(52, 211, 153, 38) : new Color(251, 191, 36, 38));
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        top.add(emojiLbl, BorderLayout.WEST);
        top.add(badge, BorderLayout.EAST);
        card.add(top);
        card.add(Theme.vSpacer(10));

        JLabel titleLbl = new JLabel("<html><body style='width:180px'>" + item.getTitle() + "</body></html>");
        titleLbl.setFont(Theme.fontBold(14));
        titleLbl.setForeground(Theme.TEXT_PRIMARY);
        titleLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLbl);
        card.add(Theme.vSpacer(4));

        JLabel descLbl = new JLabel("<html><body style='width:180px'>" + truncate(item.getDescription(), 80) + "</body></html>");
        descLbl.setFont(Theme.fontPlain(11));
        descLbl.setForeground(Theme.TEXT_SECONDARY);
        descLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(descLbl);
        card.add(Theme.vSpacer(6));

        JLabel condLbl = new JLabel("Condition: " + item.getCondition());
        condLbl.setFont(Theme.fontPlain(11));
        condLbl.setForeground(Theme.TEXT_MUTED);
        condLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(condLbl);
        card.add(Box.createVerticalGlue());

        // Bottom seller row
        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.setOpaque(false);
        bottomRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        JLabel seller = new JLabel("\uD83D\uDC64 " + item.getSeller());
        seller.setFont(Theme.fontPlain(11));
        seller.setForeground(Theme.TEXT_SECONDARY);
        String col = item.getCollege().length() > 20 ? item.getCollege().substring(0, 18) + "…" : item.getCollege();
        JLabel college = new JLabel(col);
        college.setFont(Theme.fontItalic(10));
        college.setForeground(Theme.TEXT_MUTED);
        bottomRow.add(seller, BorderLayout.WEST);
        bottomRow.add(college, BorderLayout.EAST);
        card.add(bottomRow);

        return card;
    }

    private void showItemDetail(Item item) {
        JDialog dialog = new JDialog(frame, item.getTitle(), true);
        dialog.setSize(460, 460);
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Theme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel ico = new JLabel(item.getEmoji(), SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        ico.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel ttl = new JLabel(item.getTitle(), SwingConstants.CENTER);
        ttl.setFont(Theme.fontBold(20));
        ttl.setForeground(Theme.TEXT_PRIMARY);
        ttl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel prc = new JLabel(item.isFree() ? "FREE \uD83C\uDF81" : "\u20B9 " + String.format("%.2f", item.getPrice()), SwingConstants.CENTER);
        prc.setFont(Theme.fontBold(18));
        prc.setForeground(item.isFree() ? Theme.SUCCESS : Theme.WARNING);
        prc.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(ico);
        panel.add(Theme.vSpacer(10));
        panel.add(ttl);
        panel.add(Theme.vSpacer(6));
        panel.add(prc);
        panel.add(Theme.vSpacer(16));

        panel.add(detailRow("Category",  item.getCategory().toString().replace("_", " ")));
        panel.add(Theme.vSpacer(4));
        panel.add(detailRow("Condition", item.getCondition()));
        panel.add(Theme.vSpacer(4));
        panel.add(detailRow("Seller",    item.getSeller()));
        panel.add(Theme.vSpacer(4));
        panel.add(detailRow("College",   item.getCollege()));
        panel.add(Theme.vSpacer(4));
        panel.add(detailRow("Contact",   item.getContact()));
        panel.add(Theme.vSpacer(12));

        JLabel desc = new JLabel("<html><body style='width:360px'>" + item.getDescription() + "</body></html>");
        desc.setFont(Theme.fontPlain(12));
        desc.setForeground(Theme.TEXT_SECONDARY);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(desc);
        panel.add(Theme.vSpacer(20));

        Theme.GradientButton contactBtn = Theme.gradientButton("\uD83D\uDCDE  Contact Seller");
        contactBtn.setPreferredSize(new Dimension(200, 40));
        contactBtn.setMaximumSize(new Dimension(200, 40));
        contactBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactBtn.addActionListener(e -> JOptionPane.showMessageDialog(dialog,
            "Contact: " + item.getContact(), "Seller Info", JOptionPane.INFORMATION_MESSAGE));
        panel.add(contactBtn);

        dialog.setBackground(Theme.BG_DARK);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private JPanel detailRow(String key, String val) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel k = new JLabel(key + ":");
        k.setFont(Theme.fontBold(12));
        k.setForeground(Theme.TEXT_SECONDARY);
        k.setPreferredSize(new Dimension(80, 20));
        JLabel v = new JLabel(val);
        v.setFont(Theme.fontPlain(12));
        v.setForeground(Theme.TEXT_PRIMARY);
        row.add(k);
        row.add(v);
        return row;
    }

    // ── MY LISTINGS PANEL ─────────────────────────────────────────

    private JComponent buildMyListingsPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Theme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("My Listings");
        title.setFont(Theme.fontBold(22));
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(title);
        p.add(Theme.vSpacer(20));

        JPanel grid = new JPanel(new WrapLayout(FlowLayout.LEFT, 16, 16));
        grid.setBackground(Theme.BG_DARK);
        for (int i = items.size() - 1; i >= Math.max(0, items.size() - 2); i--) {
            grid.add(buildMyItemCard(items.get(i)));
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBackground(Theme.BG_DARK);
        scroll.getViewport().setBackground(Theme.BG_DARK);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(scroll);

        return p;
    }

    private JPanel buildMyItemCard(Item item) {
        Theme.CardPanel card = new Theme.CardPanel(14);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        card.setPreferredSize(new Dimension(280, 110));

        JLabel titleLbl = new JLabel(item.getEmoji() + "  " + item.getTitle());
        titleLbl.setFont(Theme.fontBold(14));
        titleLbl.setForeground(Theme.TEXT_PRIMARY);
        titleLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLbl);
        card.add(Theme.vSpacer(6));

        JLabel priceLbl = new JLabel(item.isFree() ? "FREE" : "\u20B9 " + String.format("%.0f", item.getPrice()));
        priceLbl.setFont(Theme.fontBold(13));
        priceLbl.setForeground(item.isFree() ? Theme.SUCCESS : Theme.WARNING);
        priceLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(priceLbl);
        card.add(Theme.vSpacer(10));

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);
        btns.setAlignmentX(Component.LEFT_ALIGNMENT);
        btns.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        JButton edit = new JButton("Edit");
        edit.setFont(Theme.fontPlain(12));
        edit.setForeground(Theme.ACCENT);
        edit.setBackground(Theme.BG_CARD2);
        edit.setBorder(new Theme.RoundedBorder(8, Theme.BORDER, Theme.BG_CARD2));
        edit.setContentAreaFilled(false);
        edit.setFocusPainted(false);
        edit.setOpaque(false);
        edit.setPreferredSize(new Dimension(70, 30));
        edit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton del = new JButton("Delete");
        del.setFont(Theme.fontPlain(12));
        del.setForeground(Theme.DANGER);
        del.setBackground(new Color(248, 113, 113, 30));
        del.setBorder(new Theme.RoundedBorder(8, new Color(248, 113, 113, 60), new Color(248, 113, 113, 30)));
        del.setContentAreaFilled(false);
        del.setFocusPainted(false);
        del.setOpaque(false);
        del.setPreferredSize(new Dimension(80, 30));
        del.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btns.add(edit);
        btns.add(del);
        card.add(btns);

        return card;
    }

    // ── PROFILE PANEL ─────────────────────────────────────────────

    private JComponent buildProfilePanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Theme.BG_DARK);
        wrapper.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        Theme.CardPanel card = new Theme.CardPanel(14);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(36, 40, 36, 40));
        card.setPreferredSize(new Dimension(480, 420));

        JLabel avatar = new JLabel("\uD83D\uDC64", SwingConstants.CENTER); // 👤
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 56));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel uname = new JLabel(currentUser, SwingConstants.CENTER);
        uname.setFont(Theme.fontBold(18));
        uname.setForeground(Theme.TEXT_PRIMARY);
        uname.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel college = new JLabel("Engineering College · CSE Dept", SwingConstants.CENTER);
        college.setFont(Theme.fontPlain(13));
        college.setForeground(Theme.TEXT_SECONDARY);
        college.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel joined = new JLabel("Joined March 2025", SwingConstants.CENTER);
        joined.setFont(Theme.fontItalic(12));
        joined.setForeground(Theme.TEXT_MUTED);
        joined.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(avatar);
        card.add(Theme.vSpacer(12));
        card.add(uname);
        card.add(Theme.vSpacer(4));
        card.add(college);
        card.add(Theme.vSpacer(2));
        card.add(joined);
        card.add(Theme.vSpacer(28));

        card.add(profileRow("\uD83D\uDCE6", "Items Listed", "2"));
        card.add(Theme.vSpacer(8));
        card.add(profileRow("\u2705", "Items Sold/Given", "0"));
        card.add(Theme.vSpacer(8));
        card.add(profileRow("\u2B50", "Rating", "New User"));
        card.add(Theme.vSpacer(24));

        Theme.GradientButton editBtn = Theme.gradientButton("Edit Profile");
        editBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        editBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(editBtn);

        wrapper.add(card);
        return wrapper;
    }

    private JPanel profileRow(String icon, String label, String val) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(400, 28));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel left = new JLabel(icon + "  " + label);
        left.setFont(Theme.fontPlain(13));
        left.setForeground(Theme.TEXT_SECONDARY);
        JLabel right = new JLabel(val);
        right.setFont(Theme.fontBold(13));
        right.setForeground(Theme.TEXT_PRIMARY);
        row.add(left, BorderLayout.WEST);
        row.add(right, BorderLayout.EAST);
        return row;
    }

    // ── SETTINGS PANEL ────────────────────────────────────────────

    private JComponent buildSettingsPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Theme.BG_DARK);
        wrapper.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        Theme.CardPanel card = new Theme.CardPanel(14);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(36, 40, 36, 40));
        card.setPreferredSize(new Dimension(480, 400));

        JLabel title = new JLabel("Settings");
        title.setFont(Theme.fontBold(22));
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(title);
        card.add(Theme.vSpacer(24));

        String[][] settings = {
            {"\uD83D\uDD14","Notifications","Enable item alerts"},
            {"\uD83D\uDD12","Privacy","Manage visibility"},
            {"\uD83C\uDFA8","Appearance","Dark / Light mode"},
            {"\u2753","Help & Support","FAQ and contact"}
        };

        for (String[] s : settings) {
            JPanel row = new JPanel(new BorderLayout(12, 0));
            row.setOpaque(false);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

            JLabel ico = new JLabel(s[0]);
            ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
            ico.setPreferredSize(new Dimension(30, 30));

            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setOpaque(false);
            JLabel name = new JLabel(s[1]);
            name.setFont(Theme.fontBold(13));
            name.setForeground(Theme.TEXT_PRIMARY);
            JLabel desc = new JLabel(s[2]);
            desc.setFont(Theme.fontPlain(11));
            desc.setForeground(Theme.TEXT_MUTED);
            info.add(name);
            info.add(desc);

            JLabel arrow = new JLabel("›");
            arrow.setFont(Theme.fontBold(18));
            arrow.setForeground(Theme.TEXT_MUTED);
            arrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            row.add(ico, BorderLayout.WEST);
            row.add(info, BorderLayout.CENTER);
            row.add(arrow, BorderLayout.EAST);
            card.add(row);
            card.add(Theme.separator());
        }

        card.add(Theme.vSpacer(16));

        JButton logout = new JButton("Logout");
        logout.setFont(Theme.fontBold(13));
        logout.setForeground(Theme.DANGER);
        logout.setBackground(new Color(248, 113, 113, 20));
        logout.setBorder(new Theme.RoundedBorder(10, new Color(248, 113, 113, 60), new Color(248, 113, 113, 20)));
        logout.setContentAreaFilled(false);
        logout.setFocusPainted(false);
        logout.setOpaque(false);
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        logout.setPreferredSize(new Dimension(400, 40));
        logout.setAlignmentX(Component.LEFT_ALIGNMENT);
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.addActionListener(e -> new LoginScreen(frame).show());
        card.add(logout);

        wrapper.add(card);
        return wrapper;
    }

    // ── HELPERS ───────────────────────────────────────────────────

    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max) + "…" : s;
    }

    // ── SAMPLE DATA ───────────────────────────────────────────────

    private List<Item> generateSampleItems() {
        List<Item> list = new ArrayList<>();
        list.add(new Item("Thermodynamics Textbook", "Cengel & Boles, 8th Ed. Minimal highlights",
            Item.Category.TEXTBOOKS, Item.Type.PAID, 350,
            "Alok Singh Negi", "Graphic Era Hill University", "9876543210", "Good"));
        list.add(new Item("Drafting Kit (Full Set)", "Includes compass, protractor, set squares & scale",
            Item.Category.CAD_TOOLS, Item.Type.PAID, 600,
            "Rudra Chauhan", "Graphic Era Hill University", "9123456789", "Like New"));
        list.add(new Item("Casio FX-991EX", "Scientific calculator, all functions working",
            Item.Category.CALCULATORS, Item.Type.PAID, 400,
            "Himansu", "Graphic Era Hill University", "9988776655", "Good"));
        list.add(new Item("Lab Coat (M size)", "White, used 1 semester only",
            Item.Category.LAB_EQUIPMENT, Item.Type.FREE, 0,
            "Apoorv Barthwal", "Graphic Era Hill University", "9871234560", "Like New"));
        list.add(new Item("Engineering Drawing Book", "Free — Bhatt & Panchal, complete notes inside",
            Item.Category.TEXTBOOKS, Item.Type.FREE, 0,
            "Shashank Mishra", "Graphic Era Hill University", "9001234567", "Fair"));
        list.add(new Item("Drawing Board A1", "Wooden board with parallel ruler, minor scuffs",
            Item.Category.CAD_TOOLS, Item.Type.PAID, 250,
            "Ishaan Singh Bhandari", "Graphic Era Hill University", "9812345670", "Good"));
        return list;
    }

    // ── WrapLayout (FlowLayout that wraps properly in scroll panes) ──

    static class WrapLayout extends FlowLayout {
        public WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;

                int hgap = getHgap(), vgap = getVgap();
                Insets insets = target.getInsets();
                int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);

                Dimension dim = new Dimension(0, 0);
                int rowWidth = 0, rowHeight = 0;

                int count = target.getComponentCount();
                for (int i = 0; i < count; i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                        if (rowWidth + d.width > maxWidth) {
                            addRow(dim, rowWidth, rowHeight);
                            rowWidth = 0;
                            rowHeight = 0;
                        }
                        if (rowWidth > 0) rowWidth += hgap;
                        rowWidth += d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }
                addRow(dim, rowWidth, rowHeight);
                dim.width += insets.left + insets.right + hgap * 2;
                dim.height += insets.top + insets.bottom + vgap * 2;
                return dim;
            }
        }

        private void addRow(Dimension dim, int rowWidth, int rowHeight) {
            dim.width = Math.max(dim.width, rowWidth);
            if (dim.height > 0) dim.height += getVgap();
            dim.height += rowHeight;
        }
    }
}
