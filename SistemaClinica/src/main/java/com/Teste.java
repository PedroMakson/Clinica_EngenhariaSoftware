package com;

import java.sql.Connection;
import java.sql.SQLException;

import com.controllers.ConsultaController;
import com.controllers.FuncionarioController;
import com.models.dao.ConsultaDAO;
import com.models.entity.Conexao;
import java.time.LocalDate;

public class Teste {

    public static void main(String[] args) throws SQLException {

        // Connection conexao = Conexao.getInstancia();
        // ConsultaDAO consultaDAO = new ConsultaDAO(conexao);

        // consultaDAO.consultarConsultasPorPeriodo(LocalDate.of(2024, 4, 1),
        // LocalDate.of(2024, 4, 13));

        ConsultaController.visualizarDadosConsulta();

    }

}
