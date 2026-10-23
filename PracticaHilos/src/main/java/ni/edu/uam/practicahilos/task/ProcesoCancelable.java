package ni.edu.uam.practicahilos.task;
import ni.edu.uam.practicahilos.model.EstadoProceso;

public class ProcesoCancelable implements Runnable {
    private final String nombre;
    private final int limite;

    private volatile boolean activo = true;
    private volatile EstadoProceso estado = EstadoProceso.EN_ESPERA;

    public ProcesoCancelable(String nombre, int limite) {
        if (limite <= 0) {
            throw new IllegalArgumentException(
                    "El límite debe ser mayor que cero"
            );
        }
        this.nombre = nombre;
        this.limite = limite;
    }

    public void detener() {
        activo = false;
    }

    public EstadoProceso getEstado() {
        return estado;
    }

    @Override
    public void run() {
        estado = EstadoProceso.EJECUTANDO;
        try {
            for (int i = 1; i <= limite; i++) {

                if (cancelacionSolicitada()) {
                    return;
                }

                Thread.sleep(250);

                if (cancelacionSolicitada()) {
                    return;
                }

                // Unidad de trabajo simulada
                System.out.println(nombre + " procesa solicitud " + i);
            }
            estado = EstadoProceso.FINALIZADO;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(nombre + ": espera interrumpida por cancelación");
        } finally {
            if (estado != EstadoProceso.FINALIZADO) {
                estado = EstadoProceso.DETENIDO;
            }
            System.out.println(nombre + ": salida de run(), estado " + estado);
        }
    }
    private boolean cancelacionSolicitada () {
        return !activo || Thread.currentThread().isInterrupted();
    }
}

