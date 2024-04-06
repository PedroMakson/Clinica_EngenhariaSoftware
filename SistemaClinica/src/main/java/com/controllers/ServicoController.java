package com.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import com.App;
import com.models.dao.ServicoDAO;
import com.models.entity.Conexao;
import com.models.entity.Servico;

public class ServicoController {

    private static Connection conexao = Conexao.getInstancia();
    private static ServicoDAO servicoDAO = new ServicoDAO(conexao);
    static Scanner scanner = new Scanner(System.in);

    public static void cadastrarServico() throws SQLException {
        // Variáveis para armazenar os dados do serviço
        String nome = null;
        double valor = 0;
        boolean status = true;

        System.out.println("+-------------------------------------------+");
        System.out.println("|      C  A  D  A  S  T  R  O    D  E       |");
        System.out.println("|             S  E  R  V  I  Ç  O           |");
        System.out.println("+-------------------------------------------+");

        // Validação para o nome do serviço
        do {
            System.out.print("-> Nome do serviço: ");
            nome = scanner.nextLine();

            // Verifica se o nome não é nulo ou vazio
            if (Objects.isNull(nome) || nome.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > O nome do serviço não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            // Verifica se o nome não excede o tamanho máximo permitido
            if (nome.length() > 40) {
                App.limparTela();
                System.out.println("\n > O nome do serviço deve conter no máximo 40 caracteres. Tente novamente. <\n");
                continue;
            }

            // Verifica se já existe um serviço com o mesmo nome
            if (servicoDAO.verificarNomeServico(nome)) {
                App.limparTela();
                System.out.println("\n > Já existe um serviço cadastrado com esse nome. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para o valor do serviço
        do {
            System.out.print("-> Valor do serviço: ");
            String valorStr = scanner.nextLine();

            // Tenta converter a entrada do usuário para um valor double
            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                App.limparTela();
                System.out.println("\n > Valor do serviço inválido. Por favor, insira um valor numérico válido. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Verifica se o valor é um valor positivo
            if (valor <= 0) {
                App.limparTela();
                System.out.println("\n > Valor do serviço deve ser um valor positivo. Tente novamente. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Cria um objeto Servico com os dados validados
        Servico servico = new Servico(nome, valor, status);

        // Insere o paciente no banco de dados
        if (servicoDAO.inserirServico(servico)) {
            App.limparTela();
            System.out.println("\n > Serviço cadastrado COM SUCESSO! <\n");
        } else {
            App.limparTela();
            System.out.println("\n > Serviço cadastrado SEM SUCESSO! <\n");
        }

    }

    public static void exibirServicos() throws SQLException {
        servicoDAO.exibirServicosCadastrados();
    }
}