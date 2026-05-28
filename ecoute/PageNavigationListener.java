package ecoute;

import affichage.LayoutPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageNavigationListener implements ActionListener {
    private final LayoutPanel layoutPanel;
    private final String pageName;

    public PageNavigationListener(LayoutPanel layoutPanel, String pageName) {
        this.layoutPanel = layoutPanel;
        this.pageName = pageName;
    }

    public void actionPerformed(ActionEvent e) {
        this.layoutPanel.showPage(pageName);
    }
}
