package ni.edu.uam.practicahilos;

public class ContadorSincronizado {

    private int total = 0;

    public synchronized void incrementar() {
        total++;
    }

    public synchronized int getTotal() {
        return total;
    }
}