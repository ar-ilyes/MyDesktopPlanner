package Modules;

import java.time.LocalDate;
import java.util.Date;
public class TacheSimple extends Tache {
    private int periodicite;

    private Creneau creneau;
    public TacheSimple(String nom, int duree,Creneau creneau ,Priorite priorite, LocalDate deadline, Categorie categorie, Couleur color, Etat etat, Etat_realisation etatRealisation, int periodicite) {
        super(nom,duree,priorite,deadline, categorie, color, etat, etatRealisation);
        this.periodicite = periodicite;
        this.creneau = creneau;
    }

    public Creneau getCreneau(){
        return this.creneau;
    }
    public void setCreneau(Creneau creneau){
        this.creneau = creneau;
    }
    public int getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(int periodicite) {
        this.periodicite = periodicite;
    }
}

