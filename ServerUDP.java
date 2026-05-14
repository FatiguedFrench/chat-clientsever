import java.io.*;
import java.net.*;
import java.util.UUID;

public class ServerUDP {
    public static void main(String[] args) {
        try {
            // 1. Inizializza il socket per ricevere i dati (Unicast)
            DatagramSocket serverSocket = new DatagramSocket(6000);
            
            // 2. Invia segnale al gruppo Multicast (Scenario 3) 
            DatagramSocket multiSender = new DatagramSocket();
            InetAddress group = InetAddress.getByName("230.0.0.0");
            String startMsg = "START";
            DatagramPacket trigger = new DatagramPacket(startMsg.getBytes(), startMsg.length(), group, 6001);
            multiSender.send(trigger);
            System.out.println("Segnale Multicast inviato. In ascolto su porta 6000...");

            // 3. Loop infinito per gestire i client
            while (true) {
                byte[] bufferIn = new byte[1024];
                DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                serverSocket.receive(packetIn); // Attende pacchetti

                // 4. Deserializzazione dell'oggetto (Scenario 4) 
                ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Oggetto datoRicevuto = (Oggetto) ois.readObject();
                System.out.println("Ricevuto dato da: " + datoRicevuto.getNomeDispositivo());

                // 5. Risposta con codice univoco al client 
                String idUnivoco = UUID.randomUUID().toString();
                byte[] bufferOut = idUnivoco.getBytes();
                DatagramPacket packetOut = new DatagramPacket(
                        bufferOut, bufferOut.length, packetIn.getAddress(), packetIn.getPort()
                );
                serverSocket.send(packetOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}