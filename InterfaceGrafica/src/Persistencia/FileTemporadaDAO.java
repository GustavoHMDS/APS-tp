package Persistencia;

import Modelo.Temporada;

import java.util.List;

public class FileTemporadaDAO implements TemporadaDAO {
    @Override
    public Temporada buscaTemporada(int id) {
        return null;
    }

    @Override
    public List<Temporada> carregaTemporadas() {
        return List.of();
    }

    @Override
    public boolean adicionarTemporada(Temporada temporada, int id) {
        return false;
    }

    @Override
    public boolean removerTemporada(int id) {
        return false;
    }
}
