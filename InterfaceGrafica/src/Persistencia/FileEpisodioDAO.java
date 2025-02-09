package Persistencia;

import Modelo.Episodio;
import Modelo.Temporada;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileEpisodioDAO implements EpisodioDAO {
    @Override
    public Episodio buscaEpisodio(int id) {
        return null;
    }

    @Override
    public Episodio carregaEpisodios(String nombre) {
        return null;
    }

    @Override
    public boolean adicionaEpisodio(Temporada temporada, Episodio episodio) {
        File arquivoTemporada = new File(temporada.getPath());
        File pastaEpisodios = new File(arquivoTemporada, "episodios/");
        if(!pastaEpisodios.exists()) pastaEpisodios.mkdir();
        File novoEpisodioDados = new File(temporada.getPath() + "episodio" + (temporada.getEpisodiosQuantidade()+1) + ".txt");
        try{
            novoEpisodioDados.createNewFile();
            List<String> dados = new ArrayList<>();
            dados.add("Nome: " + episodio.getNome());
            dados.add("Codigo: " + episodio.getCodigo());
            dados.add("Path: " + temporada.getPath() + episodio.getPath());
            Files.write(novoEpisodioDados.toPath(), dados);
            temporada.setEpisodiosQuantidade(temporada.getEpisodiosQuantidade()+1);
            File temporadaDados = new File(temporada.getPath() + "dados.txt");
            List<String> dadosTemporada = Files.readAllLines(temporadaDados.toPath());
            dadosTemporada.set(2, "Episodios: " + temporada.getEpisodiosQuantidade());
            Files.write(temporadaDados.toPath(), dadosTemporada);
            return true;
        } catch(Exception e) {
            System.out.println("Não foi possível guardar as informações do episódio.");
        }
        return false;
    }

    @Override
    public boolean removeEpisodio(Temporada temporada, int id) {
        if(temporada.getEpisodiosQuantidade() < 1) return false;
        File temporadaPasta = new File(temporada.getPath());
        if(temporadaPasta.exists()) {
            File[] arquivos = temporadaPasta.listFiles();
            int episodioIndice = 0;
            for(File arquivo : arquivos) {
                if(arquivo.isDirectory() == false && arquivo.getName().contains("episodio")) {
                    if(episodioIndice > 0) {
                        arquivo.renameTo(new File(temporada.getPath() + "episodio" + episodioIndice + ".txt"));
                        episodioIndice++;
                    }

                    if(arquivo.getName().contains(""+id)) {
                        arquivo.delete();
                        episodioIndice = id;
                    }
                }
            }
            temporada.setEpisodiosQuantidade(temporada.getEpisodiosQuantidade() - 1);
            try{
                File temporadaDados = new File(temporada.getPath() + "dados.txt");
                List<String> dadosTemporada = Files.readAllLines(temporadaDados.toPath());
                dadosTemporada.set(2, "Episodios: " + temporada.getEpisodiosQuantidade());
                Files.write(temporadaDados.toPath(), dadosTemporada);
                return true;
            } catch(Exception e) {
                System.out.println("Não foi possivel salvar mudanças na temporada!");
            }
        }
        return false;
    }
}
