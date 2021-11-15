package chat;
import java.io.*;
import java.net.*;
import java.util.*;
 
public class Chatserver {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<Userthread> userThreads = new HashSet<>();
 
    public Chatserver(int port) {
        this.port = port;
    }
 
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Chat Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");
 
                Userthread newUser = new Userthread(socket, this);
                userThreads.add(newUser);
                newUser.start();
 
            }
 
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
    	Scanner sc= new Scanner(System.in);
    	System.out.println("veuillez entrer le numéro de port");
        int port = sc.nextInt();
 
        Chatserver server = new Chatserver(port);
        server.execute();
    }
 
    void broadcast(String message, Userthread excludeUser) {
        for (Userthread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }
 
    void addUserName(String userName) {
        userNames.add(userName);
    }
 
    void removeUser(String userName, Userthread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }
 
    Set<String> getUserNames() {
        return this.userNames;
    }
 
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}