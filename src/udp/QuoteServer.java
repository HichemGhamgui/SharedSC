package udp;

import java.io.*;
import java.net.*;
import java.util.*;
 
public class QuoteServer {
	static String adr="192.168.43.81";
    private DatagramSocket socket;
    private List<String> listQuotes = new ArrayList<String>();
    private Random random;
 
    public QuoteServer(int port, InetAddress a) throws SocketException {
        socket = new DatagramSocket(port,a);
        random = new Random();
    }
 
    public static void main(String[] args) {
    	Scanner sc= new Scanner(System.in);
        String quoteFile;
        int port;
        System.out.println("entrer le numéro de port");
        port = sc.nextInt();
        System.out.println("entrer le chemin du fichier des quotes");
        quoteFile = sc.next();
       

        try {
            QuoteServer server = new QuoteServer(port, InetAddress.getByName(adr));
            server.loadQuotesFromFile(quoteFile);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
 
    private void service() throws IOException {
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1], 1);
            socket.receive(request);
 
            String quote = getRandomQuote();
            byte[] buffer = quote.getBytes();
 
            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();
 
            DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            socket.send(response);
        }
    }
 
    private void loadQuotesFromFile(String quoteFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(quoteFile));
        String aQuote;
 
        while ((aQuote = reader.readLine()) != null) {
            listQuotes.add(aQuote);
        }
 
        reader.close();
    }
 
    private String getRandomQuote() {
        int randomIndex = random.nextInt(listQuotes.size());
        String randomQuote = listQuotes.get(randomIndex);
        return randomQuote;
    }
}