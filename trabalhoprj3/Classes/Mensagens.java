package trabalhoprj3.Classes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import trabalhoprj3.OperacaoServidor;

public class Mensagens implements Serializable {
    
    private String nome;
    private String texto;
    private String nomeExistente;
    private Set<String> atualizarClientes = new HashSet<String>();
    private OperacaoServidor operacao;

    public String obterNome() {
        return nome;
    }
    public String obterTexto() {
        return texto;
    }
    public String obterNomeExistente() {
        return nomeExistente;
    }
    public Set<String> obterClientes() {
        return atualizarClientes;
    }
    public void atualizarNome(String nome) {
        this.nome = nome;
    }
    public void atualizarTexto(String texto) {
        this.texto = texto;
    }
    public void atualizarNomeExistente(String nomeExsistente) {
        this.nomeExistente = nomeExsistente;
    }
    public void atualizarClientes(Set<String> atualizarClientes) {
        this.atualizarClientes = atualizarClientes;
    }
    public OperacaoServidor obterOperacao() {
        return operacao;
    }
    public void atualizarOperacao(OperacaoServidor operacao) {
        this.operacao = operacao;
    }
}
