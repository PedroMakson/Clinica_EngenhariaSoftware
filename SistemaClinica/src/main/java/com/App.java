package com;

import java.sql.SQLException;
import java.util.Scanner;

import com.controllers.FuncionarioController;
import com.models.entity.Conexao;

public class App {

    public static void main(String[] args) throws SQLException {

        limparTela();
        Conexao.getInstancia();
        Scanner scanner = new Scanner(System.in);
        int opcao;
        String login, senha;

        do {
            System.out.println("+-------------------------------+");
            System.out.println("|  *  *   C L I N I C A   *  *  |");
            do {
                System.out.println("+-------------------------------+");
                System.out.println("| 1 -> Realizar login           |");
                System.out.println("| 2 -> Cadastrar conta          |");
                System.out.println("| 3 -> Pedir ajuda              |");
                System.out.println("| 4 -> Sair do app              |");
                System.out.println("+-------------------------------+");
                System.out.printf("| Digite: ");
                opcao = scanner.nextInt();

                limparTela();
                if (opcao < 1 || opcao > 4) {
                    System.out.println("\nOpção inválida, tente novamente!\n");
                }

                switch (opcao) {
                    case 1:
                        limparTela();
                        System.out.println("+-------------------------------+");
                        System.out.println("|          L O G I N            |");
                        System.out.println("+-------------------------------+");
                        System.out.printf("| -> Login (CPF): ");
                        login = scanner.next();
                        System.out.printf("| -> Senha: ");
                        senha = scanner.next();
                        System.out.println("+-------------------------------+");

                        limparTela();
                        if (FuncionarioController.logar(login, senha)) {
                            if (FuncionarioController.verificarStatus(login)) {
                                System.out.println("\n > Usuário logado com sucesso. <\n");
                            } else {
                                System.out.println("\n > Usuário bloqueado no sistema. <\n");
                                break;
                            }
                        } else {
                            if(FuncionarioController.verificarExistenciaCPF(login) == false) {
                                System.out.println("\n > Usuário inexistente no banco de dados. <\n");
                                break;
                            }
                            System.out.println("\n > Login ou senha inválidos. <\n");
                        }
                        break;
                    default:
                        break;
                }

            } while (opcao < 1 || opcao > 4);
        } while (opcao != 4);

        System.out.println("\n+----------------------------------+");
        System.out.println("| Você deslogou do DENTAL CLINIC!  |");
        System.out.println("+----------------------------------+\n");

        scanner.close();
    }

    public static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // No Windows, use o comando "cls" para limpar a tela.
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Caso contrário (Linux, macOS), use a sequência ANSI para limpar a tela.
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}