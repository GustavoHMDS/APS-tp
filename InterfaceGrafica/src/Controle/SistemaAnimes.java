package Controle;

import Modelo.Anime;
import Modelo.Usuario;

public interface SistemaAnimes {
    boolean cadastrarAnime(String nome, int id);
    void preencheCatalogo();
    void play(String videoPath);
}
