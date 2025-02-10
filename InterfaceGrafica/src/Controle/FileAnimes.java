package Controle;

import Modelo.Anime;
import Modelo.Episodio;
import Modelo.Temporada;
import Persistencia.DAOs.AnimeDAO;
import Persistencia.DAOs.EpisodioDAO;
import Persistencia.DAOs.TemporadaDAO;
import Persistencia.FileDAOs.FileAnimeDAO;
import Persistencia.FileDAOs.FileEpisodioDAO;
import Persistencia.FileDAOs.FileTemporadaDAO;

import java.io.IOException;

public class FileAnimes implements SistemaAnimes{
    SistemaGeral sistema;
    private final AnimeDAO animeDAO;
    private final TemporadaDAO temporadaDAO;
    private final EpisodioDAO episodioDAO;

    public FileAnimes(SistemaGeral sistema) {
        this.sistema = sistema;
        animeDAO = FileAnimeDAO.getInstance();
        temporadaDAO = FileTemporadaDAO.getInstance();
        episodioDAO = FileEpisodioDAO.getInstance();
    }

    @Override
    public boolean cadastrarAnime(String nome, int id) {
        if(animeDAO.adicionarAnime(nome, id)) {
            sistema.getCatalogo().adicionaAnime(animeDAO.buscaAnime(id));
            return true;
        }
        return false;
    }

    @Override
    public boolean removerAnime(Anime anime) {
        try {
            if (animeDAO.excluirAnime(anime)) {
                sistema.getCatalogo().removeAnime(anime.getNome());
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao excluir anime: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean adicionarTemporada(Anime anime, Temporada temporada) {
        try {
            return (temporadaDAO.adicionarTemporada(anime, temporada));
        } catch (Exception e) {
            System.out.println("Erro ao adicionar temporada: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removerTemporada(Anime anime, int id) {
        try {
            return  (temporadaDAO.removerTemporada(anime, id));
        } catch (Exception e) {
            System.out.println("Erro ao excluir temporada: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean adicionarEpisodio(Temporada temporada, Episodio episodio) {
        try {
            return  (episodioDAO.adicionaEpisodio(temporada, episodio));
        } catch (Exception e) {
            System.out.println("Erro ao adicionar episodio: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removerEpisodio(Temporada temporada, int id) {
        try {
            return  (episodioDAO.removeEpisodio(temporada, id));
        } catch (Exception e) {
            System.out.println("Erro ao adicionar episodio: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Temporada buscarTemporada(Anime anime, int id) {
        return temporadaDAO.buscaTemporada(anime, id);
    }

    @Override
    public Episodio buscarEpisodio(Temporada temporada, int id) {
        return episodioDAO.buscaEpisodio(temporada, id);
    }

    @Override
    public void preencheCatalogo() {
        if((sistema.getCatalogo().animes = animeDAO.carregarAnimes()) == null) {
            System.err.println("Problema ao carregar animes");
            System.exit(1);
        }
    }

    @Override
    public void play(String videoPath) {
        try {
            // Para sistemas Windows
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Usa o comando 'start' para abrir o arquivo com o reprodutor padrão
                Runtime.getRuntime().exec("cmd /c start " + videoPath);
            }
            // Para sistemas Unix/Linux/MacOS
            else if (System.getProperty("os.name").toLowerCase().contains("nix") ||
                    System.getProperty("os.name").toLowerCase().contains("nux") ||
                    System.getProperty("os.name").toLowerCase().contains("mac")) {
                // Usa o comando 'open' para MacOS ou 'xdg-open' para Linux
                String command = System.getProperty("os.name").toLowerCase().contains("mac") ?
                        "open " + videoPath : "xdg-open " + videoPath;
                Runtime.getRuntime().exec(command);
            }
        } catch (IOException e) {
            System.out.println("Erro ao tentar abrir o vídeo: " + e.getMessage());
        }
    }


}
