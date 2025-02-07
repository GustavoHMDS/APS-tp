package Persistencia;

import Modelo.Anime;

import java.util.List;

public interface AnimeDAO {
    Anime buscarAnime(String nome);
    List<Anime> carregarAnimes();
    boolean adicionarAnime(Anime anime);
    boolean excluirAnime(Anime anime);
}
