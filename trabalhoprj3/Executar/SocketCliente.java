package trabalhoprj3.Executar;

import trabalhoprj3.Classes.Mensagens;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketCliente {
    private Socket socket;
    private ObjectOutputStream saida;
    
    public Socket conectar() {
        try {
            this.socket = new Socket("127.0.0.1", 5555);
            this.saida = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
        }
        return socket;
    }
    
    public void enviar(Mensagens mensagem) {
        try {
            saida.writeObject(mensagem);
        } catch (IOException ex) {
        }
    }
}
