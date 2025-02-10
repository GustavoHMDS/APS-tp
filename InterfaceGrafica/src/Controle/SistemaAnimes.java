package Controle;

import Modelo.Anime;
import Modelo.Usuario;

public interface SistemaAnimes {
    boolean cadastrarAnime(String nome, int id);
    boolean removerAnime(int id);
    boolean adicionarTemporada();
    boolean removerTemporada();
    boolean adicionarEpisodio();
    boolean removerEpisodio();
    void preencheCatalogo();
    void play(String videoPath);
}
