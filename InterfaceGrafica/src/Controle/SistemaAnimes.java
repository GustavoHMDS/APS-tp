package Controle;

import Modelo.Anime;
import Modelo.Episodio;
import Modelo.Temporada;
import Modelo.Usuario;

public interface SistemaAnimes {
    boolean cadastrarAnime(String nome, int id);
    boolean removerAnime(Anime anime);
    boolean adicionarTemporada(Anime anime, Temporada temporada);
    boolean removerTemporada(Anime anime, int id);
    boolean adicionarEpisodio(Temporada temporada, Episodio episodio);
    boolean removerEpisodio(Temporada temporada, int id);
    Temporada buscarTemporada(Anime anime, int id);
    Episodio buscarEpisodio(Temporada temporada, int id);
    void preencheCatalogo();
    void play(String videoPath);
}
