package Modules;

import java.time.temporal.ChronoUnit;
import java.util.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;

public class Calendrier {
    private LocalDate periodeDebut;
    private LocalDate periodeFin;
    private HashMap<Integer, TacheSimple> tachesSimple;
    private HashMap<Integer, TacheDecompose> tachesDecompose;
    private HashMap<LocalDate, Journee> lesJournees;
    private LocalDate dateActuelle;
    private String heureActuelle;
    private Historique historique;
    private Utilisateur utilisateur;

    public Calendrier(LocalDate periodeDebut, LocalDate periodeFin,Historique historique) {
        this.periodeDebut = periodeDebut;
        this.periodeFin = periodeFin;
        this.tachesSimple = new HashMap<Integer, TacheSimple>();
        this.tachesDecompose = new HashMap<Integer, TacheDecompose>();
        this.lesJournees = new HashMap<LocalDate, Journee>();
        this.historique = historique;
        long daysBetween = ChronoUnit.DAYS.between(periodeDebut, periodeFin);

        for (int i = 0; i <= daysBetween; i++) {
            LocalDate currentDate = periodeDebut.plusDays(i);
            Journee journee = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),currentDate,this);
        }
        this.dateActuelle = LocalDate.now();
        this.heureActuelle = "";
    }

    public void setHistorique(Historique historique) {
        this.historique = historique;
    }

    public void introduirePeriode(LocalDate debut, LocalDate fin) {
        this.periodeDebut = debut;
        this.periodeFin = fin;
    }

    //getter and setter for utilisateur
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }



    //public void introduireTache(Tache tache) throws OutOfDateException {
    //    if (tache.getDate().isBefore(periodeDebut) || tache.getDate().isAfter(periodeFin)) {
    //      throw new OutOfDateException("La date de la tâche est en dehors de la période du calendrier.");
    //  }
    //  int tacheId = tache.getID();
    //  if (tache instanceof TacheSimple) {
    //      tachesSimple.put(tacheId, (TacheSimple)tache);
    //  } else if (tache instanceof TacheDecompose) {
    //      tachesDecompose.put(tacheId, (TacheDecompose)tache);
    //  }

    //  Journee journee = lesJournees.get(tache.getDate());
    //  if (journee == null) {
    //      journee = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),tache.getDate(),this);
    //      lesJournees.put(tache.getDate(), journee);
    //  }

    //  journee.introduireTache((TacheSimple)tache);
    //}

    public TacheSimple getTacheSimple(int tacheId) {
        return tachesSimple.get(tacheId);
    }

    public TacheDecompose getTacheDecompose(int tacheId) {
        return tachesDecompose.get(tacheId);
    }

    public HashMap<Integer, TacheSimple> getTachesSimple() {
        return tachesSimple;
    }

    public HashMap<Integer, TacheDecompose> getTachesDecompose() {
        return tachesDecompose;
    }
    public void ajouterTacheSimple(TacheSimple tacheSimple) {
        tachesSimple.put(tacheSimple.getID(), tacheSimple);
    }

    public void ajouterTacheDecompose(TacheDecompose tacheDecompose) {
        tachesDecompose.put(tacheDecompose.getID(), tacheDecompose);
    }

    public int NbrTachesRealisee() {
        int totalRendement = 0;
        for (HashMap.Entry<LocalDate, Journee> entry : lesJournees.entrySet()) {
            LocalDate journeeDate = entry.getKey();
            if (journeeDate.isBefore(periodeDebut) || journeeDate.isAfter(periodeFin)) {
                continue;
            }
            Journee journee = entry.getValue();
            totalRendement += journee.getRendement();
        }
        return totalRendement;
    }

    public double Rendement(){////////il faut prendre en consideration les taches decomposees
        return (double)this.NbrTachesRealisee()/(double)this.tachesSimple.size();
    }

    public HashMap<LocalDate, Journee> getLesJournees() {
        return lesJournees;
    }

    public LocalDate getPeriodeFin() {
        return periodeFin;
    }

    public Journee getJournee(LocalDate date) {
        return lesJournees.get(date);
    }
    public void removeDay(LocalDate date) {
        lesJournees.remove(date);
    }
    public void addDay(LocalDate date, Journee journee) {
        lesJournees.put(date, journee);
        /*if(date.isBefore(periodeDebut) && date.isAfter(periodeFin)){
           this.tachesSimple.putAll(journee.getTaches());
        }*/
    }

    public ArrayList<TacheSimple> planifierAuto(ArrayList<TacheSimple> taches){
        Comparator<TacheSimple> comparator = Comparator
                .comparing(TacheSimple::getPriorite)
                .thenComparing(TacheSimple::getDeadline);
        taches.sort(comparator);
        LocalDate dayDate=this.periodeDebut;
        ArrayList<TacheSimple> array_sugg=new ArrayList<TacheSimple>();
        for(TacheSimple tache : taches){
            if(dayDate.isBefore(this.periodeFin)){
                Journee sugDay=this.getJournee(dayDate);
                TacheSimple suggestion=sugDay.Suggest(tache);
                while(suggestion.getDate()==null){
                    dayDate=dayDate.plusDays(1);
                    if(dayDate.isBefore(this.periodeFin)){
                        sugDay=this.getJournee(dayDate);
                        suggestion=sugDay.Suggest(tache);
                    }else{
                        break;
                    }
                }
                array_sugg.add(suggestion);
                dayDate=dayDate.plusDays(1);
            }else{
                break;
            }
        }
        return array_sugg;
    }

    public boolean planifierAuto(TacheSimple tache){
        boolean stop=false;
        for (Journee jour : this.getLesJournees().values()){
            if(!stop){
                if(tache.getDuree()<jour.biggestDureeCreneau().getDuree()){
                    jour.introduireTacheAuto(tache);
                    stop=true;
                }
            }else {
                break;
            }
        }
        if(!stop){
            return false;
        }
        return true;
    }
    public boolean planifierAuto(TacheDecompose tache){
        Journee jour = this.getJournee(this.periodeDebut);
        return jour.introduireTacheAuto(tache);
    }
    public void setPeriodeFin(LocalDate periodeFin) {
        this.periodeFin = periodeFin;
    }
    public boolean etaleLaPeriode(ArrayList<TacheSimple> Taches){
        Comparator<TacheSimple> comparator = Comparator
                .comparing(TacheSimple::getPriorite)
                .thenComparing(TacheSimple::getDeadline);
        Taches.sort(comparator);
        LocalDate Daydate=this.periodeDebut;
        ArrayList<TacheSimple> TachesNonPlanifiees=new ArrayList<TacheSimple>();
        for(TacheSimple tache : Taches){
            Daydate = this.periodeDebut;
            if(Daydate.isBefore(this.periodeFin) || Daydate.isEqual(this.periodeFin)){
                Journee jour=this.getJournee(Daydate);
                while(jour.biggestDureeCreneau().getDuree()<tache.getDuree()){
                    Daydate=Daydate.plusDays(1);
                    if(Daydate.isBefore(this.periodeFin) || Daydate.isEqual(this.periodeFin)){
                        jour=this.getJournee(Daydate);
                    }else{
                        break;
                    }
                }
                if(jour.biggestDureeCreneau().getDuree()>tache.getDuree()) {
                    jour.introduireTacheAuto(tache);
                }else{
                    TachesNonPlanifiees.add(tache);
                }
            }else{
                break;
            }

        }

        TachesNonPlanifiees.sort(comparator);
        for(TacheSimple TacheNP : TachesNonPlanifiees){
            Journee jour=new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),Daydate,this);
            jour.introduireCreneau(new Creneau("08:00",jour.addMinutesToTime("08:00",TacheNP.getDuree())));
            jour.introduireTacheAuto(TacheNP);
            this.addDay(Daydate,jour);
            this.setPeriodeFin(Daydate);
            Daydate=Daydate.plusDays(1);
        }
        return true;
    }
    public boolean applySuggestions(ArrayList<TacheSimple> taches){
        Comparator<TacheSimple> comparator = Comparator
                .comparing(TacheSimple::getPriorite)
                .thenComparing(TacheSimple::getDeadline);
        taches.sort(comparator);
        for(TacheSimple tache : taches){
            if(tache.getDate()!=null){
                Journee jour=this.getJournee(tache.getDate());
               boolean bool= jour.introduireTacheAuto(tache);
                if(!bool){
                     return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }
    public boolean supprimerTache(TacheSimple tache){
        return this.getJournee(tache.getDate()).supprimerTache(tache);
    }
public void supprimerTache(TacheDecompose tache){
        for(TacheSimple partie : tache.getParties()){
            this.supprimerTache(partie);
        }
        this.getTachesDecompose().remove(tache.getID());
    }

    public boolean replanification(TacheSimple tache) {
        ArrayList<TacheSimple> ReplannedTaches = new ArrayList<TacheSimple>();
        ReplannedTaches.add(tache);
        for (TacheSimple t : this.getTachesSimple().values()) {
            if (!t.isBloqué()) {
                ReplannedTaches.add(t);
                this.supprimerTache(t);
            }
        }
        ArrayList<TacheSimple> Suggestions = this.planifierAuto(ReplannedTaches);
        if (Suggestions.size() == ReplannedTaches.size()) {
            return this.applySuggestions(Suggestions);
        } else {
            return false;//replanification impossible
        }
    }

    public void archiver(){
        this.historique.ajouterCalendrier(this);
    }

    public LocalDate getJourPlusRentable(){
        LocalDate date=this.periodeDebut;
        double max=0;
        for(Journee jour : this.getLesJournees().values()){
            if(jour.getRendement()>max){
                max=jour.getRendement();
                date=jour.getDate();
            }
        }
        return date;
    }

    public int getDureeCategorie(String categorie){
        int duree=0;
        for(TacheSimple tache : this.getTachesSimple().values()){
            if(tache.getCategorie().equals(categorie)){
                duree+=tache.getDuree();
            }
        }
        return duree;
    }

    public double getMoyenneDesRendements(){
        double moyenne=0;
        for(Journee jour : this.getLesJournees().values()){
            moyenne+=jour.getRendement();
        }
        return moyenne/this.getLesJournees().size();
    }

}



