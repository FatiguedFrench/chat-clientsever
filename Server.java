import java.io.*;
import java.net.*;

public class Server {
	private ServerSocket serverSocket;
	private int porta;

	public Server(int porta) {
		this.porta = porta;
		try {
			this.serverSocket = new ServerSocket(porta);
			System.out.println("Server avviato sulla porta " + porta);
		} catch (IOException e) {
			System.err.println("Errore: Porta già in uso (Scenario 3).");
		}
	}

	public void attendi() {
		try {
			while (true) { // Loop infinito per connessioni continue
				Socket clientSocket = serverSocket.accept();
				System.out.println("Nuova connessione da: " + clientSocket.getInetAddress());
				
				// Crea e avvia un nuovo thread per questo client specifico
				GestoreClient gestore = new GestoreClient(clientSocket);
				new Thread(gestore).start();
			}
		} catch (IOException e) {
			System.err.println("Errore durante l'accettazione della connessione.");
		}
	}

	public void termina() {
		try {
			if (serverSocket != null) serverSocket.close(); // Chiusura servizio [cite: 14]
		} catch (IOException e) {}
	}
}