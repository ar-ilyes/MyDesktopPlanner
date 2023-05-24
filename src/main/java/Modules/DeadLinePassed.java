package Modules;

public class DeadLinePassed extends RuntimeException implements java.io.Serializable {
    public DeadLinePassed() {
        super("Planification impossible de la tâche dans ce jour car il dépasse le deadline.");
    }
}

