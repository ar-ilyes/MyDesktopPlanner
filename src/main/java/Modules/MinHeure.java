package Modules;

public class MinHeure {
    private String heure; // représente l'heure réelle de la journée sous forme de chaîne de caractères (comme 11:30)
    private boolean libre;
    private boolean dansCreneau;

    private boolean bloque;

    public MinHeure(String heure, boolean libre, boolean dansCreneau) {
        this.heure = heure;
        this.libre = libre;
        this.dansCreneau = dansCreneau;
        this.bloque= false;
    }

    // getters et setters pour les attributs
    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public boolean isDansCreneau() {
        return dansCreneau;
    }

    public void setDansCreneau(boolean dansCreneau) {
        this.dansCreneau = dansCreneau;
    }
    public boolean isBloque() {
        return bloque;
    }
    public void setBloque(boolean bloque) {
        this.bloque = bloque;
    }
}

