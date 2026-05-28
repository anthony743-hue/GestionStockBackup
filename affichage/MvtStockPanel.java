package affichage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import affichage.components.MaTable;
import affichage.components.MonPanel;
import models.mouvement.MvtStock;
import models.mouvement.MvtStockDetail;
import utilities.MethodUtils;

public class MvtStockPanel extends JPanel {
    private MvtStock m;
    private List<MvtStockDetail> lsM2;
    private MaTable tableMvtStDt;

    public MvtStockPanel() throws Exception {
        setLayout(new GridBagLayout());
        this.m = new MvtStock();
        this.lsM2 = new ArrayList<>();
        MvtStockDetail m2 = new MvtStockDetail();
        lsM2.add(m2);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel j1 = new JPanel(new GridBagLayout());

        MonPanel mp1 = m.createFormulaire();
        j1.add(mp1, gbc);

        JButton enregistrer = new JButton("Enregistrer");
        enregistrer.addActionListener(e -> {
            public void actionPerformed(ActionEvent e){
                try {
                    enregistrer();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        gbc.gridx++;
        gbc.weighty = 0.0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        j1.add(enregistrer, gbc);  

        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        add(j1,gbc);

        JPanel j = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.9;
        gbc.weighty = 1.0;
    
        tableMvtStDt = m2.createTable(lsM2);

        j.add(new JScrollPane(tableMvtStDt), gbc);

        JButton ajouter = new JButton("Ajouter nouvelle ligne");
        ajouter.addActionListener(e -> addMvtStockDetailForm());

        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx++;
        j.add(ajouter, gbc);

        gbc.weightx = 1.0;
        gbc.weighty = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy++;
        add(j,gbc);
    }

    private void enregistrer()  throws Exception {
        DefaultTableModel model = (DefaultTableModel) tableMvtStDt.getModel();
        Class<?> c = MvtStockDetail.class;
        Field[] fields = c.getDeclaredFields();

        int i = 0, j = 0;
        MvtStockDetail temp = null;
        String val = null, nomChamp = null;
        for(i=0; i < lsM2.size(); i++){
            temp = lsM2.get(i);
            for(j=0; j < fields.length; i++){
                fields[i].setAccessible(true);
                nomChamp = fields[i].getName();
                if(!nomChamp.equalsIgnoreCase("id") ){
                    continue;
                }
                
                val = (String) model.getValueAt(i, j);
                if(val == null || val.isEmpty()){
                    continue;
                }
                Method method = c.getDeclaredMethod("set" + MethodUtils.capitalized(nomChamp), String.class);
                method.invoke(temp,val);
            }
        }
    }

    private void addMvtStockDetailForm() {
        tableMvtStDt.addRow();
        lsM2.add(new MvtStockDetail());
        revalidate();
        repaint();
    }
}
