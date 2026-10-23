package ni.edu.uam.practicahilos.app;

import ni.edu.uam.practicahilos.model.EstadoProceso;

public class PruebaDetencion {

    public static void main(String[] args)
            throws InterruptedException {

        ControlVentanas control = new ControlVentanas();

        System.out.println("Estado inicial: " + control.getEstado());

        // Primera sesión: cancelación
        System.out.println("¿Inició? " + control.iniciar());

        // Debe rechazar una segunda sesión simultánea
        System.out.println("¿Inició otra vez? " + control.iniciar());

        Thread.sleep(1100);

        System.out.println("¿Solicitó detener? " + control.detener());

        esperarResultado(control);

        System.out.println(
                "Resultado de la primera sesión: " + control.getEstado()
        );

        // Segunda sesión: finalización natural.
        System.out.println("¿Reinició? " + control.iniciar());

        esperarResultado(control);

        System.out.println(
                "Resultado de la segunda sesión: " + control.getEstado()
        );
    }

    private static void esperarResultado(ControlVentanas control)
            throws InterruptedException {

        while (control.getEstado() == EstadoProceso.EJECUTANDO
                || control.getEstado() == EstadoProceso.DETENIENDO) {
            Thread.sleep(50);
        }
    }
}