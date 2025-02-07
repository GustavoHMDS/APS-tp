package Persistencia;

import Modelo.Episodio;

public interface EpisodioDAO {
    Episodio buscaEpisodio(int id);
    Episodio carregaEpisodios(String nombre);
    boolean adicionaEpisodio(Episodio episodio, int id);
    boolean removeEpisodio(int id);
}
