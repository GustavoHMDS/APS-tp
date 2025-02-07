package Modelo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    public List<Anime> animes = new ArrayList<>();

    public int adicionaAnime(String nome, int codigo) {
        File pastaAnimes = new File("animes/");
        if(!pastaAnimes.exists()) {
            if(!pastaAnimes.mkdir()) return 0;
        }
        File anime = new File(pastaAnimes, nome.replace(" ", "-") + "/");
        if(!anime.mkdir()) {
            return 0;
        }
        File dadosAnime = new File(anime, "dados.txt");
        try{
            dadosAnime.createNewFile();
            List<String> dados = new ArrayList<>();
            dados.add("Nome: " + nome);
            dados.add("Codigo: " + codigo);
            dados.add("Temporadas: " + 0);
            dados.add("Path: animes/" + nome.replace(" ", "-") + "/");
            Files.write(dadosAnime.toPath(), dados);

            this.animes.add(new Anime(nome, codigo, 0,"animes/" + nome.replace(" ", "-") + "/"));
            System.out.println(this.animes.size());
        } catch (Exception e) {
            System.out.println("Não foi possível salvar dados do anime! " + e);
        }

        return 1;
    }

    public int removeAnime(String nome) {
        int i = 0;
        for(Anime anime : animes) {
            if(anime.getNome().equals(nome)) {
                this.animes.remove(i);
                return 1;
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

