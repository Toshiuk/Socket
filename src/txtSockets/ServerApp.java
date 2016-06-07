package txtSockets;

import java.io.IOException;

public class ServerApp {
	public static void main(String[] args) throws IOException {
		new Server(81).executa();
	 }
}
