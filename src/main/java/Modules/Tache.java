package Modules;

import java.util.Date;

import java.time.LocalDate;

public abstract class Tache {

    protected String nom;

    protected Priorite priorite;
    protected LocalDate deadline;
    protected LocalDate date;
    protected Categorie categorie;
    protected Couleur color;
    protected Etat etat;
    protected Etat_realisation etatRealisation;
    protected boolean Bloqué = false;
    protected int ID;
    protected static int IDtemp = 0;

    protected int Duree;

    public Tache(String nom, int duree ,Priorite priorite, LocalDate deadline, Categorie categorie, Couleur color, Etat etat, Etat_realisation etatRealisation) {
        this.nom = nom;
        this.Duree = duree;
        this.priorite = priorite;
        this.deadline = deadline;
        this.categorie = categorie;
        this.color = color;
        this.etat = etat;
        this.etatRealisation = etatRealisation;
        this.ID = IDtemp;
        IDtemp++;
    }


    public int getDuree(){
        return this.Duree;
    }

    public void setDuree(int duree){
        this.Duree = duree;
    }

    // Getters and setters
    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public boolean isBloqué() {
        return Bloqué;
    }

    public void setBloqué(boolean Bloqué) {
        this.Bloqué = Bloqué;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Couleur getColor() {
        return color;
    }

    public void setColor(Couleur color) {
        this.color = color;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {

        this.etat = etat;
        if(this.etat==Etat.Completed){
            this.etatRealisation = Etat_realisation.TERMINÉ;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Etat_realisation getEtatRealisation() {
        return etatRealisation;
    }

    public void setEtatRealisation(Etat_realisation etatRealisation) {
        this.etatRealisation = etatRealisation;
    }

    public boolean isComplete() {
        if(this.etat==Etat.Completed){
            return true;
        }else{
            return false;
        }
    }

    public int getID() {
        return this.ID;
    }

    public int setID(int ID) {
        return this.ID = ID;
    }

    // Abstract method

}


