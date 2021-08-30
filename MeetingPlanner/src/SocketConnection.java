import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

public class SocketConnection {
	
	private static final String HOST = "localhost"; //"45.79.81.143";
	private static final int PORT = 23512;
	
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private static final int HEADER_LENGTH = 8;
	
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public SocketConnection() {
    	startConnection(HOST, PORT);
    }
    
    public void startConnection(String ip, int port) {
    	try {
	        clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        System.out.println("[CLIENT] Connected to server");
    	} catch (IOException e) {
    		// e.printStackTrace();
    		System.out.println("[CLIENT] Unable to connect to server");
    	}
    }

    public void sendMessage(String msg) {
    	System.out.println("[CLIENT] Sending Message: " + msg);
    	int length = msg.getBytes(UTF8).length;
    	String header = Integer.toString(length);
    	for (int i = 0; i < HEADER_LENGTH - Integer.toString(length).length(); i++) {
    		header = "0" + header;
    	}
    	out.println(header + msg);
    }
    
    public String receiveMessage() {
		try {
			char[] buffer = new char[HEADER_LENGTH];
			int charsIn = in.read(buffer, 0, HEADER_LENGTH);
			StringBuilder data = new StringBuilder(charsIn);
			data.append(buffer, 0, charsIn);
			
			int msgLength = Integer.parseInt(data.toString());
			buffer = new char[msgLength];
			charsIn = in.read(buffer, 0, msgLength);
			data = new StringBuilder(charsIn);
			data.append(buffer, 0, charsIn);
			
			System.out.println("[CLIENT] Received Message: " + data.toString());
			return data.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "";
    }

    public void stopConnection() {
        try {
        	sendMessage("QUIT");
			in.close();
	        out.close();
	        clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
    	SocketConnection test = new SocketConnection();
    	
    	Scanner in = new Scanner(System.in);
    	while (true) {
    		System.out.print("Send Message: ");
    		String send = in.nextLine();
	    	test.sendMessage(send);
	    	if (send.equals("QUIT")) break;
	    	test.receiveMessage();
    	}
    	System.out.println("[CLIENT] Disconnecting...");
    	in.close();
    	test.stopConnection();
    }
}
