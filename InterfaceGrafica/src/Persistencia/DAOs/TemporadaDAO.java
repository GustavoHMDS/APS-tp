package Persistencia.DAOs;

import Modelo.Anime;
import Modelo.Temporada;

import java.io.IOException;
import java.util.List;

public interface TemporadaDAO {
    Temporada buscaTemporada(Anime anime, int id);
    List<Temporada> carregaTemporadas(Anime anime) throws IOException;
    boolean adicionarTemporada(Anime anime,Temporada temporada);
    boolean removerTemporada(Anime anime, int id);
}
