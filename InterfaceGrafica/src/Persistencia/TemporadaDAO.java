package Persistencia;

import Modelo.Temporada;

import java.util.List;

public interface TemporadaDAO {
    Temporada buscaTemporada(int id);
    List<Temporada> carregaTemporadas();
    boolean adicionarTemporada(Temporada temporada, int id);
    boolean removerTemporada(int id);
}
