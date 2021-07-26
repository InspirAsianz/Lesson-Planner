
import javax.swing.*;

public class StartProgram implements Runnable {
    public void run() {
        SwingUtilities.invokeLater(new LoginWindow());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new StartProgram());
    }
}
