import java.io.Serializable;

public class Oggetto implements Serializable {
    private String nomeDispositivo;
    private long timestamp;

    public Oggetto(String nomeDispositivo) {
        this.nomeDispositivo = nomeDispositivo;
        this.timestamp = System.currentTimeMillis();
    }

    public String getNomeDispositivo() {
        return nomeDispositivo;
    }
}