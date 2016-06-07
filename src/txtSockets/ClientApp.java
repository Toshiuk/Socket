package txtSockets;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientApp {
	 public static void main(String[] args) throws UnknownHostException, IOException {
		 Socket cliente = new Socket("127.0.0.1", 81);
		 System.out.println("O cliente se conectou ao servidor: 127.0.0.1:81");
		 
		 Scanner entrada = new Scanner(cliente.getInputStream());
		 
		 while(entrada.hasNextLine()){
			 System.out.println(entrada.nextLine());			 
		 }
		 
		 entrada.close();
	 }
}
