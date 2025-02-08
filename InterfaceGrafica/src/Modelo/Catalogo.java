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

    public int removeAnime(String nome) {
        int i = 0;
        for(Anime anime : animes) {
            if(anime.getNome().equals(nome)) {
                for(int j = anime.getTemporadasQuantidade(); j > 0; j--) {
                    anime.removerTemporada(j);
                }
                File animePasta = new File(anime.getPath());
                File[] arquivos = animePasta.listFiles();
                for(File arquivo : arquivos) {
                    arquivo.delete();
                }
                if(animePasta.delete()) {
                    this.animes.remove(i);
                    return 1;
                }
            }
            i++;
        }
        return 0;
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

