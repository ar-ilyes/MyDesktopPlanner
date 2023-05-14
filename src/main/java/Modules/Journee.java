package Modules;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Journee {
    private HashMap<Integer,TacheSimple> taches; // HashMap d'objets de la classe TacheSimple
    private ArrayList<Creneau> creneauxLibres; // tableau d'objets de la classe Creneau
    private LocalDate date; // représente la date de la journée sous forme de chaîne de caractères (comme 2023-04-30)
    private ArrayList<MinHeure> lesMinHeures;
    private Calendrier calendrierSuper;

    public Journee(HashMap<Integer,TacheSimple> taches, ArrayList<Creneau> creneauxLibres, LocalDate date, Calendrier calendrierSuper) {
        this.taches = taches;
        this.creneauxLibres = creneauxLibres;
        this.date = date;
        this.calendrierSuper = calendrierSuper;
        this.lesMinHeures = new ArrayList<MinHeure>();

        LocalTime currentTime = LocalTime.of(0, 0); // Start at 00:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 0; i < 48; i++) { // 48 half-hour increments in a day
            currentTime = currentTime.plusMinutes(30); // Increment by 30 minutes
            // ====!!!!! maybe change it to make it variable !!!====
            String currentTimeString = currentTime.format(formatter);
            boolean libre = true;
            boolean dansCreneau = false;
            for (Creneau creneau : this.creneauxLibres) {
                if (creneau.Include(currentTimeString)) {
                    dansCreneau = true;
                    break;
                }
            }
            this.lesMinHeures.add(new MinHeure(currentTimeString, libre, dansCreneau));
        }
    }

    // getters et setters pour les attributs
    public ArrayList<MinHeure> getLesDemiHeures() {
        return this.lesMinHeures;
    }

    public void setLesDemiHeures(ArrayList<MinHeure> lesDemiHeures) {
        this.lesMinHeures = lesDemiHeures;
    }

    public HashMap<Integer,TacheSimple> getTaches() {
        return taches;
    }

    public void setTaches(HashMap<Integer,TacheSimple> taches) {
        this.taches = taches;
    }

    public ArrayList<Creneau> getCreneauxLibres() {
        return creneauxLibres;
    }

    public void setCreneauxLibres(ArrayList<Creneau> creneauxLibres) {
        this.creneauxLibres = creneauxLibres;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Calendrier getCalendrierSuper() {
        return calendrierSuper;
    }

    public void setCalendrierSuper(Calendrier calendrierSuper) {
        this.calendrierSuper = calendrierSuper;
    }

    // méthode pour ajouter une tâche au HashMap de tâches
    public void introduireTache(TacheSimple tache) {
        this.taches.put(tache.getID(), tache);
    }

    // méthode pour ajouter un créneau libre au tableau de créneaux libres
    public void introduireCreneau(Creneau creneau) {
        for(MinHeure demiHeure : this.lesMinHeures) {
            if(creneau.Include(demiHeure.getHeure())) {
                demiHeure.setDansCreneau(true);
                demiHeure.setLibre(true);
            }
        }
        this.creneauxLibres.add(creneau);
    }

    // méthode pour calculer le rendement de la journée (nombre de tâches complétées)
    public int rendement() {
        int nbTachesCompletes = 0;
        for(TacheSimple tache : this.taches.values()) {
            if(tache.isComplete()) {
                nbTachesCompletes++;
            }
        }
        return nbTachesCompletes;
    }
    public static String addMinutesToTime(String time, int minutesToAdd) {
        // Parse the input time string into hours and minutes
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        // Add the minutes to the current time
        minutes += minutesToAdd;
        hours += minutes / 60;
        minutes %= 60;

        // Format the updated time as a string
        String updatedTime = String.format("%02d:%02d", hours, minutes);
        return updatedTime;
    }
    private void setLesMinHeuresLibre(TacheSimple tache){
        for(MinHeure demiHeure : this.lesMinHeures){
            if(tache.getCreneau().Include(demiHeure.getHeure())){
                demiHeure.setLibre(false);
                if(tache.isBloqué()){
                    demiHeure.setBloque(true);
                }
            }
        }
    }

    public boolean introduireTacheAuto(TacheSimple tache){
        //loop over creneaux libres and find the creneau that has Duree > tache.getDuree()
        for (Creneau creneau : this.creneauxLibres){
            if (creneau.getDuree() >= tache.getDuree()){
                this.creneauxLibres.remove(creneau);//and we re-put it in the list after we update its debut and fin
                tache.setCreneau(new Creneau(creneau.getDebut(),addMinutesToTime(creneau.getDebut(),tache.getDuree())));
                tache.setDate(this.date);
                this.taches.put(tache.getID(),tache);
                creneau.setDebut(addMinutesToTime(creneau.getDebut(),tache.getDuree()));
                if(creneau.getDebut().compareTo(creneau.getFin())==0){// if the debut is Fin we don't re-put creneau in arrayList creneauxLibres
                        this.creneauxLibres.remove(creneau);
                }
                else{
                    this.introduireCreneau(creneau);
                }
                //=======!!! update les DemiHeures avec libre=false !!!=======
                if(tache.getPeriodicite()!=0){
                    LocalDate dateSuivante = this.getDate().plusDays(tache.getPeriodicite());
                    if(dateSuivante.compareTo(this.getCalendrierSuper().getPeriodeFin())<=0){
                        if(this.getCalendrierSuper().getLesJournees().containsKey(dateSuivante)){
                            Journee journeeSuivante = this.getCalendrierSuper().getLesJournees().get(dateSuivante);
                            return journeeSuivante.introduireTacheAuto(tache);
                        }else{
                            System.out.println("La tache ne peut pas etre introduite car la journee suivante n'existe pas");
                            return false;
                        }
                    }else{
                        System.out.println("La tache ne peut pas etre introduite car la date suivante est hors de la periode");
                        return false;
                    }
                }else{
                    setLesMinHeuresLibre(tache);
                    this.calendrierSuper.ajouterTacheSimple(tache);
                    return true;
                }

            }
        }
        return false;
    }

   public boolean introduireTacheManuelle(TacheSimple tache){
        return true;// ====!!! à faire plus tard !!!====
   }

   public boolean introduireTacheAuto(TacheDecompose tache){
        //LocalDate jourDate = this.getDate();
        //while(jourDate.compareTo(this.getCalendrierSuper().getPeriodeFin())<=0){
        //    Journee jour = this.calendrierSuper.getJournee(jourDate);
        //    if(!jour.getCreneauxLibres().isEmpty());
        //    }
       if(this.getDate().compareTo(this.getCalendrierSuper().getPeriodeFin())<=0){
           if(!this.getCreneauxLibres().isEmpty()){
               Creneau creneau=this.biggestDureeCreneau();
               if((tache.getDuree()-tache.getPeriodeDesParties())<creneau.getDuree()){
                   TacheSimple partie = tache.decomposer(tache.getDuree()-tache.getPeriodeDesParties());
                   this.introduireTacheAuto(partie);
                   this.calendrierSuper.ajouterTacheDecompose(tache);
                   return true;
                }else{
                   TacheSimple partie = tache.decomposer(creneau.getDuree());
                   this.introduireTacheAuto(partie);
                   //planifier pour le prochain jour
                   if(this.getDate().plusDays(1).compareTo(this.getCalendrierSuper().getPeriodeFin())<=0){
                        Journee joursuiv = this.getCalendrierSuper().getJournee(this.getDate().plusDays(1));
                        this.calendrierSuper.removeDay(joursuiv.getDate());
                        boolean temp=joursuiv.introduireTacheAuto(tache);
                        this.calendrierSuper.addDay(joursuiv.getDate(),joursuiv);
                        return temp;
                   }else{
                        return false;
                   }
               }
           }else{
               Journee joursuiv = this.getCalendrierSuper().getJournee(this.getDate().plusDays(1));
               this.calendrierSuper.removeDay(joursuiv.getDate());
               boolean temp=joursuiv.introduireTacheAuto(tache);
               this.calendrierSuper.addDay(joursuiv.getDate(),joursuiv);
               return temp;
           }
        }else{
           return false;
       }
   }

   public TacheSimple Suggest(TacheSimple tache) {
        Creneau creneau = this.biggestDureeCreneau();
       if (tache.getDuree() < creneau.getDuree()) {
           tache.setDate(this.getDate());
       }else{
           tache.setDate(null);
       }
       return tache;
   }
   public Creneau biggestDureeCreneau(){
        Creneau temp = this.getCreneauxLibres().get(0);
        for(Creneau creneau : this.getCreneauxLibres()){
            if(creneau.getDuree()>temp.getDuree()){
                temp=creneau;
            }
        }
        return temp;
   }

   public boolean supprimerTache(TacheSimple tache){
        if(this.taches.containsKey(tache.getID())){
            this.introduireCreneau(tache.getCreneau());
            this.taches.remove(tache.getID());
            this.calendrierSuper.getTachesSimple().remove(tache.getID());
            return true;
        }else{
            return false;
        }
   }
}


