package Modules;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
public class TacheSimple extends Tache implements Cloneable , Serializable {
    private int periodicite;
    private boolean partieTacheDecompose=false;

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
    public boolean isPartieTacheDecompose() {
        return partieTacheDecompose;
    }
    public void setPartieTacheDecompose(boolean partieTacheDecompose) {
        this.partieTacheDecompose = partieTacheDecompose;
    }

    public TacheSimple clone() throws CloneNotSupportedException{
        TacheSimple tacheSimple = (TacheSimple) super.clone();
        tacheSimple.setCreneau((Creneau) this.creneau.clone());
        return tacheSimple;
    }
}

