package trabalhoprj3;

import trabalhoprj3.GUI.JanelaMenus;

public class TrabalhoPRJ3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        JanelaMenus quadroDialogo = new JanelaMenus();
        quadroDialogo.setLocationRelativeTo(null);
        quadroDialogo.setVisible(true);
    } 
}
