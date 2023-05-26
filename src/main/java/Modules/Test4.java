package Modules;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Test4 {
    public static void main(String[] args) {
        //MyPlannerApp app = new MyPlannerApp();
        LocalDate debutPeriode = LocalDate.of(2020, 1, 1);
        LocalDate finPeriode = LocalDate.of(2020, 1, 7);
        //the calendar of the user
        Calendrier calendrier = new Calendrier(debutPeriode,finPeriode,new Historique());

        //the user
        Utilisateur user = new Utilisateur("test",calendrier, 1,  new ArrayList<Badge>(), "test_password");
        calendrier.setHistorique(user.getHistorique());
        calendrier.setUtilisateur(user);
        //app.getUsers().put("test", user);
        //app.setCurrentUser(user);

        //the 1st day and its creneaux and tasks
        Creneau creneau1 = new Creneau("08:00","10:00");

        Journee jour = new Journee(new HashMap<Integer,TacheSimple>(),new ArrayList<Creneau>(),LocalDate.of(2020, 1, 1),calendrier);
        jour.introduireCreneau(creneau1);

        calendrier.addDay(jour.getDate(),jour);
       Creneau creneauTache=new Creneau("08:30","09:30");
        //the tasks
        TacheSimple tache1 = new TacheSimple("test2Tache", 60, creneauTache, Priorite.LOW, LocalDate.of(2020, 1, 1), new Categorie("Study"), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, 0);
        calendrier.getLesJournees().get(LocalDate.of(2020, 1, 1)).introduireTacheManuelle(tache1,creneauTache);

        TacheSimple tache2 = new TacheSimple("test2Tache2", 60, creneau1, Priorite.LOW, LocalDate.of(2020, 1, 1), new Categorie("Study"), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, 0);
        calendrier.replanification(tache2);


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
