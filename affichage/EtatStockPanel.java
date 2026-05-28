package affichage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import affichage.components.MaTable;
import affichage.components.MonPanel;
import models.etat.EtatStock;
import models.etat.EtatStockFille;
import service.GenericService;

public class EtatStockPanel extends JPanel {
    private EtatStock l;
    private GenericService service;
    private MaTable maTable;

    public EtatStockPanel() throws Exception {
        l = new EtatStock();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.3;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;

        EtatStockFille ef = new EtatStockFille();
        MonPanel m = ef.createFormulaire();
        JPanel j = new JPanel();
        j.add(m);

        JButton button = new JButton("Filtrer");
        button.addActionListener(e -> {
            try {
                refresh();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        j.add(button);
        add(j,gbc);
        
        service = new GenericService();
        
        maTable = ef.createTable();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.7;
        add(new JScrollPane(maTable), gbc);
    }

    private void refresh() throws Exception {  
        ArrayList<Object> ls = service.findAll(l);
        maTable.setData(ls);
        maTable.refresh();

    }
}
