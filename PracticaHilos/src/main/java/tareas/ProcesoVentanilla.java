package tareas;

public class ProcesoVentanilla implements Runnable {
    private final String nombre;

    public ProcesoVentanilla(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {

            if (Thread.currentThread().isInterrupted()) {
                System.out.println("[" + Thread.currentThread().getName() + "] " + nombre + " interrumpido.");
                return;
            }

            System.out.println("[" + Thread.currentThread().getName() + "] " + nombre + " procesa " + i);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                System.out.println("[" + Thread.currentThread().getName() + "] " + nombre + " interrumpido durante sleep.");
                return;
            }
        }
    }
}