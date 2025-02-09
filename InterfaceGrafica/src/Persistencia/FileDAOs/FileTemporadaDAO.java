package Persistencia.FileDAOs;

import Modelo.Anime;
import Modelo.Temporada;
import Persistencia.DAOs.TemporadaDAO;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileTemporadaDAO extends FileDAO implements TemporadaDAO {
    private static FileTemporadaDAO instance;

    private FileTemporadaDAO() {}

    public static FileTemporadaDAO getInstance() {
        if(instance == null) instance = new FileTemporadaDAO();
        return instance;
    }

    @Override
    public Temporada buscaTemporada(Anime anime, int id) {
        if(id > anime.getTemporadasQuantidade()) return null;
        File pastaTemporada = new File(anime.getPath() + "temporada" + id + "/");
        if(!pastaTemporada.exists() || !pastaTemporada.isDirectory()) return null;
        File dados = new File(pastaTemporada, "dados.txt");
        if(!dados.exists()) return null;
        try{
            List <String> linhas = Files.readAllLines(dados.toPath());
            String nome = null, path = null;
            int codigo = 0, episodios =  -1;
            for(String linha : linhas) {
                String[] linhaDados = linha.split(": ");
                switch (linhaDados[0]) {
                    case "Nome":
                        nome = linhaDados[1];
                        break;
                    case "Codigo":
                        codigo = Integer.parseInt(linhaDados[1]);
                        break;
                    case "Episodios":
                        episodios = Integer.parseInt(linhaDados[1]);
                        break;
                    case "Path":
                        path = linhaDados[1];
                        break;
                }
            }
            return new Temporada(nome, anime, codigo, episodios, path);
        } catch (Exception e) {
            System.out.println("Não foi possível ler dados da temporada. " + e);
        }
        return null;
    }

    @Override
    public List<Temporada> carregaTemporadas() {
        return List.of();
    }

    @Override
    public boolean adicionarTemporada(Anime anime,Temporada temporada) {
        File arquivo = new File(anime.getPath());
        if(arquivo.exists() && arquivo.isDirectory()) {
            String temporadaPath = anime.getPath() + "temporada" + (anime.getTemporadasQuantidade() + 1) + "/";
            File novaTemporada = new File(temporadaPath);
            if(novaTemporada.mkdir()) {
                File temporadaDados = new File(temporadaPath + "dados.txt");
                try{
                    temporadaDados.createNewFile();
                    List<String> dados = new ArrayList<>();
                    dados.add("Nome: " + temporada.getNome());
                    dados.add("Codigo: "+ temporada.getCodigo());
                    dados.add("Episodios: "+ temporada.getEpisodiosQuantidade());
                    dados.add("Path: " + temporadaPath);
                    Files.write(temporadaDados.toPath(), dados);
                    anime.setTemporadasQuantidade(anime.getTemporadasQuantidade() + 1);
                    File dadosAnimeArquivo = new File(arquivo, "dados.txt");
                    List<String> dadosAnime = Files.readAllLines(dadosAnimeArquivo.toPath());
                    dadosAnime.set(2,"Temporadas: " + anime.getTemporadasQuantidade());
                    Files.write(dadosAnimeArquivo.toPath(), dadosAnime);
                    return true;
                } catch (Exception e) {
                    System.out.println("Não foi possivel salvar dados da temporada. " + e);
                }
            }
        }
        return false;
    }

    @Override
    public boolean removerTemporada(Anime anime, int id) {
        if(anime.getTemporadasQuantidade() < 1) return false;
        File animePasta = new File(anime.getPath());
        if(animePasta.exists()) {
            File[] arquivos = animePasta.listFiles();
            int temporadaIndice = 0;
            System.out.println(arquivos.length);
            for(File arquivo : arquivos) {
                if(arquivo.isDirectory() && arquivo.getName().contains("temporada")) {
                    if(temporadaIndice > 0) {
                        arquivo.renameTo(new File(anime.getPath() + "temporada" + temporadaIndice + "/"));
                        temporadaIndice++;
                    }

                    if(arquivo.getName().contains(""+id)) {
                        boolean res = deletarPastaRecursivamente(arquivo);
                        System.out.println(res);
                        temporadaIndice = id;
                    }
                }
            }
            anime.setTemporadasQuantidade(anime.getTemporadasQuantidade() - 1);
            try{
                File animeDados = new File(anime.getPath() + "dados.txt");
                List<String> dadosAnime = Files.readAllLines(animeDados.toPath());
                dadosAnime.set(2, "Temporadas: " + anime.getTemporadasQuantidade());
                Files.write(animeDados.toPath(), dadosAnime);
                return true;
            } catch(Exception e) {
                System.out.println("Não foi possivel salvar mudanças no anime!");
                return false;
            }
        }
        return false;
    }
}
