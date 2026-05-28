package models.mouvement;

import affichage.components.Composant;

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

    public void setArticleId(String s){
        setArticleId(Long.parseLong(s));
    }    

    public Long getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Long prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setPrixUnitaire(String s){
        setPrixUnitaire(Long.parseLong(s));
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

    public void setQuantite(String s){

    }

    @Override
    public String toString(){
        return String.format("ArticleId %d Pu %d Quantite %d", getArticleId(),getPrixUnitaire(),getQuantite());
    }
}
