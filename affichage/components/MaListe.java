package affichage.components;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

public class MaListe extends JComboBox<Object> implements InterfaceComp {
    public MaListe() {
        super();
    }

    public MaListe(Collection<?> data) {
        super();
        setData(data);
    }

    public void setData(Collection<?> data) {
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
        model.removeAllElements();

        if (data != null) {
            for (Object obj : data) {
                model.addElement(obj);
            }
        }

        setModel(model);
    }

    public void refresh() {
        DefaultComboBoxModel<Object> model = (DefaultComboBoxModel<Object>) getModel();
        int size = model.getSize();

        java.util.List<Object> items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            items.add(model.getElementAt(i));
        }

        setData(items);
    }

    public Object getValue() {
        return getSelectedItem();
    }
}