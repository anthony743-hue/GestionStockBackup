package affichage.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

import utilities.MethodUtils;

public class MonPanel extends JPanel implements InterfaceComp {
    private Object target = null;
    protected Map<String, InterfaceComp> mapToChamp;

    public Object getTarget() {
        return target;
    }

    public Map<String, InterfaceComp> getMapToChamp() {
        return mapToChamp;
    }

    public void setMapToChamp(Map<String, InterfaceComp> mapToChamp) {
        this.mapToChamp = mapToChamp;
    }

    public MonPanel() throws Exception {
        setLayout(new GridBagLayout());
        mapToChamp = new HashMap<>();
    }

    public MonPanel(Object target) throws Exception{
        this();
        this.target = target;
    }

    public Object getValue() throws Exception {
        String nom = null;
        InterfaceComp comp = null;
        Object val = null;
        for(Map.Entry<String, InterfaceComp> entry : mapToChamp.entrySet()){
            nom = entry.getKey();
            comp = entry.getValue();
            if(comp instanceof MonPanel m){
                m.getValue();
            } else if( comp instanceof MaListe m ){
                
            } else{
                val = comp.getValue();
                Method method = target.getClass().getDeclaredMethod("set" + MethodUtils.capitalized(nom), val.getClass());
                method.invoke(target,val);
            }
        }
        return null;
    }
}