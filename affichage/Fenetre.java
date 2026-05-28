package affichage;

import models.mouvement.*;
import javax.swing.*;

public class Fenetre extends JFrame {

    public Fenetre() throws Exception {
        setTitle("Exemple MaListe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 750);
        setLocationRelativeTo(null); // centre la fenêtre

        JPanel panel = new JPanel();
        MvtStock m = new MvtStock();
        panel.add(m.createFormulaire());
        add(new GlobalePanel());
        setVisible(true);
    }
}