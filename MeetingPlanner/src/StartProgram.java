
import javax.swing.*;

public class StartProgram implements Runnable {
	
	public static SocketConnection socket;
	public static String username;
	public static String usertype = "STUDENT";
	
    public void run() {
        SwingUtilities.invokeLater(new MainWindow());
    }
    
    public static void main(String[] args) {
    	socket = new SocketConnection();
    	Runtime.getRuntime().addShutdownHook(new Shutdown());
        SwingUtilities.invokeLater(new StartProgram());
    }
    
	private static class Shutdown extends Thread {
		public void run() {
			socket.stopConnection();
			System.out.println("System exited successfully");
		}
	}
}
