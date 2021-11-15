package chat;
import java.net.*;
import java.util.Scanner;
import java.io.*;
 
public class Chatclient {
    private String hostname;
    private int port;
    private String userName;
 
    public Chatclient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
 
    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
 
            System.out.println("Connected to the chat server");
 
            new Readthread(socket, this).start();
            new Writethread(socket, this).start();
 
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
 
    }
 
    void setUserName(String userName) {
        this.userName = userName;
    }
 
    String getUserName() {
        return this.userName;
    }
 
 
    public static void main(String[] args) {
    	Scanner sc= new Scanner(System.in);
    	System.out.println("veuillez entrer le hostname");
        String hostname = sc.next();
        System.out.println("veuillez entrer le numéro de port");
        int port = sc.nextInt();
 
        Chatclient client = new Chatclient(hostname, port);
        client.execute();
    }
}