package Modules;

/**
 * Décrivez votre classe Badge ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Badge
{
    private String nom;
    public Badge(String nom){
        this.nom = nom;
    }
    public Badge(){
        this.nom = "";
    }
    public String getNom(){
        return this.nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
}