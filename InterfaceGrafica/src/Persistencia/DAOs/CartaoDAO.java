package Persistencia.DAOs;

import Modelo.Cartao;

import java.io.IOException;
import java.util.List;

public interface CartaoDAO {
    List<Cartao> buscaCartoes(String email) throws IOException;
    boolean cadastrarCartao(Cartao cartao, String email) throws IOException;
    boolean editaCartao(String email, int cartaoIndice, long numeroCartao, int codigoCartao);
    boolean excluiCartao(String email, int cartaoIndice);
}
