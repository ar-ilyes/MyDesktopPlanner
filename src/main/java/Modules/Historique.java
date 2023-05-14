package Modules;

import java.util.ArrayList;

/**
 * Décrivez votre classe Historique ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Historique
{
    ArrayList<Calendrier> calendriers;
    public Historique() {}
    public Historique(ArrayList<Calendrier> calendriers) {
        this.calendriers = calendriers;
    }
    public void ajouterCalendrier(Calendrier calendrier) {
        calendriers.add(calendrier);
    }
    public void supprimerCalendrier(Calendrier calendrier) {
        calendriers.remove(calendrier);
    }
    public ArrayList<Calendrier> getCalendriers() {
        return calendriers;
    }
    public void setCalendriers(ArrayList<Calendrier> calendriers) {
        this.calendriers = calendriers;
    }



}