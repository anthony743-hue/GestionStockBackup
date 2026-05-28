package affichage.components;

import javax.swing.*;

import affichage.LayoutPanel;
import ecoute.PageNavigationListener;

import java.awt.*;

public class SideBar extends JPanel {
    private JButton selectedButton = null;
    private static Color normalBg = new Color(245, 245, 245);
    private static Color hoverBg = new Color(220, 230, 250);

    private Font font = new Font("Segoe UI", Font.PLAIN, 14);
    private LayoutPanel layoutPanel;
    public SideBar(LayoutPanel layoutPanel) {
        this.layoutPanel = layoutPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setBackground(new Color(250, 250, 250));
    }

    public void addPage(String title, String pageName) {
        JButton btn = new JButton(title);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(font);

        btn.addActionListener(new PageNavigationListener(this.layoutPanel, pageName));
        add(btn);
        add(Box.createVerticalStrut(8));
        revalidate();
        repaint();
    }
}