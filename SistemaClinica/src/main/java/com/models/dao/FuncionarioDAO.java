package com.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO {

    private static Connection conexao;

    public FuncionarioDAO(Connection conexao) {
        FuncionarioDAO.conexao = conexao;
    }

    public boolean validarFuncionario(String cpfFuncionario, String senhaFuncionario) throws SQLException {
        String sql = "SELECT cpf, senha FROM Funcionario WHERE cpf = ? AND senha = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfFuncionario);
            stmt.setString(2, senhaFuncionario);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");
                    String senha = resultSet.getString("senha");

                    if (cpf.equals(cpfFuncionario) && senha.equals(senhaFuncionario)) {
                        return true;
                    } 
                }
            }
        }
        return false; // Retorna false se não encontrar uma correspondência de CPF e senha
    }

    public boolean validarStatusFuncionario(String cpfFuncionario) throws SQLException {
        String sql = "SELECT cpf, status FROM Funcionario WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfFuncionario);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");
                    Boolean statusConta = resultSet.getBoolean("status");

                    if (cpf.equals(cpfFuncionario) && statusConta.equals(false)) {
                        return false;
                    } else if (cpf.equals(cpfFuncionario) && statusConta.equals(true)) {
                        return true;
                    }
                }
            }
        }

        return false; // Retorna false se não encontrar uma correspondência de CPF
    }

    public boolean validarCPF(String cpfFuncionario) throws SQLException {
        String sql = "SELECT cpf FROM Funcionario WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfFuncionario);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");

                    if (cpf.equals(cpfFuncionario)) {
                        return true;
                    }
                }
            }
        }

        return false; // Retorna false se não encontrar uma correspondência de CPF
    }


}