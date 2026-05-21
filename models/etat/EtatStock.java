package models.etat;

import java.time.LocalDate;

public class EtatStock {
    private Long id;
    private LocalDate daty;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getDaty() {
        return daty;
    }
    public void setDaty(LocalDate daty) {
        this.daty = daty;
    }
}
