package Modules;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Creneau {
    private String debut; // représente l'heure de début réelle du créneau sous forme de chaîne de caractères (comme 11:30)
    private String fin; // représente l'heure de fin réelle du créneau sous forme de chaîne de caractères (comme 11:30)
    private static int min = 30; // période de temps en minutes qui est égale à 30

    public Creneau(String debut, String fin) {
        this.debut = debut;
        this.fin = fin;
    }

    // getters et setters pour les attributs
    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        if(isValidHour(debut)) {
            if(isMultipleOfMin(debut)) {
                this.debut = debut;
            } else {
                this.debut = getNextMultipleOfMin(debut);
            }
        } else {
            System.out.println("L'heure de début n'est pas dans le format attendu (hh:mm)");
        }
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        if(isValidHour(fin)) {
            if(isMultipleOfMin(fin)) {
                this.fin = fin;
            } else {
                this.fin = getNextMultipleOfMin(fin);
            }
        } else {
            System.out.println("L'heure de fin n'est pas dans le format attendu (hh:mm)");
        }
    }

    public static int getMin() {
        return min;
    }

    public static void setMin(int min) {
        Creneau.min = min;
    }

    // méthode pour vérifier si l'heure est un multiple de min
    private boolean isMultipleOfMin(String heure) {
        String[] heureMin = heure.split(":");
        int minutes = Integer.parseInt(heureMin[1]);
        return (minutes == 0 || minutes == min);
    }

    // méthode pour vérifier si l'heure est dans le format attendu
    private boolean isValidHour(String heure) {
        return heure.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    }

    // méthode pour obtenir la prochaine heure multiple de min après l'heure donnée
    private String getNextMultipleOfMin(String heure) {//==========!!! work in case of 30 min !!!============
        String[] heureMin = heure.split(":");
        int heures = Integer.parseInt(heureMin[0]);
        int minutes = Integer.parseInt(heureMin[1]);
        if(minutes > min) {
            heures += 1;
            minutes = 0;
        }else{
            minutes = min;
        }
        return String.format("%02d", heures) + ":" + String.format("%02d", minutes);
    }
    public int getDuree() {
        String[] heureMinDebut = this.debut.split(":");
        String[] heureMinFin = this.fin.split(":");
        int heuresDebut = Integer.parseInt(heureMinDebut[0]);
        int minutesDebut = Integer.parseInt(heureMinDebut[1]);
        int heuresFin = Integer.parseInt(heureMinFin[0]);
        int minutesFin = Integer.parseInt(heureMinFin[1]);
        return (heuresFin - heuresDebut) * 60 + (minutesFin - minutesDebut);
    }
    public boolean Include(String Hour){
        LocalTime beginning = LocalTime.parse(this.debut, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(this.fin, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime time = LocalTime.parse(Hour, DateTimeFormatter.ofPattern("HH:mm"));
        return !time.isBefore(beginning) && !time.isAfter(end);
    }
}

