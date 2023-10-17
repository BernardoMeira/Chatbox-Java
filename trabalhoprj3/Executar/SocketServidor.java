package trabalhoprj3.Executar;

import trabalhoprj3.Classes.Mensagens;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import trabalhoprj3.OperacaoServidor;

public class SocketServidor {
    private Map<String, ObjectOutputStream> Onlines = new HashMap<String, ObjectOutputStream>();
    private ServerSocket serverSocket;
    private Socket socket;

    public SocketServidor() {
        try {
            serverSocket = new ServerSocket(5555);
            while (true) {
                socket = serverSocket.accept();
                new Thread(new ListenerSocket(socket)).start();
            }
        } catch (IOException ex) {
        }
    }

    private class ListenerSocket implements Runnable {
        private ObjectOutputStream saida;
        private ObjectInputStream entrada;

        public ListenerSocket(Socket socket) {
            try {
                this.saida = new ObjectOutputStream(socket.getOutputStream());
                this.entrada = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
            }
        }

        @Override
        public void run() {
            Mensagens mensagem = null;
            try {
                while ((mensagem = (Mensagens) entrada.readObject()) != null) {
                    OperacaoServidor operacao = mensagem.obterOperacao();
                    if (operacao.equals(OperacaoServidor.CONECTAR)) {
                        boolean conectado = conectar(mensagem, saida);
                        if (conectado) {
                            Onlines.put(mensagem.obterNome(), saida);
                            enviarOnlines();
                        }
                    } else if (operacao.equals(OperacaoServidor.DESCONECTAR)) {
                        desconectar(mensagem, saida);
                        enviarOnlines();
                        return;
                    } else if (operacao.equals(OperacaoServidor.ENVIAR_UM)) {
                        enviarUm(mensagem);
                    } else if (operacao.equals(OperacaoServidor.ENVIAR_TODOS)) {
                        enviarTodos(mensagem);
                    }
                }
            } catch (IOException ex) {
                Mensagens cliente = new Mensagens();
                cliente.atualizarNome(mensagem.obterNome());
                desconectar(cliente, saida);
                enviarOnlines();
            } catch (ClassNotFoundException ex) {
            }
        }
    }

    private boolean conectar(Mensagens mensagem, ObjectOutputStream saida) {
        if (Onlines.containsKey(mensagem.obterNome())) {
            mensagem.atualizarTexto("NAO");
            send(mensagem, saida);
            return false;
        } else {
            mensagem.atualizarTexto("SIM");
            send(mensagem, saida);
            mensagem.atualizarTexto(" - conectou - ");
            mensagem.atualizarOperacao(OperacaoServidor.ENVIAR_UM);
            enviarTodos(mensagem);
            return true;
        }
    }

    private void desconectar(Mensagens mensagem, ObjectOutputStream saida) {
        Onlines.remove(mensagem.obterNome());
        mensagem.atualizarTexto(" - desconectou - ");
        mensagem.atualizarOperacao(OperacaoServidor.ENVIAR_UM);
        enviarTodos(mensagem);
    }
    
    private void send(Mensagens mensagem, ObjectOutputStream saida) {
        try {
            saida.writeObject(mensagem);
        } catch (IOException ex) {
        }
    }

    private void enviarOnlines() {
        Set<String> atualizarNomes = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> map : Onlines.entrySet()) {
            atualizarNomes.add(map.getKey());
        }
        Mensagens mensagem = new Mensagens();
        mensagem.atualizarOperacao(OperacaoServidor.CLIENTES_ONLINE);
        mensagem.atualizarClientes(atualizarNomes);
        for (Map.Entry<String, ObjectOutputStream> map : Onlines.entrySet()) {
            mensagem.atualizarNome(map.getKey());
            try {
                map.getValue().writeObject(mensagem);
            } catch (IOException ex) {
            }
        }
    }
    
    private void enviarTodos(Mensagens mensagem) {
        for (Map.Entry<String, ObjectOutputStream> map : Onlines.entrySet()) {
            if (!map.getKey().equals(mensagem.obterNome())) {
                mensagem.atualizarOperacao(OperacaoServidor.ENVIAR_UM);
                try {
                    map.getValue().writeObject(mensagem);
                } catch (IOException ex) {
                }
            }
        }
    }
    
    private void enviarUm(Mensagens mensagem) {
        for (Map.Entry<String, ObjectOutputStream> map : Onlines.entrySet()) {
            if (map.getKey().equals(mensagem.obterNomeExistente())) {
                try {
                    String M = mensagem.obterTexto();
                    mensagem.atualizarTexto(" - privado - \n" + M);
                    map.getValue().writeObject(mensagem);
                } catch (IOException ex) {
                }
            }
        }
    }
}
