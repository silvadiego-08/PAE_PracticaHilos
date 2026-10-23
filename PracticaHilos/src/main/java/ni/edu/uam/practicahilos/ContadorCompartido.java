package ni.edu.uam.practicahilos;

public class ContadorCompartido {

    private int total = 0;

    public void incrementar() {
        int valor = total;
        Thread.yield();
        total = valor + 1;
    }

    public int getTotal() {
        return total;
    }
}


