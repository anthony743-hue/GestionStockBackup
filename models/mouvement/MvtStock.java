package models.mouvement;

import affichage.components.Composant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.Article;

public class MvtStock extends Composant {
    private Long id;
    private LocalDate daty;
    private Long TypeMvt;
    private String observation;

    public Long getTypeMvt() {
        return TypeMvt;
    }

    public void setTypeMvt(Long typeMvt) {
        TypeMvt = typeMvt;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDate getDaty() {
        return daty;
    }

    public void setDaty(LocalDate daty) {
        this.daty = daty;
    }

    public void setDaty(String s) throws Exception {
        if( s == null ){
            setDaty(LocalDate.now());
        } else {
            setDaty(LocalDate.parse(s));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, ArrayList<MvtStockLib>> getDetailled() throws Exception {
        MvtStockLib t = new MvtStockLib();
        t.setDaty(daty);
        Map<Long, ArrayList<MvtStockLib>> ls = t.getAsMap(t.findByDate());
        Map<Long, Article> articles  = new HashMap<>();
        Long key = 0L;
        ArrayList<MvtStockLib> lsTemp = null;

        int idx = 0;
        Long sumCump = 0L, sumE = 0L, qte = 0L, pu = 0L, qteE=  0L, cump = 0L, diffTemp = 0L;
        String type = null, typeVal = null;
        MvtStockLib temp = null;
        for(Map.Entry<Long,ArrayList<MvtStockLib>> entry : ls.entrySet()){
            key = entry.getKey();
            lsTemp = entry.getValue();
            
            idx = -1;
            sumCump = 0L;
            sumE = 0L;
            for(int i=0; i < lsTemp.size(); i++){
                temp = lsTemp.get(i);
                qte = temp.getQuantite();
                type = temp.getType();
                typeVal = temp.getTypeVal().trim();
                pu = temp.getPu();
                
                if( type.equalsIgnoreCase("entree") ){
                    sumE += qte;
                    if( typeVal.equalsIgnoreCase("fifo") && idx == -1 ){
                        idx = i;
                    } else if( typeVal.equalsIgnoreCase("lifo") ){
                        idx = i;
                    } else if( typeVal.equalsIgnoreCase("cump") ){
                        sumCump+= pu * qte;
                    }
                } else if( type.equalsIgnoreCase("sortie") && sumE > 0 ){
                    if( typeVal.equalsIgnoreCase("fifo") ){
                        while (idx < i && qte > 0) {
                            temp = lsTemp.get(idx);
                            type = temp.getType();
                            qteE = temp.getQuantite();
                            if( type.equalsIgnoreCase("entree") &&  qteE > 0 ){
                                diffTemp = qte - qteE;
                                qte = Math.max(0, diffTemp);
                                diffTemp = Math.max(0, -diffTemp);
                                temp.setQuantite(diffTemp);
                            }   
                            idx++;
                        }
                    } else if( typeVal.equalsIgnoreCase("lifo") ){
                        while (idx >= 0 && qte > 0) {
                            temp = lsTemp.get(idx);
                            type = temp.getType();
                            qteE = temp.getQuantite();
                            if( type.equalsIgnoreCase("entree") && qteE > 0 ){
                                diffTemp = qte - qteE;
                                qte = Math.max(0, diffTemp);
                                diffTemp = Math.max(0, -diffTemp);
                                temp.setQuantite(diffTemp);
                            }   
                            idx--;
                        }
                    } else if( typeVal.equalsIgnoreCase("cump") ){
                        cump = sumCump / sumE;
                        sumCump = qte * cump;
                        sumE-= qte;
                    }
                }
            }
            entry.setValue(lsTemp);
        }

        return ls;
    }

    private Map<Long, MvtStock> getMvtAsMap(ArrayList<MvtStock> ls){
        Map<Long, MvtStock> lret = new HashMap<>();
        Long key = 0L;
        for (MvtStock m : ls) {
            key = m.getId();
            lret.put(key, m);
        }
        return lret;
    }
}
