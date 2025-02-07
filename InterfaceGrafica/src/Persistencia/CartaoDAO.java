package Persistencia;

import Modelo.Cartao;

import java.util.List;

public interface CartaoDAO {
    List<Cartao> buscaCartoes(String email);
    boolean cadastrarCartao(Cartao cartao, String email);
    boolean editaCartao(Cartao cartao, String email);
}
