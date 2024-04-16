package com;

import java.sql.Connection;
import java.sql.SQLException;

import com.controllers.ConsultaController;
import com.models.dao.PrescricaoMedicaDAO;
import com.models.entity.Conexao;

public class Teste {

    public static void main(String[] args) throws SQLException {

        Connection conexao = Conexao.getInstancia();
        // ConsultaDAO consultaDAO = new ConsultaDAO(conexao);
        PrescricaoMedicaDAO prescricaoMedicaDAO = new PrescricaoMedicaDAO(conexao);
        prescricaoMedicaDAO.obterProntuarioMedicoCPF("12345678900");

        // consultaDAO.atualizarAtributosConsulta(1, 1, "2");

        // consultaDAO.consultarConsultasPorPeriodo(LocalDate.of(2024, 4, 1),
        // LocalDate.of(2024, 4, 13));

        // ConsultaController.atualizarDadosDaConsulta();

    }

}
