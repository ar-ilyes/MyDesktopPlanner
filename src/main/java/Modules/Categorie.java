package Modules;

public class Categorie {
    private String nom;
    private Couleur couleur;

    public Categorie(String nom) {
        this.nom = nom;
        this.couleur = Couleur.BLANC;//!!!!!!!!!!each categorie have to get a differnet color // maybe we will use txt files
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }
}