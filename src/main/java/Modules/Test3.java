package Modules;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Test3 {
    public static void main(String[] args) {
        MyPlannerApp app = new MyPlannerApp();
        LocalDate debutPeriode = LocalDate.of(2020, 1, 1);
        LocalDate finPeriode = LocalDate.of(2020, 1, 2);
        //the calendar of the user
        Calendrier calendrier = new Calendrier(debutPeriode,finPeriode,new Historique());

        //the user
        Utilisateur user = new Utilisateur("test",calendrier, 0, new Badge(), "test_password");
        calendrier.setHistorique(user.getHistorique());
        app.getUsers().put("test", user);
        app.setCurrentUser(user);

        //the 1st day and its creneaux and tasks
        Creneau creneau1 = new Creneau("08:30","09:30");
        Creneau creneau2 = new Creneau("14:30","15:30");
        Journee jour = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 1),calendrier);
        jour.introduireCreneau(creneau1);
        jour.introduireCreneau(creneau2);

        //the 2nd day and its creneaux and tasks
        Creneau creneau2_1 = new Creneau("08:30","09:30");
        Creneau creneau2_2 = new Creneau("14:00","14:30");
        Journee jour2 = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 2),calendrier);
        jour2.introduireCreneau(creneau2_1);
        jour2.introduireCreneau(creneau2_2);


        calendrier.addDay(jour.getDate(),jour);
        calendrier.addDay(jour2.getDate(),jour2);

        //the tasks
        TacheSimple tache1 = new TacheSimple("test2Tache", 130, creneau1, Priorite.LOW, LocalDate.of(2020, 1, 1), new Categorie("Study",Couleur.BLANC), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, 0);
        TacheSimple tache2 = new TacheSimple("test2Tache2", 50, creneau2, Priorite.LOW, LocalDate.of(2020, 1, 1), new Categorie("Study",Couleur.BLANC), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, 0);
        TacheSimple tache3 = new TacheSimple("test2Tache3", 100, creneau2, Priorite.LOW, LocalDate.of(2020, 1, 1), new Categorie("Study",Couleur.BLANC), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, 0);
        TacheDecompose tacheDecompose=new TacheDecompose("testTacheDecompose", 400, creneau1, Priorite.LOW, LocalDate.of(2020, 1, 1), new Categorie("Study",Couleur.BLANC), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, new ArrayList<TacheSimple>(),0);

        ArrayList<TacheSimple> taches = new ArrayList<TacheSimple>();
        taches.add(tache1);
        taches.add(tache2);
        taches.add(tache3);

        //ArrayList<TacheSimple> suggestion=calendrier.planifierAuto(taches);
        //for (TacheSimple t : suggestion){
        //    System.out.println(t.getNom()+" date "+t.getDate()+" debut: "+t.getCreneau().getDebut()+" fin: "+t.getCreneau().getFin());
        //}
        calendrier.etaleLaPeriode(taches);



        //affichage
       /*for(MinHeure c : jour.getLesDemiHeures()){
           System.out.println(c.getHeure()+" dans creneau :"+c.isDansCreneau()+" libre  :"+c.isLibre()+" Bloque :"+c.isBloque());
       }*/
        for (Journee j : calendrier.getLesJournees().values()){
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("===========>>Jour :"+j.getDate());
            System.out.println("Taches :");
            for (TacheSimple t : j.getTaches().values()){
                System.out.println(t.getNom()+" debut: "+t.getCreneau().getDebut()+" fin: "+t.getCreneau().getFin());
            }
            System.out.println("Creneaux libres :");
            for (Creneau c : j.getCreneauxLibres()){
                System.out.println(c.getDebut()+" "+c.getFin());
            }
        }
        System.out.println("FinPeriode :"+calendrier.getPeriodeFin());
        System.out.println("les taches dans calendrier :");
        for(TacheSimple tacheSimple : calendrier.getTachesSimple().values()){
            System.out.println(tacheSimple.getNom()+" date "+tacheSimple.getDate()+" debut: "+tacheSimple.getCreneau().getDebut()+" fin: "+tacheSimple.getCreneau().getFin());
        }
    }
}
