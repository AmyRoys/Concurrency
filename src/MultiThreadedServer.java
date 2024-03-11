import java.io.*;
import java.net.*;
import java.util.Scanner; 


// MultiThreadedServer listens for incoming client connections on a specific port (PORT)
// When a client connects, it spawns a new thread (ClientHandler) to handle that client's requests
// ClientHandler represents the thread responsible for handling client connections
// It listens for messages from the client, echoes them back, and closes the connection when 
// the client disconnects.
// The server runs indefinitely, continuously listening for incoming connections

public class MultiThreadedServer {
    public static void main(String[] args) {
        final int PORT = 5000;
        
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Listening on port " + PORT + "...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                
                // Create a new thread for each client connection
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Input and output streams for the client
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage;
            Scanner scanner = new Scanner(System.in);
            while ((clientMessage = input.readLine()) != null) {
                System.out.println("Received from client: " + clientMessage);
                
                System.out.print("Enter reply: ");
                String reply = scanner.nextLine();

                output.println(reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
