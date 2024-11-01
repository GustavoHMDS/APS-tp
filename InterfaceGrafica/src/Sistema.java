import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Sistema {
    public static Usuario usuario;

    public static boolean sistemaLogin(String usuario, String senha) {
        try (BufferedReader leitor = new BufferedReader(new FileReader("C:\\Users\\Gustavo\\Documents\\faculdade\\APS\\InterfaceGrafica\\src\\UsuariosLogin"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes.length >= 2 && partes[0].trim().equals("CPF") && partes[1].trim().equals(usuario)) {
                    String CPF = partes[1].trim();
                    linha = leitor.readLine(); // Lê a linha de senha
                    if (linha != null && linha.split(":")[1].trim().equals(senha)) {
                        System.out.println("CPF e senhas corretos. Buscando dados do usuario");
                        Sistema.usuario = buscaUsuario(CPF);
                        if (Sistema.usuario == null) {
                            System.out.println("Erro de sincronia entre UsuariosLogin e Usuarios para o CPF: " + CPF);
                            return false;
                        }
                        return true;
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao ler o arquivo de usuários: " + ex.getMessage());
            return false;
        }
        return false;
    }

    public static Usuario buscaUsuario(String CPF) {
        try (BufferedReader leitor = new BufferedReader(new FileReader("C:\\Users\\Gustavo\\Documents\\faculdade\\APS\\InterfaceGrafica\\src\\Usuarios"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(":");
                System.out.println("Buscando Usuario...");
                if (partes.length >= 2 && partes[0].trim().equals("CPF") && partes[1].trim().equals(CPF)) {
                    System.out.println("Usuario encontrado! lendo dados...");
                    String nome = leitor.readLine().split(":")[1].trim();
                    int idade = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                    int id = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                    String tipo = leitor.readLine().split(":")[1].trim();
                    System.out.println("Definindo tipo Usuario...");
                    switch (tipo) {
                        case "Aluno":
                            int semestre = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                            String[] historicoAluno = leitor.readLine().split(":")[1].trim().split(",");
                            System.out.println("Criando Usuario...");
                            return new Aluno(id, Long.parseLong(CPF), idade, nome, TipoUsuario.Aluno, semestre, historicoAluno);
                        case "Professor":
                            String grau = leitor.readLine().split(":")[1].trim();
                            float salarioProfessor = Float.parseFloat(leitor.readLine().split(":")[1].trim());
                            String[] historicoProfessor = leitor.readLine().split(":")[1].trim().split(",");
                            System.out.println("Criando Usuario...");
                            return new Professor(id, Integer.parseInt(CPF), idade, nome, TipoUsuario.Professor, salarioProfessor, grau, historicoProfessor);
                        case "Admin":
                            float salarioAdmin = Float.parseFloat(leitor.readLine().split(":")[1].trim());
                            System.out.println("Criando Usuario...");
                            return new Admin(id, Integer.parseInt(CPF), idade, nome, TipoUsuario.Admin, salarioAdmin);
                        default:
                            System.out.println("Erro de formatação no usuário com CPF: " + CPF);
                            return null;
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao ler o arquivo de usuários: " + ex.getMessage());
            return null;
        }
        return null;
    }
}
