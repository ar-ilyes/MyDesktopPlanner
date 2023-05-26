package Modules;

public class UnacceptableDateFinPeriode extends RuntimeException{
    public UnacceptableDateFinPeriode() {
        super("La date de fin de la periode est inacceptable car elle vient apres la date de debut de la periode ");
    }
}
