package Modules;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        //MyPlannerApp app = new MyPlannerApp();
        LocalDate debutPeriode = LocalDate.of(2020, 1, 1);
        LocalDate finPeriode = LocalDate.of(2020, 1, 7);
        //the calendar of the user
        Calendrier calendrier = new Calendrier(debutPeriode,finPeriode,new Historique());

        //the user
        Utilisateur user = new Utilisateur("test",calendrier, 0, new ArrayList<Badge>(), "test_password");
        calendrier.setHistorique(user.getHistorique());
        calendrier.setUtilisateur(user);
        //app.getUsers().put("test", user);
        //app.setCurrentUser(user);

        //the 1st day and its creneaux and tasks
        Creneau creneau1 = new Creneau("08:30","11:30");
        Creneau creneau2 = new Creneau("12:30","15:30");
        Journee jour = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 1),calendrier);
        jour.introduireCreneau(creneau1);
        jour.introduireCreneau(creneau2);
        TacheSimple tache1 = new TacheSimple("testTache", 60, creneau1, Priorite.LOW, LocalDate.of(2020, 1, 8), new Categorie("Study"), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, 2);
        TacheSimple tache2 = new TacheSimple("testTache2", 130, creneau2, Priorite.LOW, LocalDate.of(2020, 1, 1), new Categorie("Study"), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, 0);
        TacheSimple tache3 = new TacheSimple("testTache3", 100, creneau2, Priorite.LOW, LocalDate.of(2020, 1, 1), new Categorie("Study"), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, 0);

        //the 2nd day and its creneaux and tasks
        Creneau creneau2_1 = new Creneau("07:30","11:30");
        Creneau creneau2_2 = new Creneau("11:30","14:30");
        Journee jour2 = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 2),calendrier);
        jour2.introduireCreneau(creneau2_1);
        jour2.introduireCreneau(creneau2_2);

        //the 3rd day and its creneaux and tasks
        Creneau creneau3_1 = new Creneau("07:30","11:30");
        Creneau creneau3_2 = new Creneau("11:30","14:30");
        Journee jour3 = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 3),calendrier);
        jour3.introduireCreneau(creneau3_1);
        jour3.introduireCreneau(creneau3_2);

        //the 4th day and its creneaux and tasks
        Creneau creneau4_1 = new Creneau("07:30","11:30");
        Creneau creneau4_2 = new Creneau("11:30","14:30");
        Journee jour4 = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 4),calendrier);
        jour4.introduireCreneau(creneau4_1);
        jour4.introduireCreneau(creneau4_2);

        //the 5th day and its creneaux and tasks
        Creneau creneau5_1 = new Creneau("07:30","11:30");
        Creneau creneau5_2 = new Creneau("11:30","14:30");
        Journee jour5 = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 5),calendrier);
        jour5.introduireCreneau(creneau5_1);
        jour5.introduireCreneau(creneau5_2);

        //the 6th day and its creneaux and tasks
        Creneau creneau6_1 = new Creneau("07:30","11:30");
        Creneau creneau6_2 = new Creneau("11:30","14:30");
        Journee jour6 = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 6),calendrier);
        jour6.introduireCreneau(creneau6_1);
        jour6.introduireCreneau(creneau6_2);

        //the 7th day and its creneaux and tasks
        Creneau creneau7_1 = new Creneau("07:30","11:30");
        Creneau creneau7_2 = new Creneau("11:30","14:30");
        Journee jour7 = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 7),calendrier);
        jour7.introduireCreneau(creneau7_1);
        jour7.introduireCreneau(creneau7_2);



        calendrier.addDay(jour.getDate(),jour);
        calendrier.addDay(jour2.getDate(),jour2);
        calendrier.addDay(jour3.getDate(),jour3);
        calendrier.addDay(jour4.getDate(),jour4);
        calendrier.addDay(jour5.getDate(),jour5);
        calendrier.addDay(jour6.getDate(),jour6);
        calendrier.addDay(jour7.getDate(),jour7);

        //addition les taches au jour coreespondant:
        jour.introduireTacheAuto(tache1);
        jour.introduireTacheAuto(tache2);
        jour.introduireTacheAuto(tache3);

        TacheDecompose tacheDecompose=new TacheDecompose("testTacheDecompose", 400, creneau1, Priorite.LOW, LocalDate.of(2020, 1, 1), new Categorie("Study"), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, new ArrayList<TacheSimple>(),0);
        jour.introduireTacheAuto(tacheDecompose);
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


    }
}
