package Modules;

import java.util.ArrayList;

public class Projet {///projet est juste comme une etiquette pour les taches,on peut associer des taches à un projet existant puis on peut les consulter au niveau de la page projet
    private String nom;
    private String description;
    private ArrayList<TacheSimple> tacheSimples;
    private ArrayList<TacheDecompose> tacheDecomposees;
    private Etat_realisation etatRealisationGlobal;
    private Calendrier calendrierSuper;
    private int ID;
    private int IDtemp=0;
    public Projet(Calendrier calendrierSuper,String nom, String description, ArrayList<TacheSimple> tacheSimples, ArrayList<TacheDecompose> tacheDecomposees, Etat_realisation etatRealisationGlobal) {
        this.nom = nom;
        this.description = description;
        this.tacheSimples = tacheSimples;
        this.tacheDecomposees = tacheDecomposees;
        this.etatRealisationGlobal = etatRealisationGlobal;
        this.calendrierSuper=calendrierSuper;
        this.ID=IDtemp;
        IDtemp++;
    }
    public Projet(String nom, String description,Calendrier calendrierSuper) {
        this.nom = nom;
        this.description = description;
        this.tacheSimples = new ArrayList<TacheSimple>();
        this.tacheDecomposees = new ArrayList<TacheDecompose>();
        this.etatRealisationGlobal = Etat_realisation.EN_COURS;
        this.calendrierSuper=calendrierSuper;
        this.ID=IDtemp;
        IDtemp++;
    }
    public int getID() {
        return this.ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNom() {
        return this.nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
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


    ///le rendement d'un projet çad le nombre de taches terminées sur le nombre de taches totales
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
