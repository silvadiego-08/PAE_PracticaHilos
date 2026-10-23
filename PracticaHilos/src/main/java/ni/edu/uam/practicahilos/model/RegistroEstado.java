
package ni.edu.uam.practicahilos.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RegistroEstado {

    private final String nombreHilo;
    private final Thread.State estado;
    private final LocalTime instante;

    public RegistroEstado(String nombreHilo,
                          Thread.State estado,
                          LocalTime instante) {

        this.nombreHilo = nombreHilo;
        this.estado = estado;
        this.instante = instante;
    }

    public Thread.State estado() {
        return estado;
    }

    public String mostrar() {

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

        return "[" + instante.format(formato) + "] "
                + nombreHilo + " -> " + estado;
    }
}
