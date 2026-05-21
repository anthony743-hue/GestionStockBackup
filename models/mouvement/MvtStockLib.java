package models.mouvement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import models.Article;
import service.GenericService;

public class MvtStockLib {
    private Long idMvtDetail;
    private Long quantite;
    private Long pu;
    private String type;
    private String typeVal;
    private LocalDate daty;
    private Long ArticleId;

    public String getTypeVal() {
        return typeVal;
    }

    public void setTypeVal(String typeVal) {
        this.typeVal = typeVal;
    }

    public Long getArticleId() {
        return ArticleId;
    }

    public void setArticleId(Long articleId) {
        ArticleId = articleId;
    }

    public LocalDate getDaty() {
        return daty;
    }

    public void setDaty(LocalDate daty) {
        this.daty = daty;
    }

    public Long getIdMvtDetail() {
        return idMvtDetail;
    }

    public void setIdMvtDetail(Long idMvtDetail) {
        this.idMvtDetail = idMvtDetail;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getPu() {
        return pu;
    }

    public void setPu(Long pu) {
        this.pu = pu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<MvtStockLib> findAll() throws Exception {
        GenericService service = new GenericService();

        ArrayList<Object> ls = service.findAll(MvtStockLib.class);
        ArrayList<MvtStockLib> l = new ArrayList<>();
        for (Object obj : ls) {
            if (obj instanceof MvtStockLib m) {
                l.add(m);
            }
        }
        return l;
    }

    public ArrayList<MvtStockLib> findByDate() throws Exception {
        ArrayList<MvtStockLib> l = findAll(), ret = new ArrayList<>();
        for (MvtStockLib m : l) {
            if (m.getDaty().compareTo(daty) < 0) {
                ret.add(m);
            }
        }
        ret.sort(Comparator.comparing(MvtStockLib::getDaty));
        return ret;
    }

    public Map<Long, ArrayList<MvtStockLib>> getAsMap(ArrayList<MvtStockLib> ls) {
        Map<Long, ArrayList<MvtStockLib>> lret = new HashMap<>();
        Long key = 0L;
        ArrayList<MvtStockLib> temp = null;
        for (MvtStockLib m : ls) {
            key = m.getArticleId();
            if (!lret.containsKey(key)) {
                lret.put(key, new ArrayList<>());
            }
            temp = lret.get(key);
            temp.add(m);
            lret.put(key, temp);
        }
        return lret;
    }

    @Override
    public String toString() {
        return "MvtStockLib{" +
                "idMvtDetail=" + idMvtDetail +
                ", quantite=" + quantite +
                ", pu=" + pu +
                ", type='" + type + '\'' +
                ", typeVal='" + typeVal + '\'' +
                ", daty=" + daty +
                ", ArticleId=" + ArticleId +
                '}';
    }
}
