public class Professor extends Usuario{
    float salario;
    String grau;
    String[] historico;

    public Professor(int id, long CPF, int idade, String nome, TipoUsuario tipo, float salario, String grau, String[] historico) {
        super(id, CPF, idade, nome, tipo);
        this.salario = salario;
        this.grau = grau;
        this.historico = historico;
    }
}
