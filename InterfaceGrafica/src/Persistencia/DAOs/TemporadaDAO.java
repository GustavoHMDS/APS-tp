package Persistencia.DAOs;

import Modelo.Anime;
import Modelo.Temporada;

import java.util.List;

public interface TemporadaDAO {
    Temporada buscaTemporada(Anime anime, int id);
    List<Temporada> carregaTemporadas();
    boolean adicionarTemporada(Anime anime,Temporada temporada);
    boolean removerTemporada(Anime anime, int id);
}
