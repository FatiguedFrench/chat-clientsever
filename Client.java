import java.io.*;
import java.net.*;

public class Client {
	private String nome;
	private String colore;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public Client(String nome) {
		this.nome = nome;
		this.colore = "Default";
	}

	public Client(String nome, String colore) {
		this.nome = nome;
		this.colore = colore;
	}

	public int connetti(String nomeServer, int portaServer) {
		try {
			socket = new Socket(nomeServer, portaServer);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			return 1;
		} catch (UnknownHostException e) {
			System.err.println("Host sconosciuto: " + nomeServer + " (Scenario 4)");
			return 0;
		} catch (IOException e) {
			return 0; 
		}
	}

	public void scrivi() {
		if (out != null) {
			out.println("Richiesta da " + nome + " [" + colore + "]");
		}
	}

	public void leggi() {
		try {
			if (in != null) {
				System.out.println("Risposta server: " + in.readLine());
			}
		} catch (IOException e) {}
	}

	public void chiudi() {
		try {
			if (socket != null) socket.close();
		} catch (IOException e) {}
	}
}