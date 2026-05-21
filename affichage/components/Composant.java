package affichage.components;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;

import utilities.MethodUtils;

public class Composant {

    public MonPanel createFormulaire() throws Exception {
        MonPanel m = new MonPanel(this);
        Map<String, InterfaceComp> mapToChamp = m.getMapToChamp();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Field[] champs = this.getClass().getDeclaredFields();
        String nom = null;
        JLabel label = null;
        Champ champSaisie = null; 
        Composant temp = null;
        for (Field f : champs) {
            f.setAccessible(true);
            nom = f.getName();
            Class<?> typeClazz = f.getType();

            if (typeClazz.isAssignableFrom(MonPanel.class)) {
                temp = (Composant) (typeClazz.getDeclaredConstructor().newInstance());
                MonPanel sousPanel = temp.createFormulaire();
                sousPanel.setBorder(BorderFactory.createTitledBorder(nom));

                gbc.gridx = 0;
                gbc.gridy++;
                gbc.gridwidth = 2;
                m.add(sousPanel, gbc);
                mapToChamp.put(nom, sousPanel);
                continue;
            }

            label = new JLabel(nom + " :");

            if (MethodUtils.isNumber(typeClazz)) {
                champSaisie = new Champ(Champ.NOMBRE);
            } else if (typeClazz == LocalDate.class) {
                champSaisie = new Champ(Champ.DATE);
            } else {
                champSaisie = new Champ(Champ.TEXTE);
            } 

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 1;
            gbc.weightx = 0.0;
            m.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            m.add(champSaisie, gbc);
            mapToChamp.put(nom, champSaisie);
        }

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        m.add(Box.createVerticalGlue(), gbc);
        return m;
    }

    public MaTable createTable(Collection<?> data) throws Exception{
        return new MaTable(data);
    }

    public MaTable createTable() throws Exception {
        return new MaTable();
    }
}