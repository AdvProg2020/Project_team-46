package network;

import model.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        new ServerHelp().runServer();
    }

    private static class ServerHelp {
        private void runServer() {
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                while (true) {
                    Socket clientSocket;
                    clientSocket = serverSocket.accept();
                    System.out.println("New Client Accepted");
                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Account handleRegister(String input) {
            return null;
        }
    }

    public static class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private ServerHelp serverHelp;
        private Account account;

        public ClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream,
                             ServerHelp serverHelp) {
            this.clientSocket = clientSocket;
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
            this.serverHelp = serverHelp;
        }

        private void handleClient() {
            String inputString;
            while (true) {
                try {
                    inputString = dataInputStream.readUTF();
                    System.out.println("Client " + " sent " + inputString);
                    if (inputString.startsWith("Register")) {
                        account = serverHelp.handleRegister(inputString);
                    } //else if ()
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }


}
