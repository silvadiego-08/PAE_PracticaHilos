package ni.edu.uam.practicahilos.app;


import ni.edu.uam.practicahilos.task.ProcesoCancelable;

public class PruebaDetencion {
    public static void main(String[] args)
            throws InterruptedException {

        ProcesoCancelable tareaA =
                new ProcesoCancelable("Ventanilla A", 20);

        ProcesoCancelable tareaB =
                new ProcesoCancelable("Ventanilla B", 20);

        Thread hiloA = new Thread(tareaA, "Ventanilla-A");
        Thread hiloB = new Thread(tareaB, "Ventanilla-B");

        System.out.println("En espera");

        hiloA.start();
        hiloB.start();

        System.out.println("Ejecutando");

        // Simula el tiempo antes de pulsar Detener.
        Thread.sleep(1100);

        System.out.println("Deteniendo");

        // Primero se solicita detener ambas tareas.
        tareaA.detener();
        tareaB.detener();

        // Después se interrumpe cualquier espera en sleep().
        hiloA.interrupt();
        hiloB.interrupt();

        // Se espera la terminación real de ambos hilos.
        hiloA.join();
        hiloB.join();

        System.out.println("Detenido");
        System.out.println("¿A sigue vivo? " + hiloA.isAlive());
        System.out.println("¿B sigue vivo? " + hiloB.isAlive());
    }
}
