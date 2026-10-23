package app;

import tareas.ProcesoVentanilla;

public class PruebaEstacion1 {

    public static void main(String[] args) {
        System.out.println("=== DEMOSTRACIÓN 1: Llamada directa a run() (Bloqueante) ===");
        ProcesoVentanilla v1 = new ProcesoVentanilla("Ventanilla 1");
        ProcesoVentanilla v2 = new ProcesoVentanilla("Ventanilla 2");

        v1.run();
        v2.run();

        System.out.println("\n=== DEMOSTRACIÓN 2: Ejecución en hilos con start() (Concurrente) ===");

        Thread hiloVentanilla1 = new Thread(new ProcesoVentanilla("Ventanilla 1"), "Hilo-Ventanilla-1");
        Thread hiloVentanilla2 = new Thread(new ProcesoVentanilla("Ventanilla 2"), "Hilo-Ventanilla-2");

        hiloVentanilla1.start();
        hiloVentanilla2.start();

        System.out.println("-> Hilos iniciados desde el hilo principal [" + Thread.currentThread().getName() + "]");
    }
}