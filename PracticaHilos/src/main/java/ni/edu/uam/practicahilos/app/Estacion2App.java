package ni.edu.uam.practicahilos.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import ni.edu.uam.practicahilos.model.RegistroEstado;
import ni.edu.uam.practicahilos.task.TareaObservacion;

import java.time.LocalTime;

public class Estacion2App extends Application {

    private final Label etiquetaEstado =
            new Label("Estado: Sin iniciar");

    private final TextArea areaRegistro = new TextArea();

    private final Button botonIniciar =
            new Button("Iniciar observación");

    @Override
    public void start(Stage stage) {

        Label titulo = new Label(
                "Estación 2 - Estados del hilo"
        );

        areaRegistro.setEditable(false);
        areaRegistro.setPrefHeight(300);

        botonIniciar.setOnAction(event -> iniciarObservacion());

        VBox contenedor = new VBox(
                15,
                titulo,
                etiquetaEstado,
                botonIniciar,
                areaRegistro
        );

        contenedor.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(contenedor, 550, 430);

        stage.setTitle("Monitor de estados");
        stage.setScene(scene);
        stage.show();
    }

    private void iniciarObservacion() {

        botonIniciar.setDisable(true);
        areaRegistro.clear();

        Thread hilo = new Thread(
                new TareaObservacion("A"),
                "Ventanilla-A"
        );

        registrarEstado(hilo);

        hilo.start();

        registrarEstado(hilo);

        iniciarMonitor(hilo);
    }

    private void iniciarMonitor(Thread hilo) {

        Thread monitor = new Thread(() -> {

            Thread.State estadoAnterior = null;

            while (true) {

                Thread.State estadoActual = hilo.getState();

                if (estadoActual != estadoAnterior) {

                    registrarEstado(hilo);
                    estadoAnterior = estadoActual;
                }

                if (estadoActual == Thread.State.TERMINATED) {

                    Platform.runLater(() ->
                            botonIniciar.setDisable(false)
                    );

                    break;
                }

                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

        }, "Monitor-Estados");

        monitor.setDaemon(true);
        monitor.start();
    }

    private void registrarEstado(Thread hilo) {

        RegistroEstado registro = new RegistroEstado(
                hilo.getName(),
                hilo.getState(),
                LocalTime.now()
        );

        Platform.runLater(() -> {

            areaRegistro.appendText(
                    registro.mostrar() + "\n"
            );

            etiquetaEstado.setText(
                    "Estado: " + registro.estado()
            );
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
