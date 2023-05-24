package Modules;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;

public class TacheDecompose extends Tache implements Cloneable , Serializable {
    private ArrayList<TacheSimple> parties;
    private int periodeDesParties;

    private int nbParties=0;

    public TacheDecompose(String nom, int duree,Creneau creneau, Priorite priorite, LocalDate deadline, Categorie categorie, Couleur color, Etat etat, Etat_realisation etatRealisation, ArrayList<TacheSimple> parties, int periodeDesParties) {
        super(nom,duree,priorite,deadline, categorie, color, etat, etatRealisation);
        this.parties = parties;
        this.periodeDesParties = periodeDesParties;
    }

    public ArrayList<TacheSimple> getParties() {
        return parties;
    }

    public void setParties(ArrayList<TacheSimple> parties) {
        this.parties = parties;
        this.nbParties = parties.size();
    }

    public int getPeriodeDesParties() {
        return periodeDesParties;
    }

    public void setPeriodeDesParties(int periodeDesParties) {
        this.periodeDesParties = periodeDesParties;
    }

    public void addPartie(TacheSimple partie) {
        this.parties.add(partie);
    }

    public void removePartie(TacheSimple partie) {
        this.parties.remove(partie);
        this.nbParties--;
    }

    public TacheSimple decomposer(int Duree) {
        if (Duree <= this.getDuree() - this.periodeDesParties) {
            TacheSimple partie = new TacheSimple(this.nom + String.valueOf(nbParties), Duree,new Creneau("00:00","00:00"), this.priorite, this.deadline, this.categorie, this.color, this.etat, this.etatRealisation, Duree);
            this.addPartie(partie);
            nbParties++;
            this.periodeDesParties+=Duree;
            partie.setPartieTacheDecompose(true);
            return partie;
        }else {
            return null;
        }
    }
    public TacheDecompose clone() throws CloneNotSupportedException{
        TacheDecompose tacheDecompose = (TacheDecompose) super.clone();
        ArrayList<TacheSimple> parties = new ArrayList<TacheSimple>();
        for (TacheSimple partie : this.parties) {
            parties.add((TacheSimple) partie.clone());
        }
        tacheDecompose.setParties(parties);
        return tacheDecompose;
    }
}

