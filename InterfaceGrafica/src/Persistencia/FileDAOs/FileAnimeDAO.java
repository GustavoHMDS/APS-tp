package Persistencia.FileDAOs;

import Modelo.Anime;
import Persistencia.DAOs.AnimeDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileAnimeDAO extends FileDAO implements AnimeDAO {
    private final static String BASE_PATH = "animes";

    //Singleton
    private static FileAnimeDAO instance;

    private FileAnimeDAO() {}

    public static AnimeDAO getInstance() {
        if (instance == null) {
            instance = new FileAnimeDAO();
        }
        return instance;
    }

    //Métodos
    @Override
    public List<Anime> carregarAnimes() {
        File pastaAnimes = new File(BASE_PATH + "/");
        List<Anime> listaAnimes = new ArrayList<>();
        if(pastaAnimes.exists() && pastaAnimes.isDirectory()) {
            try{
                File[] animes = pastaAnimes.listFiles();
                if(animes != null) {
                    for (File arquivo : animes) {
                        System.out.println(arquivo.getName());
                        if (!arquivo.isDirectory()) continue;
                        File arquivoDados = new File(arquivo, "dados.txt");
                        if (!arquivoDados.exists()) continue;
                        try {
                            List<String> dados = Files.readAllLines(arquivoDados.toPath());
                            String nome = null, path = null;
                            int codigo = -1, temporadasQtd = -1;
                            for (String dado : dados) {
                                String[] linha = dado.split(": ");
                                switch (linha[0]) {
                                    case "Nome":
                                        nome = linha[1];
                                        break;
                                    case "Codigo":
                                        codigo = Integer.parseInt(linha[1]);
                                        break;
                                    case "Temporadas":
                                        temporadasQtd = Integer.parseInt(linha[1]);
                                        break;
                                    case "Path":
                                        path = linha[1];
                                        break;
                                }
                            }
                            listaAnimes.add(new Anime(nome, codigo, temporadasQtd, path));
                            //System.out.println(SistemaGeral.catalogo.animes.get(SistemaGeral.catalogo.animes.size() - 1).getTemporadasQuantidade());
                        } catch (Exception e) {
                            System.out.println("Não foi possível recuparar dados do anime. " + e);
                        }
                    }
                } else return null;

            } catch(Exception e) {
                System.out.println("Não foi possível acessar os animes! " + e);
                return null;
            }
        }
        return listaAnimes;
    }

    public Anime buscaAnime(int id) {
        File animesDir = new File("animes");
        if (!animesDir.exists() || !animesDir.isDirectory()) {
            return null;
        }

        File[] pastasAnime = animesDir.listFiles(File::isDirectory);
        if (pastasAnime == null) {
            return null;
        }

        for (File pastaAnime : pastasAnime) {
            File dadosAnime = new File(pastaAnime, "dados.txt");
            if (dadosAnime.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(dadosAnime))) {
                    String nome = "";
                    int codigo = -1;
                    int temporadasQuantidade = 0;
                    String path = pastaAnime.getPath() + File.separator;

                    String linha;
                    while ((linha = br.readLine()) != null) {
                        String[] partes = linha.split(": ", 2);
                        if (partes.length == 2) {
                            switch (partes[0]) {
                                case "Nome":
                                    nome = partes[1];
                                    break;
                                case "Codigo":
                                    codigo = Integer.parseInt(partes[1]);
                                    break;
                                case "Temporadas":
                                    temporadasQuantidade = Integer.parseInt(partes[1]);
                                    break;
                                case "Path":
                                    path = partes[1];
                                    break;
                            }
                        }
                    }

                    if (codigo == id) {
                        return new Anime(nome, codigo, temporadasQuantidade, path);
                    }
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public boolean adicionarAnime(String nome, int codigo) {
        File pastaAnimes = new File(BASE_PATH + "/");
        if(!pastaAnimes.exists()) {
            if(!pastaAnimes.mkdir()) return false;
        }
        File anime = new File(pastaAnimes, nome.replace(" ", "-") + "/");
        if(!anime.mkdir()) {
            return false;
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

            return true;
        } catch (Exception e) {
            System.out.println("Não foi possível salvar dados do anime! " + e);
            return false;
        }
    }

    @Override
    public boolean excluirAnime(Anime anime) {
        if (anime == null) {
            System.err.println("O anime não pode ser nulo.");
            return false;
        }

        File animePasta = new File(anime.getPath());

        if (!animePasta.exists()) {
            System.err.println("O anime não foi encontrado no sistema de arquivos.");
            return false;
        }

        // Deleta a pasta e remove do catálogo
        if (deletarPastaRecursivamente(animePasta)) {
            System.out.println("Anime excluído com sucesso: " + anime.getNome());
        } else {
            System.err.println("Erro ao excluir o anime: " + anime.getNome());
            return false;
        }
        return true;
    }
}
