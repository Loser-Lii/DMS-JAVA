package filesManageSystem;

import frame.*;
import javax.swing.*;
import java.awt.*;

public class MainGUI {
    /**
     * Lauch the application
     */
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    JFrame loginFrame= new LoginFrame();
                    loginFrame.setVisible(true);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
