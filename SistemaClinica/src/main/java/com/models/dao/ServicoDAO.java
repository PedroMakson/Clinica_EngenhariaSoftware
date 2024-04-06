package com.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.App;
import com.models.entity.Servico;

public class ServicoDAO {

    private static Connection conexao;

    public ServicoDAO(Connection conexao) {
        ServicoDAO.conexao = conexao;
    }

    public boolean verificarNomeServico(String nomeServico) throws SQLException {
        String sql = "SELECT nome FROM Servicos WHERE nome = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeServico);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String nome = resultSet.getString("nome");

                    if (nome.equals(nomeServico)) {
                        return true;
                    }
                }
            }
        }
        return false; // Retorna false se não encontrar uma correspondência de CPF
    }

    public boolean inserirServico(Servico servico) {
        String sql = "INSERT INTO Servicos (nome, valor, status) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, servico.getNome()); // Obter nome do serviço do objeto Servico
            stmt.setDouble(2, servico.getValor()); // Obter valor do serviço do objeto Servico
            stmt.setBoolean(3, servico.getStatus()); // Obter status do serviço do objeto Servico

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void exibirServicosCadastrados() throws SQLException {
        String sql = "SELECT codigoServico, nome, valor, status FROM Servicos";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                App.limparTela();
                System.out.println("+----------------------------------------------+");
                System.out.println("|    S E R V I Ç O S  C A D A S T R A D O S    |");
                System.out.println("+----------------------------------------------+");

                while (resultSet.next()) {
                    int codigoServico = resultSet.getInt("codigoServico");
                    String nome = resultSet.getString("nome");
                    double valor = resultSet.getDouble("valor");
                    boolean status = resultSet.getBoolean("status");

                    // Imprime cada serviço em uma nova linha
                    System.out.printf("| Código    | %d\n", codigoServico);
                    System.out.printf("| Nome      | %s\n", nome);
                    System.out.printf("| Valor     | R$%.2f\n", valor);
                    System.out.printf("| Status    | %s\n", (status ? "Ativo" : "Inativo"));
                    System.out.println("+-----------+----------------------------------+");
                }
                System.out.println();
            }
        }
    }
}