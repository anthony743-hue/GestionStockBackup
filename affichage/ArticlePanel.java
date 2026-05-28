package affichage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import affichage.components.MonPanel;
import ecoute.CompListener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Article;
import service.GenericService;

public class ArticlePanel extends JPanel {
    private Article article;
    public ArticlePanel() throws Exception {
        setLayout(new BorderLayout());
        JPanel j = new JPanel();
        article = new Article();
        MonPanel m = article.createFormulaire();
        j.add(m);
        JButton button = new JButton("Enregistrer l'article");
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                MonPanel comp = m;
                try {
                    comp.getValue();
                    System.out.println(comp.getTarget());
                    GenericService service = new GenericService();
                    service.save(comp.getTarget());
                    System.out.println("Sauvegarde reussi !!1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        m.add(button);
        add(j,BorderLayout.CENTER);
    }
}
