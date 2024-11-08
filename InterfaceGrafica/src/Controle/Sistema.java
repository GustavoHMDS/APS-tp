package Controle;

import Modelo.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Sistema {
    public static Usuario usuario;
    private static LocalDate dataAtual;

    public static boolean sistemaLogin(String usuario, String senha) {
        try (BufferedReader leitor = new BufferedReader(new FileReader("src/UsuariosLogin"))) {
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
        try (BufferedReader leitor = new BufferedReader(new FileReader("src/Usuarios"))) {
            String linha;
            System.out.println("Buscando Usuario...");
            while ((linha = leitor.readLine()) != null) {
                System.out.println("Linha atual: " + linha); // Debug
                String[] partes = linha.split(":");

                // Verifica se encontramos o CPF correto
                if (partes.length >= 2 && partes[0].trim().equals("CPF") && partes[1].trim().equals(CPF)) {
                    System.out.println("Usuario encontrado! lendo dados...");

                    // Lendo os dados comuns a todos os usuários
                    String nome = leitor.readLine().split(":")[1].trim();
                    String data = leitor.readLine().split(":")[1].trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataNascimento = LocalDate.parse(data, formatter);
                    String email = leitor.readLine().split(":")[1].trim();
                    String senha = leitor.readLine().split(":")[1].trim();
                    String tipo = leitor.readLine().split(":")[1].trim();

                    System.out.println("Tipo lido: " + tipo + "!");

                    // Criando o objeto de acordo com o tipo
                    switch (tipo) {
                        case "Cliente": {
                            long numeroCartao = Long.parseLong(leitor.readLine().split(":")[1].trim());
                            int codigoCartao = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                            data = leitor.readLine().split(":")[1].trim();
                            LocalDate validadeCartao = LocalDate.parse(data, formatter);
                            data = leitor.readLine().split(":")[1].trim();
                            LocalDate vencimento = LocalDate.parse(data, formatter);
                            int idAssinatura = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                            Cartao cartao = new Cartao(numeroCartao, codigoCartao, validadeCartao);
                            Assinatura assinaturaCliente = new Assinatura(vencimento, idAssinatura);
                            Cliente cliente = new Cliente(CPF, dataNascimento, nome, email, senha, assinaturaCliente, cartao);

                            System.out.println("Cliente criado com sucesso: " + cliente.getNome());

                            // Retornar imediatamente
                            return cliente;
                        }

                        case "Admin": {
                            int id = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                            Admin admin = new Admin(CPF, dataNascimento, nome, email, senha, id);

                            System.out.println("Admin criado com sucesso: " + admin.getNome());

                            // Retornar imediatamente
                            return admin;
                        }

                        default:
                            System.out.println("Erro de formatação no tipo de usuário: " + tipo);
                            return null;
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao ler o arquivo de usuários: " + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace(); // Adiciona rastreamento de pilha para entender o erro
        }
        System.out.println("Usuario não encontrado.");
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

    public static String getNomeUuario(){
        if (Sistema.usuario == null) {
            return "Visitante";
        } else return Sistema.usuario.getNome();
    }
    public static void defineDataAtual(){
        dataAtual = LocalDate.now();
    }
    public static LocalDate verificaData(){
        return dataAtual;
    }
}
