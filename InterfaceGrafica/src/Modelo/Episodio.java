package Modelo;

import java.io.File;

public class Episodio {
    private String nome;
    private int codigo;
    private File video;

    Episodio(String nome, int codigo, File video) {
        this.nome = nome;
        this.codigo = codigo;
        this.video = video;
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public File getVideo() {
        return video;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setVideo(File video) {
        this.video = video;
    }


}
