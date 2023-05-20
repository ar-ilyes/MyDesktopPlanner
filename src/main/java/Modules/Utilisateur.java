package Modules;


import java.util.ArrayList;

public class Utilisateur {
    private String pseudo;
    private Calendrier calendrier_perso;
    private Historique historique;
    private int nbr_min_tache;
    private ArrayList<Badge> badges;
    private String motDePasse;

    private int nbrFelicitation;
    public Utilisateur(String pseudo, Calendrier calendrier_perso, int nbr_min_tache, ArrayList<Badge> badges, String motDePasse) {
        this.pseudo = pseudo;
        this.calendrier_perso = calendrier_perso;
        this.historique = new Historique();
        this.nbr_min_tache = nbr_min_tache;
        this.badges = badges;
        this.motDePasse = motDePasse;
        this.nbrFelicitation= 0;
        this.historique=new Historique();
    }
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Calendrier getCalendrier_perso() {
        return calendrier_perso;
    }

    public void setCalendrier_perso(Calendrier calendrier_perso) {
        this.calendrier_perso = calendrier_perso;
    }

    public Historique getHistorique() {
        return historique;
    }

    public void setHistorique(Historique historique) {
        this.historique = historique;
    }

    public int getNbr_min_tache() {
        return nbr_min_tache;
    }

    public void setNbr_min_tache(int nbr_min_tache) {
        this.nbr_min_tache = nbr_min_tache;
    }

    public ArrayList<Badge> getBadge() {
        return badges;
    }

    public void setBadge(ArrayList<Badge> badges) {
        this.badges = badges;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setNbrFelicitation(int nbrFelicitation) {
        this.nbrFelicitation = nbrFelicitation;
    }
    public int getNbrFelicitation() {
        return nbrFelicitation;
    }
}

