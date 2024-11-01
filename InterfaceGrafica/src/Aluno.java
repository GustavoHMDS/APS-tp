public class Aluno extends Usuario{
    int semestre;
    String[] historico;
    public Aluno(int id, long CPF, int idade, String nome, TipoUsuario tipo, int semestre, String[] historico) {
        super(id, CPF, idade, nome, tipo);
        this.semestre = semestre;
        this.historico = historico;
    }
}
