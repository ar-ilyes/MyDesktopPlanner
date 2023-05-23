package Modules;

public class Categorie {
    private String nom;
    private Couleur couleur;

    public Categorie(String nom) {
        this.nom = nom;
        if(nom.equals("Studies")){
            this.couleur = Couleur.BLEU;
        }else  if(nom.equals("Work")){
            this.couleur = Couleur.ROUGE;
        }else if(nom.equals("Sport")){
            this.couleur = Couleur.JAUNE;
        }else if(nom.equals("Health")){
            this.couleur = Couleur.VERT;
        }else if(nom.equals("Perso")){
            this.couleur = Couleur.GRIS;
        }else if(nom.equals("Family")){
            this.couleur = Couleur.ROUGE;
        }
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