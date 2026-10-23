package ni.edu.uam.practicahilos.app;
import ni.edu.uam.practicahilos.model.EstadoProceso;
import ni.edu.uam.practicahilos.task.ProcesoCancelable;

public class ControlVentanas {
    private ProcesoCancelable tareaA;
    private ProcesoCancelable tareaB;

    private Thread hiloA;
    private Thread hiloB;

    private volatile EstadoProceso estado = EstadoProceso.EN_ESPERA;

    public EstadoProceso getEstado() {
        return estado;
    }

    public synchronized boolean iniciar() {
        if (estado == EstadoProceso.EJECUTANDO
                || estado == EstadoProceso.DETENIENDO) {
            return false;
        }

        // Cada sesión necesita tareas e hilos nuevos.
        tareaA = new ProcesoCancelable("Ventanilla A", 20);
        tareaB = new ProcesoCancelable("Ventanilla B", 20);

        hiloA = new Thread(tareaA, "Ventanilla-A");
        hiloB = new Thread(tareaB, "Ventanilla-B");

        estado = EstadoProceso.EJECUTANDO;

        hiloA.start();
        hiloB.start();

        Thread coordinador = new Thread(
                this::esperarTerminacion,
                "Coordinador-ventanillas"
        );

        coordinador.start();

        return true;
    }

    public synchronized boolean detener() {
        if (estado != EstadoProceso.EJECUTANDO) {
            return false;
        }

        estado = EstadoProceso.DETENIENDO;

        tareaA.detener();
        tareaB.detener();

        hiloA.interrupt();
        hiloB.interrupt();

        return true;
    }

    private void esperarTerminacion() {
        try {
            hiloA.join();
            hiloB.join();

            synchronized (this) {
                boolean ambasFinalizaron =
                        tareaA.getEstado() == EstadoProceso.FINALIZADO
                                && tareaB.getEstado() == EstadoProceso.FINALIZADO;

                estado = ambasFinalizaron
                        ? EstadoProceso.FINALIZADO
                        : EstadoProceso.DETENIDO;
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            System.err.println(
                    "Se interrumpió al coordinador; "
                            + "no se confirmó la terminación de ambas ventanillas."
            );
        }
    }
}
