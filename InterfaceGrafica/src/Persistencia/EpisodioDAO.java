package Persistencia;

import Modelo.Episodio;
import Modelo.Temporada;

public interface EpisodioDAO {
    Episodio buscaEpisodio(int id);
    Episodio carregaEpisodios(String nombre);
    boolean adicionaEpisodio(Temporada temporada, Episodio episodio);
    boolean removeEpisodio(Temporada temporada, int id);
}
