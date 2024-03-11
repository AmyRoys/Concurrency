import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost"; // Change this to the IP address or hostname of your server
        final int PORT = 5000;
        
        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            System.out.println("Connected to server: " + SERVER_ADDRESS + " on port " + PORT);
            
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String userInputString;
            while ((userInputString = userInput.readLine()) != null) {
                output.println(userInputString);
                System.out.println("Sent to server: " + userInputString);
                
                String serverResponse = serverInput.readLine();
                System.out.println("Server response: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
