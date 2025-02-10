package Modelo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    public List<Anime> animes = new ArrayList<>();

    public void adicionaAnime(Anime anime) {
        animes.add(anime);
    }

    public boolean removeAnime(String nome) {
        for(Anime anime : animes) {
            if(anime.getNome().equals(nome)) {
                animes.remove(anime);
                return true;
            }
        }
        return false;
    }

    public Anime getAnime(String nome) {
        for (Anime anime : this.animes) {
            if (anime.getNome().equals(nome)) return anime;
        }
        return null;
    }

    public int getSize() {
        return this.animes.size();
    }
}

