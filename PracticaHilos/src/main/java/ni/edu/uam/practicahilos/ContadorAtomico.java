package ni.edu.uam.practicahilos;

import java.util.concurrent.atomic.AtomicInteger;

public class ContadorAtomico {

    private final AtomicInteger total = new AtomicInteger(0);

    public void incrementar() {
        total.incrementAndGet();
    }

    public int getTotal() {
        return total.get();
    }
}