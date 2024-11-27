package ui;

import javax.swing.SwingUtilities;

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

        
    }

    
}
