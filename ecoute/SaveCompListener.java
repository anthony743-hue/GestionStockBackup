package ecoute;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import affichage.components.MonPanel;
import service.GenericService;

public class SaveCompListener implements ActionListener{
    private MonPanel comp;
    public MonPanel getComp() {
        return comp;
    }

    public SaveCompListener(MonPanel comp) throws Exception {
        setComp(comp);
    }

    public void setComp(MonPanel comp) throws Exception{
        if( comp == null ){
            throw new Exception("Le composant doit etre initialise");
        }
        this.comp = comp;
    }
    public void actionPerformed(ActionEvent event){
        try {
            GenericService service = new GenericService();
            
        } catch (Exception e) {
            
        }
        
    }
}
