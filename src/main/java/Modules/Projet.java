package Modules;

import java.util.ArrayList;

public class Projet {
    private String nom;
    private String description;
    private ArrayList<TacheSimple> tacheSimples;
    private ArrayList<TacheDecompose> tacheDecomposees;
    private Etat_realisation etatRealisationGlobal;
    private Calendrier calendrierSuper;
    public Projet(Calendrier calendrierSuper,String nom, String description, ArrayList<TacheSimple> tacheSimples, ArrayList<TacheDecompose> tacheDecomposees, Etat_realisation etatRealisationGlobal) {
        this.nom = nom;
        this.description = description;
        this.tacheSimples = tacheSimples;
        this.tacheDecomposees = tacheDecomposees;
        this.etatRealisationGlobal = etatRealisationGlobal;
        this.calendrierSuper=calendrierSuper;
    }
    public Projet(String nom, String description,Calendrier calendrierSuper) {
        this.nom = nom;
        this.description = description;
        this.tacheSimples = new ArrayList<TacheSimple>();
        this.tacheDecomposees = new ArrayList<TacheDecompose>();
        this.etatRealisationGlobal = Etat_realisation.EN_COURS;
        this.calendrierSuper=calendrierSuper;
    }
    public void ajouterTache(TacheSimple tache){
        tacheSimples.add(tache);
    }
    public void ajouterTache(TacheDecompose tache){
        tacheDecomposees.add(tache);
    }
    public void supprimerTache(TacheSimple tache){
        tacheSimples.remove(tache);
    }
    public void supprimerTache(TacheDecompose tache){
        tacheDecomposees.remove(tache);
    }
    public TacheSimple getTacheSimple(int index){
        return tacheSimples.get(index);
    }
    public TacheDecompose getTacheDecompose(int index){
        return tacheDecomposees.get(index);
    }
    public ArrayList<TacheSimple> getTacheSimples() {
        return tacheSimples;
    }
    public ArrayList<TacheDecompose> getTacheDecomposees() {
        return tacheDecomposees;
    }
    public boolean planifierAuto(TacheSimple tache){

        if(calendrierSuper.planifierAuto(tache)){
            this.ajouterTache(tache);
            return true;
        }else{
            return false;
        }
    }
    public boolean planifierAuto(TacheDecompose tache){
        if(calendrierSuper.planifierAuto(tache)){
            this.ajouterTache(tache);
            return true;
        }else{
            return false;
        }
    }

    public boolean planifierAuto(ArrayList<TacheSimple> taches){
        ArrayList<TacheSimple> suggestion= calendrierSuper.planifierAuto(taches);
        if(suggestion.size()==taches.size()){
            return calendrierSuper.applySuggestions(suggestion);
        }else {
            return false;
        }
    }

    public boolean etaleLaPeriode(ArrayList<TacheSimple> Taches){
        for(TacheSimple tache : Taches){
            this.ajouterTache(tache);
        }
        return calendrierSuper.etaleLaPeriode(Taches);
    }
    ///!!!!!!! maybe we need to add replanification later

    ///ajouter le rendement d'un projet çad le nombre de taches terminées sur le nombre de taches totales
    public double rendement(){
        double rendement=0;
        double nbTachesTotales=0;
        double nbTachesTerminees=0;
        for(TacheSimple tache : tacheSimples){
            nbTachesTotales++;
            if(tache.getEtatRealisation()==Etat_realisation.TERMINÉ){
                nbTachesTerminees++;
            }
        }
        for(TacheDecompose tache : tacheDecomposees){
            nbTachesTotales++;
            if(tache.getEtatRealisation()==Etat_realisation.TERMINÉ){
                nbTachesTerminees++;
            }
        }
        rendement=nbTachesTerminees/nbTachesTotales;
        return rendement;
    }
}
