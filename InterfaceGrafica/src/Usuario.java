public abstract class Usuario {
    int id;
    long CPF;
    int idade;
    String nome;
    TipoUsuario tipo;

    public Usuario(int id, long CPF, int idade, String nome, TipoUsuario tipo){
        this.id = id;
        this.CPF = CPF;
        this.idade = idade;
        this.nome = nome;
        this.tipo = tipo;
    }
}
