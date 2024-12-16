package Modelo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Temporada {
    private String nome;
    private int codigo;
    private int episodiosQuantidade;
    private String path;

    Temporada(String nome, int codigo, int episodiosQuantidade, String path) {
        this.nome = nome;
        this.codigo = codigo;
        this.episodiosQuantidade = episodiosQuantidade;
        this.path = path;
    }

    public void adicionarEpisodio(String nome, int codigo, String path) {
        File arquivo = new File(this.path + path);
        if(arquivo.exists() && !arquivo.isDirectory()) {
            this.episodiosQuantidade++;
            File novoEpisodioDados = new File(path + "episodio" + this.episodiosQuantidade + ".txt");
            try{
                novoEpisodioDados.createNewFile();
                List<String> dados = new ArrayList<>();
                dados.add("Nome: " + nome);
                dados.add("Codigo: " + codigo);
                dados.add("Path: " + this.path + path);
                Files.write(novoEpisodioDados.toPath(), dados);
            } catch(Exception e) {
                System.out.println("Não foi possível guardar as informações do episódio.");
            }
        }
    }

    public void removerEpisodio(int indice) {
        if(this.episodiosQuantidade < 1) return;
        File temporadaPasta = new File(this.path);
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
            this.episodiosQuantidade--;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
