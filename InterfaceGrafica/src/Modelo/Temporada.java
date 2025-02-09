package Modelo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Temporada {
    private Anime anime;
    private String nome;
    private int codigo;
    private int episodiosQuantidade;
    private String path;

    public Temporada(String nome, Anime anime, int codigo, int episodiosQuantidade, String path) {
        this.nome = nome;
        this.anime = anime;
        this.codigo = codigo;
        this.episodiosQuantidade = episodiosQuantidade;
        this.path = path;
    }

    public void adicionarEpisodio(String nome, int codigo, String path) {
        File arquivoTemporada = new File(this.path);
        File pastaEpisodios = new File(arquivoTemporada, "episodios/");
        if(!pastaEpisodios.exists()) pastaEpisodios.mkdir();
        File novoEpisodioDados = new File(this.path + "episodio" + (this.episodiosQuantidade+1) + ".txt");
        try{
            novoEpisodioDados.createNewFile();
            List<String> dados = new ArrayList<>();
            dados.add("Nome: " + nome);
            dados.add("Codigo: " + codigo);
            dados.add("Path: " + this.path + path);
            Files.write(novoEpisodioDados.toPath(), dados);
            this.episodiosQuantidade++;
            File temporadaDados = new File(this.path + "dados.txt");
            List<String> dadosTemporada = Files.readAllLines(temporadaDados.toPath());
            dadosTemporada.set(2, "Episodios: " + this.episodiosQuantidade);
            Files.write(temporadaDados.toPath(), dadosTemporada);

        } catch(Exception e) {
            System.out.println("Não foi possível guardar as informações do episódio.");
        }
    }

    public void removerEpisodio(int indice) {
        if(this.episodiosQuantidade < 1) return;
        System.out.println("entrou");
        File temporadaPasta = new File(this.path);
        System.out.println(this.path);
        if(temporadaPasta.exists()) {
            File[] arquivos = temporadaPasta.listFiles();
            int episodioIndice = 0;
            for(File arquivo : arquivos) {
                if(arquivo.isDirectory() == false && arquivo.getName().contains("episodio")) {
                    if(episodioIndice > 0) {
                        arquivo.renameTo(new File(this.path + "episodio" + episodioIndice + ".txt"));
                        episodioIndice++;
                    }

                    if(arquivo.getName().contains(""+indice)) {
                        arquivo.delete();
                        episodioIndice = indice;
                    }
                }
            }
            System.out.println("apagou");
            this.episodiosQuantidade--;
            try{
                File temporadaDados = new File(this.path + "dados.txt");
                List<String> dadosTemporada = Files.readAllLines(temporadaDados.toPath());
                dadosTemporada.set(2, "Episodios: " + this.episodiosQuantidade);
                Files.write(temporadaDados.toPath(), dadosTemporada);
            } catch(Exception e) {
                System.out.println("Não foi possivel salvar mudanças na temporada!");
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getPath() {
        return path;
    }

    public int getEpisodiosQuantidade() {
        return episodiosQuantidade;
    }

    public void setEpisodiosQuantidade(int episodiosQuantidade) {
        this.episodiosQuantidade = episodiosQuantidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
