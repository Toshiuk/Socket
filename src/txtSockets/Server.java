package txtSockets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	private int port;
	private PrintStream cliente;

    public Server(int port) {
        this.port = port;
    }
	 public void executa() throws IOException {
		ServerSocket server = new ServerSocket(this.port);
		System.out.println("Porta " +this.port + " aberta!");
		 
            Socket client = server.accept();
            System.out.println("Nova conexão com cliente "     + client.getInetAddress().getHostAddress());
            
            PrintStream ps = new PrintStream(client.getOutputStream());
            this.cliente = ps;
            InputStream is = new FileInputStream("arquivo.txt");
            
    			Scanner entrada = new Scanner(is);
    			PrintStream saida = new PrintStream(client.getOutputStream());
    			while (entrada.hasNextLine()) {
    				saida.println(entrada.nextLine());
    			 }
    			saida.close();
    			entrada.close();
    			server.close();
            
            
           
            }
		}
		
		
	
	