package affichage;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class LayoutPanel extends JPanel {
    public LayoutPanel(){
        super(new CardLayout());
    }
    public void showPage(String s){
        CardLayout layout = (CardLayout) getLayout();
        layout.show(this, s);
    }
}
