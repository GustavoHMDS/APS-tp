package Persistencia.FileDAOs;

import Modelo.Episodio;
import Modelo.Temporada;
import Persistencia.DAOs.EpisodioDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileEpisodioDAO implements EpisodioDAO {
    private static FileEpisodioDAO instance;

    private FileEpisodioDAO() {}

    public static FileEpisodioDAO getInstance() {
        if(instance == null) instance = new FileEpisodioDAO();
        return instance;
    }

    @Override
    public List<Episodio> carregaEpisodios(Temporada temporada) {
        List<Episodio> episodios = new ArrayList<>();
        File temporadaPath = new File(temporada.getPath());

        if (!temporadaPath.exists() || !temporadaPath.isDirectory()) {
            return episodios;
        }

        File episodiosDir = new File(temporadaPath, "episodios");
        if (!episodiosDir.exists() || !episodiosDir.isDirectory()) {
            return episodios;
        }

        File[] arquivos = episodiosDir.listFiles((_, name) -> name.endsWith(".txt"));
        if (arquivos == null) {
            return episodios;
        }

        for (File arquivo : arquivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String nome = "";
                int codigo = -1;
                String path = "";

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
                            case "Path":
                                path = partes[1];
                                break;
                        }
                    }
                }

                if (!nome.isEmpty() && codigo != -1) {
                    episodios.add(new Episodio(nome, temporada, codigo, path));
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return episodios;
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
