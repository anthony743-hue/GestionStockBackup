package affichage.components;

import javax.swing.JTextField;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Champ extends JTextField implements InterfaceComp{
    public static final int TEXTE = 0, NOMBRE = 1, DATE = 2, DATETIME = 3;
    private int type;

    public Champ(int type) {
        super(20);
        this.type = type;
    }

    public Object getValue()  {
        String texte = getText().trim();
        if (texte.isEmpty())
            return getDefaultValue();

        return switch (this.type) {
            case TEXTE -> texte;
            case NOMBRE -> Long.parseLong(texte);
            case DATE -> java.time.LocalDate.parse(texte, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            case DATETIME -> java.time.LocalDateTime.parse(texte, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            default -> throw new IllegalStateException("Type inconnu : " + type);
        };
    }

    private Object getDefaultValue(){
        return switch (this.type) {
            case TEXTE -> "";
            case NOMBRE -> 0L;
            case DATE -> LocalDate.now();
            case DATETIME -> LocalDateTime.now();
            default -> throw new IllegalStateException("Type inconnu : " + type);
        };
    }
}