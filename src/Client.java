import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.stream.events.StartDocument;

import java.io.*;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private boolean iAmReady;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendCode() {
        Socket client;
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner inputScanner = new Scanner(System.in);

            while (socket.isConnected()) {
                String codeConcatenated = "";
                ArrayList<String> code = new ArrayList<String>();

                while (!iAmReady) {
                    String line = inputScanner.nextLine();
                    if (line.equals("ready")) {
                        iAmReady = true;
                        break;
                    }
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }

                while (inputScanner.hasNextLine()) {
                    String line = inputScanner.nextLine();
                    if (line.equals("ready")) {
                        code.clear();
                        codeConcatenated = "ready";
                        iAmReady = true;
                        break;
                    }
                    if (line.equals("redo")) {
                        code.clear();
                        line = "";
                    }
                    if (line.equals("submit")) {
                        break;
                    }

                    code.add(line);

                }

                for (int i = 0; i < code.size(); i++) {
                    codeConcatenated = codeConcatenated + code.get(i);
                }

                bufferedWriter.write(codeConcatenated);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            // System.out.println(bufferedReader.readLine());

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForResponse() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response;

                while (socket.isConnected()) {
                    try {
                        response = bufferedReader.readLine();
                        System.out.println(response);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();

    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("10.64.208.85", 1234);
        Client client = new Client(socket, username);
        client.listenForResponse();
        client.sendCode();
    }
}
