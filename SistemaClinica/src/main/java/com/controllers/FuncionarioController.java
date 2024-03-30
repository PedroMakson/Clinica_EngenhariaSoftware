package com.controllers;

import java.sql.Connection;
import java.sql.SQLException;

import com.models.dao.FuncionarioDAO;
import com.models.entity.Conexao;

public class FuncionarioController {

    private static Connection conexao = Conexao.getInstancia();
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);

    public static boolean logar(String cpf, String senha) throws SQLException {
        if (funcionarioDAO.validarFuncionario(cpf, senha)) {
            return true;
        }
        return false;
    }

    public static boolean verificarStatus(String cpf) throws SQLException {
        if (funcionarioDAO.validarStatusFuncionario(cpf)) {
            return true;
        }
        return false;
    }

    public static boolean verificarExistenciaCPF(String cpf) throws SQLException {
        if (funcionarioDAO.validarCPF(cpf)) {
            return true;
        }
        return false;
    }
}