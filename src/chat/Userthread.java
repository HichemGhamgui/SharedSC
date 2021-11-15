package chat;
import java.io.*;
import java.net.*;
	
	public class Userthread extends Thread {
	    private Socket socket;
	    private Chatserver server;
	    private PrintWriter writer;
	 
	    public Userthread(Socket socket, Chatserver server) {
	        this.socket = socket;
	        this.server = server;
	    }
	 
	    public void run() {
	        try {
	            InputStream input = socket.getInputStream();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	 
	            OutputStream output = socket.getOutputStream();
	            writer = new PrintWriter(output, true);
	 
	            printUsers();
	 
	            String userName = reader.readLine();
	            server.addUserName(userName);
	 
	            String serverMessage = "New user connected: " + userName;
	            server.broadcast(serverMessage, this);
	 
	            String clientMessage;
	 
	            do {
	                clientMessage = reader.readLine();
	                serverMessage = "[" + userName + "]: " + clientMessage;
	                server.broadcast(serverMessage, this);
	 
	            } while (!clientMessage.equals("bye"));
	 
	            server.removeUser(userName, this);
	            socket.close();
	 
	            serverMessage = userName + " has quitted.";
	            server.broadcast(serverMessage, this);
	 
	        } catch (IOException ex) {
	            System.out.println("Error in UserThread: " + ex.getMessage());
	            ex.printStackTrace();
	        }
	    }
	 
	    /**
	     * Sends a list of online users to the newly connected user.
	     */
	    void printUsers() {
	        if (server.hasUsers()) {
	            writer.println("Connected users: " + server.getUserNames());
	        } else {
	            writer.println("No other users connected");
	        }
	    }
	 
	    /**
	     * Sends a message to the client.
	     */
	    void sendMessage(String message) {
	        writer.println(message);
	    }
	}