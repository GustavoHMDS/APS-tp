package Persistencia.DAOs;

import Modelo.Episodio;
import Modelo.Temporada;

public interface EpisodioDAO {
    Episodio buscaEpisodio(int id);
    Episodio carregaEpisodios(String nome);
    boolean adicionaEpisodio(Temporada temporada, Episodio episodio);
    boolean removeEpisodio(Temporada temporada, int id);
}
