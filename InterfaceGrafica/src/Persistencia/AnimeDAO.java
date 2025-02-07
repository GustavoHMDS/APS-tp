package Persistencia;

import Modelo.Anime;

import java.io.IOException;
import java.util.List;

public interface AnimeDAO {
    List<Anime> carregarAnimes();
    boolean adicionarAnime(String nome, int codigo);
    boolean excluirAnime(Anime anime) throws IOException;
}
