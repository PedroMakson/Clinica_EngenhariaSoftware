package com;

import java.sql.SQLException;
import java.util.Scanner;

import com.controllers.FuncionarioController;
import com.controllers.PacienteController;
import com.models.entity.Conexao;

public class App {

    public static void main(String[] args) throws SQLException {

        limparTela();
        Conexao.getInstancia();
        Scanner scanner = new Scanner(System.in);
        int opcao;
        String login, senha;

        do {
            do {
                do {
                    System.out.println("+-------------------------------+");
                    System.out.println("|  *  *   C L I N I C A   *  *  |");
                    System.out.println("+-------------------------------+");
                    System.out.println("| 1 -> Realizar login           |");
                    System.out.println("| 2 -> Cadastrar conta          |");
                    System.out.println("| 3 -> Pedir ajuda              |");
                    System.out.println("| 4 -> Sair do app              |");
                    System.out.println("+-------------------------------+");
                    System.out.printf("| Digite: ");
                    if (scanner.hasNextInt()) {
                        opcao = scanner.nextInt();
                        limparTela();
                        if (opcao < 1 || opcao > 4) {
                            System.out.println("\n Opção inválida, tente novamente!\n");
                        }
                    } else {
                        limparTela();
                        System.out.println("\n Opção inválida, tente novamente!\n");
                        scanner.next(); // Limpar o buffer de entrada
                        opcao = -1; // Define uma opção inválida para continuar o loop
                    }
                } while (opcao < 1 || opcao > 4);

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
                                int opcaoMenu;

                                do {
                                    do {
                                        System.out.println("+-------------------------------+");
                                        System.out.println("|    *  *   M  E  N  U   *  *   |");
                                        System.out.println("+-------------------------------+");
                                        System.out.println("| 1 -> Gerenciar Funcionários   |");
                                        System.out.println("| 2 -> Gerenciar Pacientes      |");
                                        System.out.println("| 3 -> Gerenciar Serviços       |");
                                        System.out.println("| 4 -> Gerenciar Consultas      |");
                                        System.out.println("| 5 -> Gerenciar Prescrições    |");
                                        System.out.println("| 6 -> Visualizar Prontuário    |");
                                        System.out.println("| 7 -> Relatório Financeiro     |");
                                        System.out.println("| 8 -> Deslogar                 |");
                                        System.out.println("+-------------------------------+");
                                        System.out.printf("| Digite: ");
                                        if (scanner.hasNextInt()) {
                                            opcaoMenu = scanner.nextInt();
                                            limparTela();
                                            if (opcaoMenu < 1 || opcaoMenu > 8) {
                                                System.out.println("\n > Opção inválida, tente novamente! <\n");
                                            }
                                        } else {
                                            limparTela();
                                            System.out.println("\n > Opção inválida, tente novamente! <\n");
                                            scanner.next(); // Limpar o buffer de entrada
                                            opcaoMenu = -1; // Define uma opção inválida para continuar o loop
                                        }

                                        if (opcaoMenu == 8) {
                                            break;
                                        }
                                    } while (opcaoMenu < 1 || opcaoMenu > 8);

                                    switch (opcaoMenu) {
                                        case 1: // MENU FUNCIONÁRIOS
                                            int opcaoFuncionario;
                                            do {
                                                System.out.println("+-------------------------------+");
                                                System.out.println("|           M  E  N  U          |");
                                                System.out.println("|    F U N C I O N Á R I O S    |");
                                                System.out.println("+-------------------------------+");
                                                System.out.println("| 1 -> Cadastrar Funcionário    |");
                                                System.out.println("| 2 -> Editar Funcionário       |");
                                                System.out.println("| 3 -> Visualizar Funcionário   |");
                                                System.out.println("| 4 -> Voltar menu              |");
                                                System.out.println("+-------------------------------+");
                                                System.out.printf("| Digite: ");
                                                if (scanner.hasNextInt()) {
                                                    opcaoFuncionario = scanner.nextInt();
                                                    limparTela();
                                                    if (opcaoFuncionario < 1 || opcaoFuncionario > 4) {
                                                        System.out.println(
                                                                "\n > Opção inválida, tente novamente! <\n");
                                                    }
                                                } else {
                                                    limparTela();
                                                    System.out.println("\n > Opção inválida, tente novamente! <\n");
                                                    scanner.next(); // Limpar o buffer de entrada
                                                    opcaoFuncionario = -1; // Define uma opção inválida para
                                                                           // continuar o loop
                                                }

                                                if (opcaoFuncionario == 4) {
                                                    opcaoMenu = 0;
                                                    break;
                                                }
                                            } while (opcaoFuncionario < 1 || opcaoFuncionario > 4);

                                            switch (opcaoFuncionario) {
                                                case 1:
                                                    FuncionarioController.cadastrarFuncionario();
                                                    opcaoMenu = 0;
                                                    break;
                                                case 2:
                                                    FuncionarioController.atualizarDadosDoFuncionario();
                                                    opcaoMenu = 0;
                                                    break;
                                                case 3:
                                                    FuncionarioController.visualizarDadosDoFuncionario();
                                                    opcaoMenu = 0;
                                                    break;
                                                case 4:
                                                    limparTela();
                                                    opcaoMenu = 0;
                                                    break;
                                                default:
                                                    break;
                                            }

                                            break;
                                        case 2:// MENU PACIENTES
                                            int opcaoPaciente;
                                            do {
                                                System.out.println("+-------------------------------+");
                                                System.out.println("|           M  E  N  U          |");
                                                System.out.println("|   P  A  C  I  E  N  T  E  S   |");
                                                System.out.println("+-------------------------------+");
                                                System.out.println("| 1 -> Cadastrar Paciente       |");
                                                System.out.println("| 2 -> Editar Paciente          |");
                                                System.out.println("| 3 -> Visualizar Paciente      |");
                                                System.out.println("| 4 -> Voltar menu              |");
                                                System.out.println("+-------------------------------+");
                                                System.out.printf("| Digite: ");

                                                if (scanner.hasNextInt()) {
                                                    opcaoPaciente = scanner.nextInt();
                                                    limparTela();
                                                    if (opcaoPaciente < 1 || opcaoPaciente > 4) {
                                                        System.out.println(
                                                                "\n > Opção inválida, tente novamente! <\n");
                                                    }
                                                } else {
                                                    limparTela();
                                                    System.out.println("\n > Opção inválida, tente novamente! <\n");
                                                    scanner.next(); // Limpar o buffer de entrada
                                                    opcaoPaciente = -1; // Define uma opção inválida para continuar
                                                                        // o loop
                                                }

                                                if (opcaoPaciente == 4) {
                                                    opcaoMenu = 0;
                                                    break;
                                                }

                                            } while (opcaoPaciente < 1 || opcaoPaciente > 4);

                                            switch (opcaoPaciente) {
                                                case 1:
                                                    PacienteController.cadastrarPaciente();
                                                    opcaoMenu = 0;
                                                    break;
                                                case 2:
                                                    PacienteController.atualizarDadosDoPaciente();
                                                    opcaoMenu = 0;
                                                    break;
                                                case 3:
                                                    PacienteController.visualizarDadosDoPaciente();
                                                    opcaoMenu = 0;
                                                    break;
                                                case 4:
                                                    limparTela();
                                                    opcaoMenu = 0;
                                                    break;
                                                default:
                                                    break;
                                            }
                                            break;
                                        case 3:
                                            break;
                                        case 4:
                                            break;
                                        case 5:
                                            break;
                                        case 6:
                                            break;
                                        case 7:
                                            break;
                                        default:
                                            break;
                                    }

                                } while (opcaoMenu < 1 || opcaoMenu > 8);
                            } else {
                                System.out.println("\n > Usuário bloqueado no sistema. <\n");
                                break;
                            }
                        } else {
                            if (FuncionarioController.verificarExistenciaFuncionarioCPF(login) == false) {
                                System.out.println("\n > Usuário inexistente no banco de dados. <\n");
                                break;
                            }
                            System.out.println("\n  > Login ou senha inválidos. <\n");
                        }
                        break;
                    default:
                        break;
                }

            } while (opcao < 1 || opcao > 4);
        } while (opcao != 4);

        System.out.println("\n+----------------------------------+");
        System.out.println("|  Você deslogou do DENTAL CLINIC! |");
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