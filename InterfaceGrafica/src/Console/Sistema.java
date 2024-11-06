package Console;

import Entidades.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Sistema {
    public static Usuario usuario;

    public static boolean sistemaLogin(String usuario, String senha) {
        try (BufferedReader leitor = new BufferedReader(new FileReader("UsuariosLogin"))) {
            String linha;
            String CPF = "vazio";
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes.length >= 2 && partes[0].trim().equals("email") && partes[1].trim().equals(usuario)) {
                    linha = leitor.readLine(); // Lê a linha de senha
                    if (linha != null && linha.split(":")[1].trim().equals(senha)) {
                        linha = leitor.readLine();
                        CPF = linha.split(":")[1].trim();
                        System.out.println("email e senhas corretos. Buscando dados do usuario");
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
        try (BufferedReader leitor = new BufferedReader(new FileReader("Usuarios"))) {
            String linha;
            System.out.println("Buscando Usuario...");
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes.length >= 2 && partes[0].trim().equals("CPF") && partes[1].trim().equals(CPF)) {
                    System.out.println("Usuario encontrado! lendo dados...");
                    String nome = leitor.readLine().split(":")[1].trim();
                    String data = leitor.readLine().split(":")[1].trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataNascimento = LocalDate.parse(data, formatter);
                    String email = leitor.readLine().split(":")[1].trim();
                    String senha = leitor.readLine().split(":")[1].trim();
                    String tipo = leitor.readLine().split(":")[1].trim();
                    System.out.println("Definindo tipo Usuario...");
                    switch (tipo) {
                        case "Cliente":
                            int numeroCartao = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                            int codigoCartao = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                            data = leitor.readLine().split(":")[1].trim();
                            LocalDate validadeCartao = LocalDate.parse(data, formatter);
                            data = leitor.readLine().split(":")[1].trim();
                            LocalDate vencimento = LocalDate.parse(data, formatter);
                            int idAssinatura = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                            Assinatura assinaturaCliente = new Assinatura(numeroCartao, codigoCartao, validadeCartao, vencimento, idAssinatura);
                            System.out.println("Criando Usuario...");
                            return new Cliente(CPF, dataNascimento, nome, email, senha, assinaturaCliente);
                        case "Admin":
                            int id = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                            System.out.println("Criando Usuario...");
                            return new Admin(CPF, dataNascimento, nome, email, senha, id);
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

    public static void LogOffUsuario(){
        Sistema.usuario = null;
    }

    public static String getTipoUsuario(){
        if (Sistema.usuario instanceof Cliente) {
            return "Cliente";
        }
        else if(Sistema.usuario instanceof Admin){
            return "Admin";
        }
        else return "Guest";
    }
}
