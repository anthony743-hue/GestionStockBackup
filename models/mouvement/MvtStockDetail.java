package models.mouvement;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;

import affichage.components.Champ;
import affichage.components.Composant;
import affichage.components.InterfaceComp;
import affichage.components.MonPanel;
import utilities.MethodUtils;

public class MvtStockDetail extends Composant {
    private Long id;
    private Long idMvtStockMere;
    private Long quantite;
    private Long prixUnitaire;
    private Long articleId;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Long prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMvtStockMere() {
        return idMvtStockMere;
    }

    public void setIdMvtStockMere(Long idMvtStockMere) {
        this.idMvtStockMere = idMvtStockMere;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    @Override
    public MonPanel createFormulaire() throws Exception {
        MonPanel m = new MonPanel();
        Map<String, InterfaceComp> mapToChamp = m.getMapToChamp();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridy = 0; 

        int colonne = 0; 

        Field[] champs = this.getClass().getDeclaredFields();
        String nom = null;
        Class<?> typeClazz = null;

        for (Field f : champs) {
            f.setAccessible(true);
            nom = f.getName();
            typeClazz = f.getType();

            // ----- Sous-panneau MonPanel -----
            if (MonPanel.class.isAssignableFrom(typeClazz)) {
                Composant temp = (Composant) typeClazz.getDeclaredConstructor().newInstance();
                MonPanel sousPanel = temp.createFormulaire();
                sousPanel.setBorder(BorderFactory.createTitledBorder(nom));

                gbc.gridx = colonne;
                gbc.gridwidth = 2;
                gbc.weightx = 1.0;
                m.add(sousPanel, gbc);
                mapToChamp.put(nom, sousPanel);

                colonne += 2; // occupe 2 colonnes
                continue;
            }
            Champ champSaisie = null;
            // ----- Champ simple (label + saisie) -----
            JLabel label = new JLabel(nom + " :");

            if (MethodUtils.isNumber(typeClazz)) {
                champSaisie = new Champ(Champ.NOMBRE);
            } else if (typeClazz == LocalDate.class) {
                champSaisie = new Champ(Champ.DATE);
            } else {
                champSaisie = new Champ(Champ.TEXTE);
            }

            gbc.gridx = colonne;
            gbc.gridwidth = 1;
            gbc.weightx = 0.0;
            m.add(label, gbc);

            gbc.gridx = colonne + 1;
            gbc.weightx = 1.0;
            m.add(champSaisie, gbc);
            mapToChamp.put(nom, champSaisie);

            colonne += 2; 
        }

        gbc.gridx = colonne;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        m.add(Box.createVerticalGlue(), gbc);

        return m;
    }
}
