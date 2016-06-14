
package chatsimples;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao extends Observable {

    private String ip;
    private int porta;
    private String mensagem;
    private String nickname;

    public Conexao(String ip, int porta, String nickname) {
        this.ip = ip;
        this.porta = porta;
        this.nickname = nickname;
        new Thread(new Recebe()).start();
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getIp() {
        return ip;
    }

    public int getPorta() {
        return porta;
    }

    public void envia(String texto) {
        new Thread(new Envia(texto)).start();
    }

    public void notifica(String mensagem) {
        this.mensagem = mensagem;
        setChanged();
        notifyObservers();
    }

    class Recebe implements Runnable {

        byte[] dadosReceber = new byte[255];
        boolean erro = false;
        DatagramSocket socket = null;

        @Override
        public void run() {
            while (true) {
                try {
                    socket = new DatagramSocket(getPorta());
                } catch (SocketException ex) {
                    Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
                }
                erro = false;
                while (!erro) {
                    DatagramPacket pacoteRecebido = new DatagramPacket(dadosReceber, dadosReceber.length);
                    try {
                        socket.receive(pacoteRecebido);
                        byte[] b = null;
                        b = pacoteRecebido.getData();
                        String s = "";
                        for (int i = 0; i < pacoteRecebido.getLength(); i++) {
                            if (b[i] != 0) {
                                s += (char) b[i];
                            } else {
                            	break;
                            }
                        }
                        String tempName[] = s.split("-", 0);
                        
                        String nome = tempName[0] + " disse:";
                        String mensagem = tempName[1];

                        notifica(nome + mensagem);
                    } catch (Exception e) {
                        System.out.println("erro");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        erro = true;
                        continue;
                    }
                }
            }
        }
    }

    class Envia implements Runnable {

        String texto;

        public Envia(String texto) {
            this.texto = texto;
        }

        @Override
        public void run() {
        	//Mensagem
        	StringBuilder mensagemComNome = new StringBuilder();
        	mensagemComNome.setLength(0);
        	mensagemComNome.append(nickname).append("-").append(texto);
        	
        	
            byte[] dados = mensagemComNome.toString().getBytes();

            try {
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress addr = InetAddress.getByName(getIp());
                DatagramPacket pacote = new DatagramPacket(dados, dados.length, addr, getPorta());
                clientSocket.send(pacote);
                clientSocket.close();
            } catch (SocketException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}