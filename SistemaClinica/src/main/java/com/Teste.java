package com;

import java.sql.SQLException;

import com.controllers.FuncionarioController;

public class Teste {

    public static void main(String[] args) throws SQLException {

    // Connection conexao = Conexao.getInstancia();
    // FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);

    //     // Criar uma instância de Date para data de nascimento
    //     Date dataNascimento = Date.valueOf("1990-01-01"); // Exemplo de data de nascimento

    //     // Criar uma instância de Date para data de contratação (utilizando a data
    //     // atual)
    //     Date dataContratacao = new Date(System.currentTimeMillis()); // Data atual

    //     // Instanciar um novo funcionário
    //     Funcionario funcionario = new Funcionario("12345678988", "João Silva", dataNascimento, "Masculino", "Gerente",
    //             5000.00, dataContratacao, true, "123456789", "joao1@example.com", "senha123", "12345678", "Rua A", 123,
    //             "Centro", "São Paulo", "SP");

    //     funcionarioDAO.inserirFuncionario(funcionario);

    FuncionarioController.cadastrarFuncionario();

    }

}
