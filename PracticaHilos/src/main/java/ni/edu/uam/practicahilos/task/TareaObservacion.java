package ni.edu.uam.practicahilos.task;

public class TareaObservacion implements Runnable {

    private final String nombre;

    public TareaObservacion(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {

            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            System.out.println(nombre + " procesa " + i);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}