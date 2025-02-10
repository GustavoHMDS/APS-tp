package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Anime {
    private String nome;
    private int codigo;
    private int temporadasQuantidade;
    private List<Temporada> temporadas;
    private String path;

    public Anime(String nome, int codigo, int temporadasQuantidade, String path) {
        this.nome = nome;
        this.codigo = codigo;
        this.temporadasQuantidade = temporadasQuantidade;
        temporadas = new ArrayList<>();
        this.path = path;
    }

    public boolean adicionarTemporada(Temporada temporada) {
        temporadas.add(temporada);
        temporadasQuantidade++;
        return true;
    }

    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    public boolean removerTemporada(int indice) {
        for(Temporada temporada : temporadas) {
            if(temporada.getCodigo() == indice) {
                temporadas.remove(temporada);
                temporadasQuantidade--;
                return true;
            }
        }
        return false;
    }

    public Temporada getTemporada(int codigo) {
       for(Temporada temporada : temporadas) {
           if(temporada.getCodigo() == codigo) return temporada;
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
