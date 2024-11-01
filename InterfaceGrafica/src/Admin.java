public class Admin extends Usuario{
    float salario;
    public Admin(int id, int CPF, int idade, String nome, TipoUsuario tipo, float salario) {
        super(id, CPF, idade, nome, tipo);
        this.salario = salario;
    }
}
