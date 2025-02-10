package Persistencia.DAOs;

import Modelo.Episodio;
import Modelo.Temporada;

import java.util.List;

public interface EpisodioDAO {
    List<Episodio> carregaEpisodios(Temporada temporada);
    public Episodio buscaEpisodio(Temporada temporada, int id);
    boolean adicionaEpisodio(Temporada temporada, Episodio episodio);
    boolean removeEpisodio(Temporada temporada, int id);
}
