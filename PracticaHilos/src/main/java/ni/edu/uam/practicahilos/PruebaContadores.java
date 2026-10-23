package ni.edu.uam.practicahilos;

import java.util.concurrent.CountDownLatch;

public class PruebaContadores {

    public String ejecutarPruebas() throws InterruptedException {

        StringBuilder resultados = new StringBuilder();

        resultados.append("SIN SINCRONIZACION\n");
        probar(1, resultados);

        resultados.append("\nCON SYNCHRONIZED\n");
        probar(2, resultados);

        resultados.append("\nCON ATOMICINTEGER\n");
        probar(3, resultados);

        return resultados.toString();
    }

    private void probar(int tipo, StringBuilder resultados)
            throws InterruptedException {

        for (int prueba = 1; prueba <= 10; prueba++) {

            ContadorCompartido compartido = new ContadorCompartido();
            ContadorSincronizado sincronizado = new ContadorSincronizado();
            ContadorAtomico atomico = new ContadorAtomico();

            CountDownLatch inicio = new CountDownLatch(1);

            Runnable tarea = () -> {
                try {
                    inicio.await();

                    for (int i = 0; i < 1000; i++) {
                        if (tipo == 1) {
                            compartido.incrementar();
                        } else if (tipo == 2) {
                            sincronizado.incrementar();
                        } else {
                            atomico.incrementar();
                        }
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };

            Thread hilo1 = new Thread(tarea, "Ventanilla-A");
            Thread hilo2 = new Thread(tarea, "Ventanilla-B");

            hilo1.start();
            hilo2.start();

            inicio.countDown();

            hilo1.join();
            hilo2.join();

            int total;

            if (tipo == 1) {
                total = compartido.getTotal();
            } else if (tipo == 2) {
                total = sincronizado.getTotal();
            } else {
                total = atomico.getTotal();
            }

            resultados.append("Prueba ")
                    .append(prueba)
                    .append(" | Esperado: 2000")
                    .append(" | Obtenido: ")
                    .append(total)
                    .append(" | ")
                    .append(total == 2000 ? "CORRECTO" : "PERDIDA")
                    .append("\n");
        }
    }
}