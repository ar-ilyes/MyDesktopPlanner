package Modules;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Journee implements Cloneable , java.io.Serializable {
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
    //une methode qui boucle surtout les creneaux et si elle trouve deux qui se chevauchent elle les fusionne exemple : 10h-11h et 11h-12h devient un seul creneau 10h-12h
    public void fusionnerCreneaux(){
        for (Creneau c : this.creneauxLibres){
            for (Creneau c2 : this.creneauxLibres){
                if (c.getFin().equals(c2.getDebut())){
                    c.setFin(c2.getFin());
                    this.creneauxLibres.remove(c2);
                    return;
                }
            }
        }
    }

    // méthode pour ajouter un créneau libre au tableau de créneaux libres
    public void introduireCreneau(Creneau creneau) throws WrongCreneauFormat {
        for(MinHeure demiHeure : this.lesMinHeures) {
            if(creneau.Include(demiHeure.getHeure())) {
                demiHeure.setDansCreneau(true);
                demiHeure.setLibre(true);
            }
        }
        for (Creneau c : this.creneauxLibres){//le cas de chevauchement entre les creneaux existants et le nouveau creneau
            LocalTime Cdebut = LocalTime.parse(c.getDebut());
            LocalTime Cfin = LocalTime.parse(c.getFin());
            LocalTime Creneaudebut = LocalTime.parse(creneau.getDebut());
            LocalTime Creneaufin = LocalTime.parse(creneau.getFin());
            if(c.getFin().equals(creneau.getDebut())){
                c.setFin(creneau.getFin());
                fusionnerCreneaux();
                return;
            }else if (c.getDebut().equals(creneau.getFin())){
                c.setDebut(creneau.getDebut());
                fusionnerCreneaux();
                return;
            }else if (Cdebut.isBefore(Creneaudebut) && Cfin.isAfter(Creneaufin)){
                fusionnerCreneaux();
                return;
            }else if (Cdebut.isAfter(Creneaudebut) && Cfin.isBefore(Creneaufin)){
                c.setDebut(creneau.getDebut());
                c.setFin(creneau.getFin());
                fusionnerCreneaux();
                return;
        }}
        this.creneauxLibres.add(creneau);
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

    public boolean introduireTacheAuto(TacheSimple tache) throws DeadLinePassed , WrongCreneauFormat{
        //loop over creneaux libres and find the creneau that has Duree > tache.getDuree()
        if(this.date.isAfter(tache.getDeadline())){
            tache.setEtat(Etat.Unscheduled);
            throw new DeadLinePassed();
            //return false;///the deadline is before the date of the day

        }
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
                            tache.setEtat(Etat.Unscheduled);
                            return false;
                        }
                    }else{
                        this.calendrierSuper.ajouterTacheSimple(tache);
                        return true;
                    }
                }else{
                    setLesMinHeuresLibre(tache);
                    this.calendrierSuper.ajouterTacheSimple(tache);
                    return true;
                }

            }
        }
        tache.setEtat(Etat.Unscheduled);
        return false;
    }

   public boolean introduireTacheManuelle(TacheSimple tache,Creneau creneau) throws DeadLinePassed{
        //verify if this creneaux is inside a creneau in creneauxLibres
       if(this.date.isAfter(tache.getDeadline())) {
           tache.setEtat(Etat.Unscheduled);
           throw new DeadLinePassed();
           //return false;///the deadline is before the date of the day
       }
        for(Creneau c : this.creneauxLibres){
            if(c.getDebut().compareTo(creneau.getDebut())<=0 && c.getFin().compareTo(creneau.getFin())>=0){
                this.creneauxLibres.remove(c);
                if(c.getDebut().compareTo(creneau.getDebut())==0){
                    c.setDebut(creneau.getFin());
                    if(c.getDebut().compareTo(c.getFin())!=0){
                    this.creneauxLibres.add(c);
                    }
                }else if(c.getFin().compareTo(creneau.getFin())==0){
                    c.setFin(creneau.getDebut());
                    if(c.getDebut().compareTo(c.getFin())!=0){
                    this.creneauxLibres.add(c);
                    }
                }else{
                    Creneau c1 = new Creneau(c.getDebut(),creneau.getDebut());
                    Creneau c2 = new Creneau(creneau.getFin(),c.getFin());
                    this.creneauxLibres.add(c1);
                    this.creneauxLibres.add(c2);
                }
                tache.setCreneau(creneau);
                tache.setDate(this.date);
                this.taches.put(tache.getID(),tache);
                //=======!!! update les DemiHeures avec libre=false !!!=======
                if(tache.getPeriodicite()!=0){
                    LocalDate dateSuivante = this.getDate().plusDays(tache.getPeriodicite());
                    if(dateSuivante.compareTo(this.getCalendrierSuper().getPeriodeFin())<=0){
                        if(this.getCalendrierSuper().getLesJournees().containsKey(dateSuivante)){
                            Journee journeeSuivante = this.getCalendrierSuper().getLesJournees().get(dateSuivante);
                            return journeeSuivante.introduireTacheAuto(tache);
                        }else{
                            System.out.println("La tache ne peut pas etre introduite car la journee suivante n'existe pas");
                            tache.setEtat(Etat.Unscheduled);
                            return false;
                        }
                    }else{
                        this.calendrierSuper.ajouterTacheSimple(tache);
                        return true;
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

   public boolean introduireTacheAuto(TacheDecompose tache) throws DeadLinePassed{
        //LocalDate jourDate = this.getDate();
        //while(jourDate.compareTo(this.getCalendrierSuper().getPeriodeFin())<=0){
        //    Journee jour = this.calendrierSuper.getJournee(jourDate);
        //    if(!jour.getCreneauxLibres().isEmpty());
        //    }
       if (this.getDate().isAfter(tache.getDeadline())) {
           tache.setEtat(Etat.Unscheduled);
           throw new DeadLinePassed();
           //return false;///the deadline is before the date of the day
       }
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

   public TacheSimple Suggest(TacheSimple tache,ArrayList<Creneau> creneauxLibresSugg,LocalDate dateSugg) {
       tache.setDate(null);
        for (Creneau creneau : creneauxLibresSugg){
            if (tache.getDuree() <= creneau.getDuree()) {
                tache.setDate(dateSugg);
                Creneau creneauTmp=new Creneau(creneau.getDebut(),addMinutesToTime(creneau.getDebut(),tache.getDuree()));
                creneauTmp.setDebut(creneauTmp.getDebut());
                creneauTmp.setFin(addMinutesToTime(creneau.getDebut(),tache.getDuree()));
                tache.setCreneau(creneauTmp);
                creneau.setDebut(addMinutesToTime(creneau.getDebut(),tache.getDuree()));
                if(creneau.getDebut().equals(creneau.getFin())){
                    creneauxLibresSugg.remove(creneau);
                }
                break;
            }
        }
        return tache;
   }
   public ArrayList<TacheSimple> SuggestDecom(TacheDecompose tache , HashMap<LocalDate,ArrayList<Creneau>> Allcreneaux){
        ArrayList<TacheSimple> suggestions = new ArrayList<>();
        //loop over daysdate and their creneaux in Allcreneaux hashmap
       LocalDate date=this.calendrierSuper.getPeriodeDebut();
        while(date.compareTo(this.calendrierSuper.getPeriodeFin())<=0){
            Iterator<Creneau> iterator = Allcreneaux.get(date).iterator();
            while (iterator.hasNext()) {
                Creneau creneau = iterator.next();
                if(creneau.getDuree()>tache.getDuree()-tache.getPeriodeDesParties()){
                    TacheSimple partie = tache.decomposer(tache.getDuree()-tache.getPeriodeDesParties());
                    partie.setDate(date);
                    Creneau creneautmp=new Creneau(creneau.getDebut(),addMinutesToTime(creneau.getDebut(),partie.getDuree()));
                    creneautmp.setDebut(creneau.getDebut());
                    creneautmp.setFin(addMinutesToTime(creneau.getDebut(),partie.getDuree()));
                    partie.setCreneau(creneautmp);
                    suggestions.add(partie);
                    creneau.setDebut(addMinutesToTime(creneau.getDebut(),partie.getDuree()));
                    if(creneau.getDebut().equals(creneau.getFin())){
                        iterator.remove(); // Safely remove the item
                    }
                    return suggestions;
                }else{
                    TacheSimple partie = tache.decomposer(creneau.getDuree());
                    partie.setDate(date);
                    Creneau creneautmp=new Creneau(creneau.getDebut(),addMinutesToTime(creneau.getDebut(),partie.getDuree()));
                    creneautmp.setDebut(creneau.getDebut());
                    creneautmp.setFin(addMinutesToTime(creneau.getDebut(),partie.getDuree()));
                    partie.setCreneau(creneautmp);
                    suggestions.add(partie);
                    creneau.setDebut(addMinutesToTime(creneau.getDebut(),partie.getDuree()));
                    if(creneau.getDebut().equals(creneau.getFin())){
                        iterator.remove(); // Safely remove the item
                    }

                }
            }
            date=date.plusDays(1);
        }
        tache.setParties(new ArrayList<TacheSimple>());
        tache.setPeriodeDesParties(0);
        return null;
   }
   public Creneau biggestDureeCreneau(){
        if(this.getCreneauxLibres().size()==0){
            return null;
        }
        Creneau temp = this.getCreneauxLibres().get(0);
        for(Creneau creneau : this.getCreneauxLibres()){
            if(creneau.getDuree()>temp.getDuree()){
                temp=creneau;
            }
        }
        return temp;
   }

   public boolean supprimerTache(TacheSimple tache) throws WrongCreneauFormat {
        if(this.taches.containsKey(tache.getID())){
            this.introduireCreneau(tache.getCreneau());
            this.taches.remove(tache.getID());
            this.calendrierSuper.getTachesSimple().remove(tache.getID());
            return true;
        }else{
            return false;
        }
   }

   public int getNbTachesRealisees(){
        int nb=0;
        for(TacheSimple tache : this.getTaches().values()){
            if(tache.getEtat()==Etat.Completed){
                nb++;
            }
        }//////maybe we need to exlude les parties des tacheDecomposees
        return nb;
   }

    // méthode pour calculer le rendement de la journée (nombre de tâches complétées/nombre de tâches planifiées)
    public double getRendement(){////////il faut prendre en consideration les taches decomposees
        return (double)this.getNbTachesRealisees()/(double)this.taches.size();
    }

    public void setCompletedTache(TacheSimple tache){
        this.taches.get(tache.getID()).setEtat(Etat.Completed);
        if(this.getNbTachesRealisees()>=this.calendrierSuper.getUtilisateur().getNbr_min_tache()){
            this.calendrierSuper.getUtilisateur().setNbrFelicitation(this.calendrierSuper.getUtilisateur().getNbrFelicitation()+1);

        }
        if(this.calendrierSuper.getUtilisateur().getNbrFelicitation()==5){
            this.calendrierSuper.getUtilisateur().getBadge().add(new Badge("Good"));
            this.calendrierSuper.getBadges().add(new Badge("Good"));
            this.calendrierSuper.getUtilisateur().setNbrFelicitation(0);

        }
        //if the user have 3 badges Good he will get a badge Verygood, and we remove the good badges
        if (this.calendrierSuper.getUtilisateur().getBadge().size()>=3){
            int nbGood=0;
            for(Badge badge : this.calendrierSuper.getUtilisateur().getBadge()){
                if(badge.getNom().equals("Good")){
                    nbGood++;
                }
            }
            if(nbGood>=3){
                this.calendrierSuper.getUtilisateur().getBadge().add(new Badge("VeryGood"));
                this.calendrierSuper.getBadges().add(new Badge("VeryGood"));
                    for(Badge b : this.calendrierSuper.getUtilisateur().getBadge()){
                        if(b.getNom().equals("Good")){
                            this.calendrierSuper.getUtilisateur().getBadge().remove(b);

                        }
                    }
                    for(Badge b : this.calendrierSuper.getBadges()){
                        if(b.getNom().equals("Good")){
                            this.calendrierSuper.getBadges().remove(b);
                        }
                    }


            }
        }

        //if the user have 3 badges VeryGood he will get a badge Excellent, and we remove the VeryGood badges
        if (this.calendrierSuper.getUtilisateur().getBadge().size()>=3){
            int nbVeryGood=0;
            for(Badge badge : this.calendrierSuper.getUtilisateur().getBadge()){
                if(badge.getNom().equals("VeryGood")){
                    nbVeryGood++;
                }
            }
            if(nbVeryGood>=3){
                this.calendrierSuper.getUtilisateur().getBadge().add(new Badge("Excellent"));
                this.calendrierSuper.getBadges().add(new Badge("Excellent"));
                for(Badge b : this.calendrierSuper.getUtilisateur().getBadge()){
                    if(b.getNom().equals("VeryGood")){
                        this.calendrierSuper.getUtilisateur().getBadge().remove(b);

                    }
                }
                for(Badge b : this.calendrierSuper.getBadges()){
                    if(b.getNom().equals("VeryGood")){
                        this.calendrierSuper.getBadges().remove(b);
                    }
                }
            }
        }

    }
    public Journee clone() throws CloneNotSupportedException {
        Journee clone = (Journee) super.clone();
        clone.taches = new HashMap<>();
        for (TacheSimple tache : this.taches.values()) {
            clone.taches.put(tache.getID(), (TacheSimple) tache.clone());
        }
        return clone;
    }
}


