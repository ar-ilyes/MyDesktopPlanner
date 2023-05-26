package Modules;

public class UnacceptableDateDebutPeriode extends RuntimeException{
    public UnacceptableDateDebutPeriode() {
        super("La date de debut de la periode est inacceptable car elle vient apres la date actuelle ");
    }
}
