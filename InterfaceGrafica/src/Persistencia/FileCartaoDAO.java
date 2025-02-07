package Persistencia;

import Modelo.Cartao;

import java.util.List;

public class FileCartaoDAO implements CartaoDAO{
    @Override
    public List<Cartao> buscaCartoes(String email) {
        return List.of();
    }

    @Override
    public boolean cadastrarCartao(Cartao cartao, String email) {
        return false;
    }

    @Override
    public boolean editaCartao(Cartao cartao, String email) {
        return false;
    }
}
