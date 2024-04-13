package com.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.App;
import com.models.entity.Consulta;
import com.models.entity.Funcionario;
import com.models.entity.Paciente;
import com.models.entity.Servico;

public class ConsultaDAO {

    private static Connection conexao;

    public ConsultaDAO(Connection conexao) {
        ConsultaDAO.conexao = conexao;
    }

    public boolean inserirConsulta(Funcionario funcionario, Paciente paciente, Servico servico, Consulta consulta) {
        String sql = "INSERT INTO Consulta (funcionario_cpf, funcionario_nome, paciente_cpf, paciente_nome, " +
                "codigoServico, nomeServico, valorServico, dataConsulta, horario, statusConsulta, statusPagamento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, paciente.getCpf());
            stmt.setString(4, paciente.getNome());
            stmt.setInt(5, servico.getCodigo());
            stmt.setString(6, servico.getNome());
            stmt.setDouble(7, servico.getValor());
            stmt.setDate(8, new java.sql.Date(consulta.getData().getTime()));
            stmt.setTime(9, java.sql.Time.valueOf(consulta.getHorario()));
            stmt.setString(10, consulta.getStatusConsulta());
            stmt.setBoolean(11, consulta.isPagamento());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeConsultaParaDataHora(LocalDate data, LocalTime horario) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM Consulta WHERE dataConsulta = ? AND horario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(data));
            stmt.setTime(2, java.sql.Time.valueOf(horario));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // Retorna true se existir pelo menos uma consulta para o horário especificado
                }
            }
        }
        return false; // Retorna false se não houver consulta para o horário especificado
    }

    public void consultarConsultasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        String sql = "SELECT * FROM Consulta WHERE dataConsulta BETWEEN ? AND ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(dataInicio));
            stmt.setDate(2, java.sql.Date.valueOf(dataFim));

            try (ResultSet rs = stmt.executeQuery()) {
                App.limparTela();
                while (rs.next()) {
                    int consultaId = rs.getInt("consulta_id");
                    String funcionarioNome = rs.getString("funcionario_nome");
                    String pacienteCpf = rs.getString("paciente_cpf");
                    String pacienteNome = rs.getString("paciente_nome");
                    String nomeServico = rs.getString("nomeServico");

                    // Exibição da tabela para cada consulta
                    System.out.println(
                            "\n+-------------+---------------+-----------------+---------------+-----------------+");
                    System.out
                            .println(
                                    "| CONSULTA ID | FUNCIONÁRIO   | PACIENTE CPF    | PACIENTE NOME | NOME DO SERVIÇO |");
                    System.out
                            .println(
                                    "+-------------+---------------+-----------------+---------------+-----------------+");
                    System.out.printf("| %-11d | %-14s| %-13s   | %-14s| %-14s  |\n", consultaId, funcionarioNome,
                            pacienteCpf, pacienteNome, nomeServico);
                    System.out
                            .println(
                                    "+-------------+---------------+-----------------+---------------+-----------------+");

                    // Exibição das informações sobre o serviço
                    System.out
                            .println(
                                    "| VALOR DO    | DATA          | HORÁRIO         | STATUS        | STATUS DO       |");
                    System.out
                            .println(
                                    "| SERVIÇO (R$)| CONSULTA      |                 | CONSULTA      | PAGAMENTO       |");
                    System.out
                            .println(
                                    "+-------------+---------------+-----------------+---------------+-----------------+");
                    double valorServico = rs.getDouble("valorServico");
                    LocalDate dataConsulta = rs.getDate("dataConsulta").toLocalDate();
                    LocalTime horario = rs.getTime("horario").toLocalTime();
                    String statusConsulta = rs.getString("statusConsulta");
                    boolean statusPagamento = rs.getBoolean("statusPagamento");

                    System.out.printf("| R$%-10.2f| %-13s | %-15s | %-14s| %-15s |\n", valorServico, dataConsulta,
                            horario,
                            statusConsulta, (statusPagamento ? "Pago" : "Não pago"));
                    System.out
                            .println(
                                    "+-------------+---------------+-----------------+---------------+-----------------+\n");
                }
            }
        }
    }

    public void atualizarAtributosConsulta() {

    }
}