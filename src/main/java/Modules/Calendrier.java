package Modules;

import java.time.temporal.ChronoUnit;
import java.util.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;

public class Calendrier implements Cloneable {
    private LocalDate periodeDebut;
    private LocalDate periodeFin;
    private HashMap<Integer, TacheSimple> tachesSimple;
    private HashMap<Integer, TacheDecompose> tachesDecompose;
    private HashMap<LocalDate, Journee> lesJournees;
    private LocalDate dateActuelle;
    private String heureActuelle;
    private Historique historique;//!!! useless
    private Utilisateur utilisateur;
    private ArrayList<Projet> projets;




    public Calendrier(LocalDate periodeDebut, LocalDate periodeFin,Historique historique) {
        this.periodeDebut = periodeDebut;
        this.periodeFin = periodeFin;
        this.tachesSimple = new HashMap<Integer, TacheSimple>();
        this.tachesDecompose = new HashMap<Integer, TacheDecompose>();
        this.lesJournees = new HashMap<LocalDate, Journee>();
        this.historique = historique;
       /* long daysBetween = ChronoUnit.DAYS.between(periodeDebut, periodeFin);

        for (int i = 0; i <= daysBetween; i++) {
            LocalDate currentDate = periodeDebut.plusDays(i);
            Journee journee = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),currentDate,this);
        }*/
        this.dateActuelle = LocalDate.now();
        this.heureActuelle = "";
        this.projets = new ArrayList<Projet>();
    }
    public void ajouterProjet(Projet projet) {
    	projets.add(projet);
    }
    public void supprimerProjet(Projet projet) {
    	projets.remove(projet);
    }
    public ArrayList<Projet> getProjets() {
    	return projets;
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
    public LocalDate getPeriodeDebut() {
        return periodeDebut;
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

    public ArrayList<TacheSimple> planifierAuto(ArrayList<Tache> taches) throws DeadLinePassed, WrongCreneauFormat{
        Comparator<Tache> comparator = Comparator
                .comparing(Tache::getPriorite)
                .thenComparing(Tache::getDeadline);
        taches.sort(comparator);
        // doing a copy of all creneaux inside days of periode and then put them inside a hashmap of arraylist of creneaux where the key is the date journne and the value is a copy (not reference) of arraylist of creneaux that are present in that day
        HashMap<LocalDate,ArrayList<Creneau>> creneauxSugg=new HashMap<LocalDate,ArrayList<Creneau>>();
        for (HashMap.Entry<LocalDate, Journee> entry : lesJournees.entrySet()) {
            LocalDate journeeDate = entry.getKey();
            if (journeeDate.isBefore(periodeDebut) || journeeDate.isAfter(periodeFin)) {
                continue;
            }
            Journee journee = entry.getValue();
            ArrayList<Creneau> creneauxJournee=new ArrayList<Creneau>();
            for(Creneau creneau : journee.getCreneauxLibres()){
                try {
                    creneauxJournee.add((Creneau) creneau.clone());
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
            creneauxSugg.put(journeeDate,creneauxJournee);
        }

        LocalDate dayDate=this.periodeDebut;
        ArrayList<TacheSimple> array_sugg=new ArrayList<TacheSimple>();

        for(Tache tache : taches){
            //condition if tache is instance of tachesimple
            if((tache instanceof TacheSimple)){

            dayDate=this.periodeDebut;

            Journee sugDay=this.getJournee(dayDate);
            TacheSimple suggestion=sugDay.Suggest((TacheSimple) tache,creneauxSugg.get(dayDate),dayDate);
            while(suggestion.getDate()==null){
                dayDate=dayDate.plusDays(1);
                if(dayDate.isBefore(this.periodeFin) || dayDate.isEqual(this.periodeFin)){
                    sugDay=this.getJournee(dayDate);
                    suggestion=sugDay.Suggest((TacheSimple)tache,creneauxSugg.get(dayDate),dayDate);
                }else{
                    break;
                }
            }
            if(suggestion.getDate()!=null){
                array_sugg.add(suggestion);
            }
        }else{
            Journee sugDay=this.getJournee(this.periodeDebut);
            ArrayList<TacheSimple> suggParties=sugDay.SuggestDecom((TacheDecompose) tache,creneauxSugg);
            if (suggParties!=null){//it can return null when not all parties are suggested
            array_sugg.addAll(suggParties);
            }
            }
        }
        return array_sugg;
    }

    public boolean planifierAuto(TacheSimple tache) throws DeadLinePassed , WrongCreneauFormat{
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
    public boolean planifierAuto(TacheDecompose tache) throws DeadLinePassed,WrongCreneauFormat{
        Journee jour = this.getJournee(this.periodeDebut);
        return jour.introduireTacheAuto(tache);
    }
    public void setPeriodeFin(LocalDate periodeFin) {
        this.periodeFin = periodeFin;
    }
    public void setPeriodeDebut(LocalDate periodeDebut) {
        this.periodeDebut = periodeDebut;
    }
    public boolean etaleLaPeriode(ArrayList<Tache> Taches) throws DeadLinePassed,WrongCreneauFormat{
        Comparator<Tache> comparator = Comparator
                .comparing(Tache::getPriorite)
                .thenComparing(Tache::getDeadline);
        Taches.sort(comparator);
        LocalDate Daydate=this.periodeDebut;
        LocalDate Maxdate=this.periodeDebut;
        ArrayList<Tache> TachesNonPlanifiees=new ArrayList<Tache>();
        for(Tache tache : Taches){
            Daydate = this.periodeDebut;
            if(tache instanceof TacheSimple) {
                if (Daydate.isBefore(this.periodeFin) || Daydate.isEqual(this.periodeFin)) {
                    Journee jour = this.getJournee(Daydate);
                    while (jour.biggestDureeCreneau() != null &&jour.biggestDureeCreneau().getDuree() < tache.getDuree()) {
                        Daydate = Daydate.plusDays(1);
                        if (Daydate.isBefore(this.periodeFin) || Daydate.isEqual(this.periodeFin)) {
                            jour = this.getJournee(Daydate);
                            if(Daydate.isAfter(Maxdate)){
                                Maxdate=Daydate;
                            }
                        } else {
                            break;
                        }
                    }
                    if (jour.biggestDureeCreneau()!= null && jour.biggestDureeCreneau().getDuree() >= tache.getDuree()) {
                        jour.introduireTacheAuto((TacheSimple) tache);
                    } else {
                        TachesNonPlanifiees.add(tache);
                    }
                } else {
                    break;
                }
            }else{
                Journee jour = this.getJournee(Daydate);
                if(Daydate.isAfter(Maxdate)){
                    Maxdate=Daydate;
                }
                jour.introduireTacheAuto((TacheDecompose) tache);
                ArrayList<Tache> lesparties=new ArrayList<>();
                while(tache.getDuree()-((TacheDecompose) tache).getPeriodeDesParties()>=480){
                    lesparties.add(((TacheDecompose) tache).decomposer(480));
                }
                if(tache.getDuree()-((TacheDecompose) tache).getPeriodeDesParties()!=0){
                    lesparties.add(((TacheDecompose) tache).decomposer(tache.getDuree()-((TacheDecompose) tache).getPeriodeDesParties()));
                }
                TachesNonPlanifiees.addAll(lesparties);

            }
        }
        System.out.println("day li lha9nalou"+Maxdate);
        Maxdate=Maxdate.plusDays(1);
        TachesNonPlanifiees.sort(comparator);
        for(Tache TacheNP : TachesNonPlanifiees){
            Journee jour=new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),Maxdate,this);
            jour.introduireCreneau(new Creneau("08:00",jour.addMinutesToTime("08:00",TacheNP.getDuree())));
            this.addDay(Maxdate,jour);
            this.getJournee(Maxdate).introduireTacheAuto((TacheSimple) TacheNP);
            this.setPeriodeFin(Maxdate);
            Maxdate=Maxdate.plusDays(1);
        }

        return true;
    }
    public boolean applySuggestions(ArrayList<TacheSimple> taches) throws DeadLinePassed{
        Comparator<TacheSimple> comparator = Comparator
                .comparing(TacheSimple::getPriorite)
                .thenComparing(TacheSimple::getDeadline);
        taches.sort(comparator);
        for(TacheSimple tache : taches){
            if(tache.getDate()!=null){
                Journee jour=this.getJournee(tache.getDate());
               //boolean bool= jour.introduireTacheAuto(tache);
                boolean bool= jour.introduireTacheManuelle(tache,tache.getCreneau());
                if(!bool){
                     return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }
    public boolean supprimerTache(TacheSimple tache)throws WrongCreneauFormat{
        return this.getJournee(tache.getDate()).supprimerTache(tache);
    }
public void supprimerTache(TacheDecompose tache){
        for(TacheSimple partie : tache.getParties()){
            this.supprimerTache(partie);
        }
        this.getTachesDecompose().remove(tache.getID());
    }

    public boolean replanification(TacheSimple tache) throws DeadLinePassed,WrongCreneauFormat {
        ArrayList<Tache> ReplannedTaches = new ArrayList<Tache>();
        ArrayList<Tache> BeforeReplanificationTaches = new ArrayList<Tache>();
        ReplannedTaches.add(tache);
        for (TacheSimple t : this.getTachesSimple().values()) {
            if (!t.isBloqué()) {
                BeforeReplanificationTaches.add(t);
                try {
                    ReplannedTaches.add(t.clone());
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                this.supprimerTache(t);
            }
        }
        ArrayList<TacheSimple> Suggestions = this.planifierAuto(ReplannedTaches);
        if (Suggestions.size() >= ReplannedTaches.size()) {
            return this.applySuggestions(Suggestions);
        } else {
            //replan all the tasks before replanification manually with methode introduireTachemanuelle
            for (Tache t : BeforeReplanificationTaches) {
                Journee jour = this.getJournee(t.getDate());
                jour.introduireTacheManuelle((TacheSimple) t, ((TacheSimple) t).getCreneau());
            }
            return false;//replanification impossible
        }
    }

    public void archiver(){
        try {
            this.getUtilisateur().getHistorique().ajouterCalendrier((Calendrier) this.clone());
            //remove all Journee and TacheSimple and TacheDecompose from this
            this.getLesJournees().clear();
            this.getTachesSimple().clear();
            this.getTachesDecompose().clear();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

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
            if(tache.getCategorie().getNom().equals(categorie)){
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
    public void setLesJournees(HashMap<LocalDate, Journee> lesJournees) {
        this.lesJournees = lesJournees;
    }
    protected Object clone() throws CloneNotSupportedException {
        Calendrier calendrier = (Calendrier) super.clone();
        calendrier.setLesJournees(new HashMap<LocalDate, Journee>());
        for (Journee jour : this.getLesJournees().values()) {
            Journee journee = (Journee) jour.clone();
            journee.setCalendrierSuper(calendrier);
            calendrier.addDay(jour.getDate(), journee);
        }
        return calendrier;
    }
    public Projet getProjet(int ID){
        //search for the project in the projects of the calendar with this ID
        for(Projet projet : this.getProjets()) {
            if (projet.getID() == ID) {
                return projet;
            }
        }
        return null;
    }
    public Projet getProjet(String nom){
        //search for the project in the projects of the calendar with this ID
        for(Projet projet : this.getProjets()) {
            if (projet.getNom().equals(nom)) {
                return projet;
            }
        }
        return null;
    }
}



