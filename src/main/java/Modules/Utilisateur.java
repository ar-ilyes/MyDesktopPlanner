package Modules;


public class Utilisateur {
    private String pseudo;
    private Calendrier calendrier_perso;
    private Historique historique;
    private int nbr_min_tache;
    private Badge badge;
    private String motDePasse;
    public Utilisateur(String pseudo, Calendrier calendrier_perso, int nbr_min_tache, Badge badge, String motDePasse) {
        this.pseudo = pseudo;
        this.calendrier_perso = calendrier_perso;
        this.historique = new Historique();
        this.nbr_min_tache = nbr_min_tache;
        this.badge = badge;
        this.motDePasse = motDePasse;
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

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}

