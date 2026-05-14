import java.io.*;
import java.net.*;

public class ClientUDP {
    public static void main(String[] args) {
        try {
            // 1. Iscrizione al gruppo Multicast per il segnale di avvio (Scenario 3) 
            MulticastSocket multicastSocket = new MulticastSocket(6001);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            multicastSocket.joinGroup(group);

            System.out.println("In attesa del segnale Multicast dal server...");
            byte[] multiBuf = new byte[256];
            DatagramPacket msgPacket = new DatagramPacket(multiBuf, multiBuf.length);
            multicastSocket.receive(msgPacket);
            multicastSocket.leaveGroup(group); // Uscita dal gruppo dopo il trigger

            // 2. Preparazione dati strutturati 
            DatagramSocket unicastSocket = new DatagramSocket();
            Oggetto mioDato = new Oggetto("Host-Client-1");

            // 3. Serializzazione (Scenario 4) 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(mioDato);
            byte[] dataBuf = baos.toByteArray();

            // 4. Invio al server
            InetAddress serverAddress = InetAddress.getByName("localhost");
            DatagramPacket sendPacket = new DatagramPacket(dataBuf, dataBuf.length, serverAddress, 6000);
            unicastSocket.send(sendPacket);

            // 5. Attesa del codice univoco di risposta 
            byte[] resBuf = new byte[1024];
            DatagramPacket resPacket = new DatagramPacket(resBuf, resBuf.length);
            unicastSocket.receive(resPacket);
            
            String response = new String(resPacket.getData(), 0, resPacket.getLength());
            System.out.println("Assegnato codice univoco dal server: " + response);

            unicastSocket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}