package Controle;

import Persistencia.DAOs.AnimeDAO;
import Persistencia.DAOs.EpisodioDAO;
import Persistencia.DAOs.TemporadaDAO;
import Persistencia.FileDAOs.FileAnimeDAO;
import Persistencia.FileDAOs.FileEpisodioDAO;
import Persistencia.FileDAOs.FileTemporadaDAO;

import java.io.IOException;

public class FileAnimes implements SistemaAnimes{
    SistemaGeral sistema;
    private AnimeDAO animeDAO;
    private TemporadaDAO temporadaDAO;
    private EpisodioDAO episodioDAO;

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
