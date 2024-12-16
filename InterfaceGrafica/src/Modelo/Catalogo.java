package Modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    public List<Anime> animes = new ArrayList<>();

    public int adicionaAnime(String nome, int codigo) {
        File pastaAnimes = new File("animes/");
        if(!pastaAnimes.exists()) {
            if(pastaAnimes.mkdir() == false) return 0;
        }
        File anime = new File(pastaAnimes, nome.replace(" ", "-") + "/");
        if(anime.mkdir() == false) {
            return 0;
        }
        this.animes.add(new Anime(nome, codigo, nome.replace(" ", "-") + "/"));

        return 1;
    }

    public int removeAnime(String nome) {
        for(Anime anime : animes) {
            if(anime.getNome().equals(nome)) {
                File pasta = new File("animes/" + anime.getPath());
                if(pasta.exists() && pasta.isDirectory()) {
                    pasta.delete();
                    this.animes.remove(anime);
                    break;
                } else {
                    System.out.println("Diretório não encontrado!");
                    return 0;
                }
            }
        }
        return 1;
    }

    public int getSize() {
        return this.animes.size();
    }
}

