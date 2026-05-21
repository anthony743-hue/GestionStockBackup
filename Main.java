import java.util.ArrayList;
import java.util.Map;

import affichage.Fenetre;
import models.mouvement.MvtStock;
import models.mouvement.MvtStockLib;

public class Main {
    public static void main(String[] args) {
        try {
            // MvtStock m = new MvtStock();
            // m.setDaty("2026-05-24");
            // Map<Long, ArrayList<MvtStockLib>> ls = m.getDetailled();

            // for(ArrayList<MvtStockLib> l : ls.values()){
            //     for(MvtStockLib m2 : l){
            //         if(m2.getQuantite() > 0){
            //             System.out.println(m2);
            //         }
            //     }
            // }
            new Fenetre();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
