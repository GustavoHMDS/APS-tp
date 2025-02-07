package Persistencia;

import Modelo.Episodio;

public class FileEpisodioDAO implements EpisodioDAO {
    @Override
    public Episodio buscaEpisodio(int id) {
        return null;
    }

    @Override
    public Episodio carregaEpisodios(String nombre) {
        return null;
    }

    @Override
    public boolean adicionaEpisodio(Episodio episodio, int id) {
        return false;
    }

    @Override
    public boolean removeEpisodio(int id) {
        return false;
    }
}
