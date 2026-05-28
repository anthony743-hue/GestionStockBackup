package models.etat;

import affichage.components.Composant;

public class EtatStockFille extends Composant {
    private Long id;
    private Long articleId;
    private Long quantite;
    private Long id_mere;
    public Long getId_mere() {
        return id_mere;
    }
    public void setId_mere(Long id_mere) {
        this.id_mere = id_mere;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getArticleId() {
        return articleId;
    }
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Long getQuantite() {
        return quantite;
    }
    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }
}
