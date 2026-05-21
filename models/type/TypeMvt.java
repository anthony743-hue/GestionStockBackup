package models.type;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Map;

import affichage.components.Composant;
import affichage.components.InterfaceComp;
import affichage.components.MaListe;
import affichage.components.MonPanel;
import service.GenericService;

public class TypeMvt extends Composant {
    private Long id;
    private String designation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public MonPanel createFormulaire() throws Exception {
        GenericService service = new GenericService();
        ArrayList<Object> ls = service.findAll(this);

        MonPanel m = new MonPanel(this);
        Map<String, InterfaceComp> mapToChamp = m.getMapToChamp();
        MaListe maListe = new MaListe(ls);
        
        return m;
    }

    @Override
    public String toString(){
        return designation;
    }
}
