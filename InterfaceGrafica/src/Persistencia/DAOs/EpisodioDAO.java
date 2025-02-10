package Persistencia.DAOs;

import Modelo.Episodio;
import Modelo.Temporada;

import java.util.List;

public interface EpisodioDAO {
    List<Episodio> carregaEpisodios(Temporada temporada);
    boolean adicionaEpisodio(Temporada temporada, Episodio episodio);
    boolean removeEpisodio(Temporada temporada, int id);
}
