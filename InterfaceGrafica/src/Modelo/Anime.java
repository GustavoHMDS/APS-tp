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

    public int adicionarTemporada(String nome, int codigo) {
        File arquivo = new File(this.path);
        if(arquivo.exists() && arquivo.isDirectory()) {
            String temporadaPath = this.path + "temporada" + (this.temporadasQuantidade+1) + "/";
            File novaTemporada = new File(temporadaPath);
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
                    this.temporadasQuantidade++;
                    return 1;
                } catch (Exception e) {
                    System.out.println("Não foi possivel salvar dados da temporada. " + e);
                }
            }
        }
        return 0;
    }

    public Temporada getTemporada(int indice) {
        if(indice > this.temporadasQuantidade) return null;
        File pastaTemporada = new File(this.path + "temporada" + indice + "/");
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
                return new Temporada(nome, codigo, episodios, path);
            }
        } catch (Exception e) {
            System.out.println("Não foi possível ler dados da temporada. " + e);
        }
        return null;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setTemporadasQuantidade(int temporadasQuantidade) {
        this.temporadasQuantidade = temporadasQuantidade;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    public int getTemporadasQuantidade() {
        return temporadasQuantidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getPath() {
        return path;
    }
}
