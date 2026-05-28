package models;

import affichage.components.Composant;

public class Article extends Composant {
    private Long id;
    private Long idTypeValorisation;
    private String libelle;
   
    public Long getIdTypeValorisation() {
        return idTypeValorisation;
    }

    public void setIdTypeValorisation(Long idTypeValorisation) {
        this.idTypeValorisation = idTypeValorisation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle)  throws Exception {
        if( libelle == null ){
            throw new Exception("Le libelle doit etre non null");
        }
        this.libelle = libelle;
    }

    @Override
    public String toString(){
        return String.format("{Article %d de TypeVal %d avec libelle %s}", id,idTypeValorisation,libelle);
    }
}
