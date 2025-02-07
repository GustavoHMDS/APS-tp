package Persistencia;

import Modelo.Anime;

import java.util.List;

public class FileAnimeDAO implements AnimeDAO {
    @Override
    public Anime buscarAnime(String nome) {
        return null;
    }

    @Override
    public List<Anime> carregarAnimes() {
        return List.of();
    }

    @Override
    public boolean adicionarAnime(Anime anime) {
        return false;
    }

    @Override
    public boolean excluirAnime(Anime anime) {
        return false;
    }
}
