package network;

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
    }

    public static class ClientHandler extends Thread {
        private InputStream inputStream;
        private OutputStream outputStream;
        private ServerHelp serverHelp;

        public ClientHandler(InputStream inputStream, OutputStream outputStream, ServerHelp serverHelp) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            this.serverHelp = serverHelp;
        }

        private void handleClient() {

        }
    }
}
