package affichage;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import affichage.components.SideBar;

public class GlobalePanel extends JPanel {

    private LayoutPanel principal;
    private SideBar sideBar;

    public GlobalePanel() throws Exception {
        setLayout(new GridBagLayout());

        // ----- Panneau principal avec CardLayout -----
        principal = new LayoutPanel();
        principal.setLayout(new CardLayout());
        principal.setBorder(BorderFactory.createTitledBorder("Contenu"));

        ArticlePanel articlePanel = new ArticlePanel();
        MvtStockPanel mvtStockPanel = new MvtStockPanel();
        EtatStockPanel etatStockPanel = new EtatStockPanel();
        principal.add("articlePanel", articlePanel);
        principal.add("mvtStockPanel", mvtStockPanel);
        principal.add("etatStockPanel", etatStockPanel);

        // ----- Barre latérale (pilotée par le LayoutPanel) -----
        sideBar = new SideBar(principal);
        sideBar.addPage("Ajouter un article","articlePanel");
        sideBar.addPage("Ajouter un MvtStock","mvtStockPanel");
        sideBar.addPage("Voir l'EtatStock", "etatStockPanel");
        sideBar.setBorder(BorderFactory.createTitledBorder("Navigation"));

        // ----- Placement dans la grille -----
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;   
        add(sideBar, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;  
        principal.showPage("articlePanel"); 
        add(principal, gbc);
    }
}