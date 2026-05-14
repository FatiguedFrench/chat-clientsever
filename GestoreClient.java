import java.io.*;
import java.net.*;

public class GestoreClient implements Runnable {
	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;

	public GestoreClient(Socket socket) {
		this.clientSocket = socket;
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);

			// Lettura della richiesta [cite: 14]
			String messaggio = in.readLine();
			if (messaggio != null) {
				System.out.println("Ricevuto (" + Thread.currentThread().getName() + "): " + messaggio);
				
				// Simuliamo un'operazione che richiede tempo per dimostrare la concorrenza
				Thread.sleep(2000); 

				// Invio della risposta [cite: 14]
				out.println("Ricevuto e processato dal " + Thread.currentThread().getName());
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Errore di comunicazione nel thread.");
		} finally {
			chiudi();
		}
	}

	private void chiudi() {
		try {
			if (clientSocket != null) clientSocket.close(); // Chiusura comunicazione [cite: 14]
		} catch (IOException e) {}
	}
}