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
        } catch (Exception e) {
            System.out.println("Não foi possível salvar dados do anime! " + e);
        }

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

    public Anime getAnime(String nome) {
        for(int i = 0; i < this.animes.size(); i++) {
            if(animes.get(i).getNome().equals(nome)) return animes.get(i);
        }
        return null;
    }

    public int getSize() {
        return this.animes.size();
    }
}

