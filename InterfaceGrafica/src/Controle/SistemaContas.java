package Controle;

import Modelo.Cartao;

public interface SistemaContas {
    public boolean logIn(String email, String senha);
    public boolean logOut();
    public boolean cadastrar(String cpf, String nome, String dataNascimento, String email, String senha, String tipo);
    public boolean editarUsuario(String email, String[] dados);
    public boolean deletarUsuario(String email);
    public boolean adicionarCartao(String email, Cartao cartao);
    public boolean editarCartao(String email, int cartaoIndice, long numeroCartao, int codigoCartao);
    public boolean removerCartao(String email, int cartaoIndice);

    public void adminFailSafe();
}
