public class MainClient {
	public static void main(String[] args) {
		Client client = new Client("ClientA", "Rosso");
		int connesso = 0;
		int tentativi = 0;

		while (connesso == 0 && tentativi < 3) {
			System.out.println("Tentativo di connessione " + (tentativi + 1) + "...");
			connesso = client.connetti("localhost", 5000);
			
			if (connesso == 0) {
				try { Thread.sleep(2000); } catch (InterruptedException e) {}
				tentativi++;
			}
		}

		if (connesso == 1) {
			client.scrivi();
			client.leggi();
			client.chiudi();
		} else {
			System.out.println("Impossibile raggiungere il server.");
		}
	}
}
