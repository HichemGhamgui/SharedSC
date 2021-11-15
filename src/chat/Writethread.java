package chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;
 
public class Writethread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private Chatclient client;
 
    public Writethread(Socket socket, Chatclient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
 
    	Scanner sc= new Scanner(System.in);
    	System.out.println("Veuillez saisir votre nom!");
        String userName = sc.next();
        client.setUserName(userName);
        writer.println(userName);
 
        String text;
 
        do {
            text = "["+sc.next()+"]";
            writer.println(text);
 
        } while (!text.equals("[bye]"));
 
        try {
            socket.close();
        } catch (IOException ex) {
 
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}