package Modelo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Anime {
    private String nome;
    private int codigo;
    private int temporadasQuantidade;
    private String path;

    public Anime(String nome, int codigo, int temporadasQuantidade, String path) {
        this.nome = nome;
        this.codigo = codigo;
        this.temporadasQuantidade = temporadasQuantidade;
        this.path = path;
    }

    public void adicionarTemporada(String nome, int codigo) {
        File arquivo = new File(this.path);
        if(arquivo.exists() && arquivo.isDirectory()) {
            String temporadaPath = this.path + "temporada" + this.temporadasQuantidade+1 + "/";
            File novaTemporada = new File(path);
            if(novaTemporada.mkdir()) {
                File temporadaDados = new File(temporadaPath + "dados.txt");
                try{
                    temporadaDados.createNewFile();
                    List<String> dados = new ArrayList<>();
                    dados.add("Nome: " + nome);
                    dados.add("Codigo: "+ codigo);
                    dados.add("Episodios: 0");
                    dados.add("Path: " + temporadaPath);
                    Files.write(temporadaDados.toPath(), dados);
                } catch (Exception e) {
                    System.out.println("Não foi possivel salvar dados da temporada. " + e);
                }
            }
        }
    }


    public void setPath(String path) {
        this.path = path;
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
}
