package trabalhoprj3.Classes;

import javax.swing.JFrame;
import trabalhoprj3.Executar.SocketServidor;
import trabalhoprj3.GUI.ClienteJanela;

public class Threading {
    
    public static void newServidor(){
        new Thread(new Runnable() {
            public void run() {
                new SocketServidor();
            }
        }).start();
    }
    public static void newCliente(){
        new Thread(new Runnable() {
            public void run() {
                ClienteJanela quadroDialogo = new ClienteJanela();
                quadroDialogo.setLocationRelativeTo(null);
                quadroDialogo.setVisible(true);
                quadroDialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        }).start();
    }
}
