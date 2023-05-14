package Modules;

public class Categorie {
    private String nom;
    private Couleur couleur;

    public Categorie(String nom, Couleur couleur) {
        this.nom = nom;
        this.couleur = couleur;
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