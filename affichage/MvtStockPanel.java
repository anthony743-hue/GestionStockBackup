package affichage;

import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import affichage.components.MonPanel;
import models.mouvement.MvtStock;
import models.mouvement.MvtStockDetail;

public class MvtStockPanel extends JPanel {
    private MvtStock m;
    private List<MvtStockDetail> lsM2;

    public MvtStockPanel() throws Exception {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.m = new MvtStock();
        MonPanel mp1 = m.createFormulaire();
        add(mp1);

        this.lsM2 = new ArrayList<>();
        MvtStockDetail m2 = new MvtStockDetail();
        lsM2.add(m2);
        MonPanel mp2 = m2.createFormulaire();
        add(mp2);
    }

    private void addMvtStockDetailForm() throws Exception {
        MvtStockDetail m2 = new MvtStockDetail();
        lsM2.add(m2);
        MonPanel mp2 = m2.createFormulaire();
        add(mp2);
        revalidate();
        repaint();
    }
}
