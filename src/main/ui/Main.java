package ui;

import javax.swing.SwingUtilities;
import model.Event;
import model.EventLog;

/*
 * class to initiate HealthApp or MainWindow
 */
public class Main {
    public static void main(String[] args) throws Exception {


    //    new HealthApp();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                GUI main = new GUI();
                main.show();
            }
            
        });

        printLog(EventLog.getInstance());

        
    }

    public static void printLog(EventLog el) {
		for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
	}
}
