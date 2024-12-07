package Controle;

import Modelo.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Sistema {
    public static Usuario usuario;
    public static final String nomeApp = "AniToons";
    private static LocalDate dataAtual;

    public static boolean sistemaLogin(String usuario, String senha) {
        try (BufferedReader leitor = new BufferedReader(new FileReader("src/UsuariosLogin"))) {
            String linha;
            String CPF = "vazio";
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes.length >= 2 && partes[0].trim().equals("Email") && partes[1].trim().equals(usuario)) {
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
                        if (Sistema.usuario instanceof Cliente) Sistema.verificaAssinatura();
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
                    TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipo);

                    // Criando o objeto de acordo com o tipo
                    switch (tipoUsuario) {
                        case TipoUsuario.Cliente: {
                            System.out.println("Cliente encontrado!");
                            long numeroCartao = Long.parseLong(leitor.readLine().split(":")[1].trim());
                            int codigoCartao = Integer.parseInt(leitor.readLine().split(":")[1].trim());
                            data = leitor.readLine().split(":")[1].trim();
                            LocalDate validadeCartao = LocalDate.parse(data, formatter);
                            data = leitor.readLine().split(":")[1].trim();
                            LocalDate vencimento = LocalDate.parse(data, formatter);
                            String tipoAssinatura = leitor.readLine().split(":")[1].trim();
                            System.out.println("Dados Lidos!");
                            Cartao cartao;
                            if(numeroCartao == -1) cartao = null;
                            else cartao = new Cartao(numeroCartao, codigoCartao, validadeCartao);
                            Cliente cliente = new Cliente(CPF, dataNascimento, nome, email, senha, tipoAssinatura.equals("premium"), vencimento);
                            cliente.adicionarCartao(cartao);

                            System.out.println("Cliente criado com sucesso: " + cliente.getNome());

                            // Retornar imediatamente
                            return cliente;
                        }

                        case TipoUsuario.Admin: {
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

    private static int buscaIdDisponivel(String tipoUsuario){
        try (BufferedReader leitor = new BufferedReader(new FileReader("src/Usuarios"))) {
            String linha;
            int id = -1;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(":");
                if (tipoUsuario.equals("Cliente") && partes[0].equals("IdAssinatura")) {
                    id = Integer.parseInt(partes[1].trim());
                } else if(tipoUsuario.equals("Admin") && partes[0].equals("Id")){
                    id = Integer.parseInt(partes[1].trim());
                    }
                }
            return id + 1;
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void adicionarUsuario(String cpf, String email, String senha, String nome, int diaN, int mesN, int anoN, String tipoRegistrando){
        try (FileWriter escritor = new FileWriter("src/UsuariosLogin", true)) { // true para append
            escritor.write("Email: " + email + "\n");
            escritor.write("Senha: " + senha + "\n");
            escritor.write("CPF: " + cpf + "\n");
            System.out.println("Conteúdo adicionado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar conteúdo: " + e.getMessage());
        }

        // Formatação da data para o formato "dd/MM/yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Calcula a validade do cartão (um mês após a data atual)
        LocalDate vencimentoAssinatura = LocalDate.now().plusMonths(1);
        String vencimentoFormatado = vencimentoAssinatura.format(formatter);
        try (FileWriter escritor = new FileWriter("src/Usuarios", true)) { // true para append
            escritor.write("CPF: " + cpf + "\n");
            escritor.write("Nome: " + nome + "\n");
            escritor.write("DataNascimento: " + diaN + "/" + mesN + "/" + anoN + "\n");
            escritor.write("Email: " + email + "\n");
            escritor.write("Senha: " + senha + "\n");
            if(tipoRegistrando.equals("Guest")){
                escritor.write("Tipo: Cliente \n");
                escritor.write("NumeroCartao: -1 \n");
                escritor.write("CodigoCartao: -1 \n");
                escritor.write("ValidadeCartao: 01/01/0001 \n");
                escritor.write("Vencimento: " + vencimentoFormatado + "\n");
                escritor.write("IdAssinatura: " + Sistema.buscaIdDisponivel("Cliente") + "\n");
                escritor.write("EstadoAssinatura: Free \n");
            } else if(tipoRegistrando.equals("Admin")){
                escritor.write("Tipo: Admin \n");
                escritor.write("Id: " + Sistema.buscaIdDisponivel("Admin" + "\n"));
            }
            System.out.println("Conteúdo adicionado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar conteúdo: " + e.getMessage());
        }

    }

    public static void editarUsuario(String cpf, String dadoEditado, String novoConteudo) {
        editarArquivo("src/Usuarios", cpf, dadoEditado, novoConteudo);
        if (dadoEditado.equals("email") || dadoEditado.equals("senha")){
            editarArquivo("src/UsuariosLogin", cpf, dadoEditado, novoConteudo);
        }
        switch (dadoEditado){
            case "Email":
                Sistema.usuario.setEmail(novoConteudo);
                break;
            case "Senha":
                Sistema.usuario.setSenha(novoConteudo);
                break;
            case "Nome":
                Sistema.usuario.setNome(novoConteudo);
                break;
        }
    }

    public static void trocaCartaoUsuario(String numeroCartao, String codigoCartao, String diaV, String mesV, String anoV){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate vencimentoCartao = LocalDate.parse(diaV + "/" + mesV + "/" + anoV, formatter);
        Cartao novoCartao = new Cartao(Long.parseLong(numeroCartao), Integer.parseInt(codigoCartao), vencimentoCartao);
        if(Sistema.usuario instanceof Cliente cliente){
            cliente.adicionarCartao(novoCartao);
        }
    }

    private static void editarArquivo(String nomeArquivo, String cpf, String dadoEditado, String novoConteudo) {
        try {
            // Lê todas as linhas do arquivo
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));
            boolean encontrouCPF = false;

            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);

                // Verifica se encontrou o CPF
                if (linha.contains("CPF: " + cpf)) {
                    encontrouCPF = true;
                }

                // Se o CPF foi encontrado, busca o dado a ser editado
                if (encontrouCPF && linha.contains(dadoEditado + ":")) {
                    // Faz o split na linha encontrada para pegar a chave e o valor
                    String[] partes = linha.split(":");
                    if (partes.length == 2) {
                        // Substitui o valor antigo pelo novo
                        String novaLinha = partes[0] + ": " + novoConteudo;
                        linhas.set(i, novaLinha);
                        System.out.println("Informação editada com sucesso!");
                    }
                    break; // Sai do loop após editar
                }
            }
            // Reescreve o arquivo com o conteúdo atualizado
            Files.write(Paths.get(nomeArquivo), linhas);
        } catch (IOException e) {
            System.out.println("Erro ao editar o arquivo: " + e.getMessage());
        }
    }

    public static String getNomeUsuario(){
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

    public static boolean fazPagamento(int cartao){
        if (Sistema.usuario instanceof Cliente cliente) {
            return cliente.realizarPagamento(cartao);
        }
        return false;
    }

    public static Cliente getCliente(){
        if(Sistema.usuario instanceof Cliente cliente){
            return cliente;
        }
        else return null;
    }

    public static Admin getAdmin(){
        if(Sistema.usuario instanceof Admin admin){
            return admin;
        }
        else return null;
    }

    public static void verificaAssinatura(){
        if(Sistema.usuario instanceof Cliente cliente){
           if(cliente.isPremium()) {
               boolean vencido = cliente.getVencimento().isAfter(LocalDate.now());
               if(vencido) cliente.removerAssinatura();
           }
        }
    }
}
