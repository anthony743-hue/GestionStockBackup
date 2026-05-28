package ecoute;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import affichage.components.InterfaceComp;
import affichage.components.MonPanel;

public class CompListListener implements ActionListener{
    private List<MonPanel> lsComp;
    public CompListListener(MonPanel m){
        lsComp = new ArrayList<>();
        lsComp.add(m);
    }
    public void addMPanel(MonPanel comp){
        lsComp.add(comp);
    }
    public void actionPerformed(ActionEvent event){
        try {
            for(MonPanel comp : lsComp){
                comp.getValue();
                System.out.println(comp.getTarget());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}