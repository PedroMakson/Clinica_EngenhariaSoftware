package com.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import com.App;
import com.models.dao.ConsultaDAO;
import com.models.dao.FuncionarioDAO;
import com.models.dao.PacienteDAO;
import com.models.dao.ServicoDAO;
import com.models.entity.Conexao;
import com.models.entity.Consulta;
import com.models.entity.Funcionario;
import com.models.entity.Paciente;
import com.models.entity.Servico;

public class ConsultaController {

    private static Connection conexao = Conexao.getInstancia();
    private static ConsultaDAO consultaDAO = new ConsultaDAO(conexao);
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
    private static PacienteDAO pacienteDAO = new PacienteDAO(conexao);
    private static ServicoDAO servicoDAO = new ServicoDAO(conexao);
    static Scanner scanner = new Scanner(System.in);

    public static void cadastrarConsulta() throws SQLException {

        int codigoConsulta = 1;
        Funcionario funcionario;
        Paciente paciente;
        Servico servico;
        Date data = null;
        LocalTime horario;
        String statusConsulta = "Agendado";
        boolean pagamento = false;
        String cpfFuncionario;
        String cpfPaciente;
        int codigoServico;
        LocalDate parsedDate = null;

        System.out.println("+-------------------------------------------+");
        System.out.println("|      C  A  D  A  S  T  R  O    D  E       |");
        System.out.println("|           C  O  N  S  U  L  T  A          |");
        System.out.println("+-------------------------------------------+");

        // Loop para garantir que o CPF seja válido e exista no banco de dados
        do {
            System.out.print("-> CPF do profissional: ");
            cpfFuncionario = scanner.nextLine();

            // Verifica se o CPF contém apenas dígitos
            if (!cpfFuncionario.matches("\\d+")) {
                App.limparTela();
                System.out.println("\n > CPF deve conter apenas números. <\n");
                continue;
            }

            // Verifica se o CPF tem 11 dígitos
            if (cpfFuncionario.length() != 11) {
                App.limparTela();
                System.out.println("\n > CPF deve conter 11 números. <\n");
                continue;
            }

            // Verifica se um CPF existe no banco de dados
            if (!FuncionarioController.verificarExistenciaFuncionarioCPF(cpfFuncionario)) {
                App.limparTela();
                System.out.println("\n > Não existe um profissional com esse CPF, tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        funcionario = funcionarioDAO.retornarFuncionarioPorCPF(cpfFuncionario);

        // Loop para garantir que o CPF seja válido e exista no banco de dados
        while (true) {
            System.out.print("-> CPF do cliente: ");
            cpfPaciente = scanner.nextLine();

            // Verifica se o CPF contém apenas dígitos
            if (!cpfPaciente.matches("\\d+")) {
                App.limparTela();
                System.out.println("\n > CPF deve conter apenas números. <\n");
                continue;
            }

            // Verifica se o CPF tem 11 dígitos
            if (cpfPaciente.length() != 11) {
                App.limparTela();
                System.out.println("\n > CPF deve conter 11 números. <\n");
                continue;
            }

            // Verifica se um CPF existe no banco de dados
            if (!PacienteController.verificarExistenciaPacienteCPF(cpfPaciente)) {
                App.limparTela();
                System.out.println("\n > Não existe um paciente com esse CPF, tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        }

        paciente = pacienteDAO.retornarPacientePorCPF(cpfPaciente);

        // Loop para garantir que o código do serviço seja válido e exista no banco de
        // dados
        do {
            System.out.print("-> Código do serviço: ");
            String codigoServicoStr = scanner.nextLine();

            // Verifica se o código contém apenas dígitos
            if (!codigoServicoStr.matches("\\d+")) {
                App.limparTela();
                System.out.println("\n > O código do serviço deve conter apenas números. Tente novamente. <\n");
                continue;
            }

            codigoServico = Integer.parseInt(codigoServicoStr);

            try {
                // Verifica se o código do serviço já existe no banco de dados
                if (!ServicoController.verificarExistenciaServicoCodigo(codigoServico)) {
                    App.limparTela();
                    System.out.println("\n > Não existe um serviço com esse código, tente novamente. <\n");
                    continue;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                App.limparTela();
                System.out.println("\n > Ocorreu um erro ao verificar o código do serviço. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        servico = servicoDAO.retornarServicoPorCodigo(codigoServico);

        // Validação da data da consulta
        while (data == null) {
            System.out.print("-> Data da consulta (AAAA-MM-DD): ");
            String dataString = scanner.nextLine();

            try {
                // Verifica se a data não é nula ou vazia
                if (Objects.isNull(dataString) || dataString.trim().isEmpty()) {
                    App.limparTela();
                    System.out.println("\n > A data da consulta não pode ser nula. Tente novamente. <\n");
                    continue;
                }

                // Converte a string para um objeto LocalDate
                parsedDate = LocalDate.parse(dataString);

                // Obtém a data de hoje
                LocalDate dataAtual = LocalDate.now();

                // Verifica se a data da consulta é válida (posterior ou igual à data atual)
                if (parsedDate.isEqual(dataAtual) || parsedDate.isAfter(dataAtual)) {
                    // Data de consulta válida
                    data = java.sql.Date.valueOf(parsedDate);
                    break;
                } else {
                    App.limparTela();
                    System.out.println("\n > A data da consulta deve ser igual ou posterior à data de hoje. <\n");
                    continue;
                }
            } catch (DateTimeParseException e) {
                App.limparTela();
                System.out.println("\n > Formato de data inválido. Utilize o formato AAAA-MM-DD. <\n");
                continue;
            }
        }

        // Validação do horário da consulta e verificação de existência de consulta para
        // a data e hora especificadas
        do {
            System.out.print("-> Horário da consulta (HH:MM): ");
            String horarioString = scanner.nextLine();

            try {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime parsedTime = LocalTime.parse(horarioString, timeFormatter);

                // Verifica se o horário está no intervalo de funcionamento
                LocalTime horarioInicio = LocalTime.of(8, 0); // 08:00
                LocalTime horarioFim = LocalTime.of(18, 0); // 18:00
                if (parsedTime.compareTo(horarioInicio) >= 0 && parsedTime.compareTo(horarioFim) <= 0) {
                    // Verifica se já existe uma consulta agendada para o horário especificado
                    if (consultaDAO.existeConsultaParaDataHora(parsedDate, parsedTime)) {
                        App.limparTela();
                        System.out.println(
                                "\n > Já existe uma consulta agendada para o dia " + parsedDate + " às " + parsedTime
                                        + "h. Escolha outro horário. <\n");
                        continue;
                    }

                    horario = parsedTime;
                    break;
                } else {
                    App.limparTela();
                    System.out.println("\n > O horário da consulta deve estar entre 08:00 e 18:00. <\n");
                    continue;
                }
            } catch (DateTimeParseException e) {
                App.limparTela();
                System.out.println("\n > Formato de horário inválido. Utilize o formato HH:MM. <\n");
                continue;
            }
        } while (true);

        Consulta consulta = new Consulta(codigoConsulta, funcionario, paciente, servico, data, horario, statusConsulta,
                pagamento);

        // Insere o funcionário no banco de dados
        if (consultaDAO.inserirConsulta(funcionario, paciente, servico, consulta)) {
            App.limparTela();
            System.out.println("\n > Consulta agendada COM SUCESSO! <\n");
        } else {
            System.out.println("\n > Consulta agendada SEM SUCESSO! <\n");
        }

    }

    public static void visualizarDadosConsulta() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataInicio = null;
        LocalDate dataFim = null;
        int contadorInicio = 1;
        int contadorFim = 1;

        while (contadorInicio <= 3) {
            System.out.println("\nTentativa " + contadorInicio + "/3 para a data de início.");
            System.out.println("+--------------------------------------------+");
            System.out.println("|   V I S U A L I Z A R   C O N S U L T A S  |");
            System.out.println("+--------------------------------------------+");

            // Solicitar a data de início
            System.out.print("| Data de início (AAAA-MM-DD): ");
            String inputInicio = scanner.nextLine();

            try {
                dataInicio = LocalDate.parse(inputInicio, formatter);
                break; // Se a data for válida, sair do loop
            } catch (DateTimeParseException e) {
                App.limparTela();
                System.out.println("\n> Formato de data inválido. Insira no formato correto. <");
                contadorInicio++;
                continue;
            }
        }

        App.limparTela();

        // Verificar se o limite de tentativas para a data de início foi atingido
        if (contadorInicio == 4) {
            App.limparTela();
            System.out.println(" > Limite de tentativas excedido para a data de início. Encerrando o processo. <\n");
            return;
        }

        while (contadorFim <= 3) {
            System.out.println("\nTentativa " + contadorFim + "/3 para a data de fim.");
            System.out.println("+--------------------------------------------+");
            System.out.println("|   V I S U A L I Z A R   C O N S U L T A S  |");
            System.out.println("+--------------------------------------------+");

            // Solicitar a data de fim
            System.out.print("| Data de fim (AAAA-MM-DD): ");
            String inputFim = scanner.nextLine();

            try {
                dataFim = LocalDate.parse(inputFim, formatter);
                break; // Se a data for válida, sair do loop
            } catch (DateTimeParseException e) {
                App.limparTela();
                System.out.println("\n> Formato de data inválido. Insira no formato correto. <");
                contadorFim++;
                continue;
            }
        }

        // Verificar se o limite de tentativas para a data de fim foi atingido
        if (contadorFim == 4) {
            App.limparTela();
            System.out.println(" > Limite de tentativas excedido para a data de início. Encerrando o processo. <\n");
            return;
        }

        // Verificar se as datas são válidas
        if (dataInicio.isAfter(dataFim)) {
            App.limparTela();
            System.out.println("\n> A data de início não pode ser posterior à data de fim. Encerrando o processo. <\n");
            return;
        }

        // Chamar o método consultarConsultasPorPeriodo com as datas válidas
        try {
            consultaDAO.consultarConsultasPorPeriodo(dataInicio, dataFim);
        } catch (SQLException e) {
            System.out.println("\n> Ocorreu um erro ao consultar as consultas por período. <\n");
            e.printStackTrace();
        }
    }

    static Date stringParaData(String dataString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = sdf.parse(dataString);

            // Verificar se a data é maior ou igual à data atual
            Calendar calData = Calendar.getInstance();
            calData.setTime(data);
            Calendar calAtual = Calendar.getInstance();

            if (!calData.before(calAtual)) {
                return data; // Data válida
            } else {
                App.limparTela();
                System.out.println("\n > A data deve ser maior ou igual à data atual. Tente novamente. <\n");
                return null; // Data inválida
            }
        } catch (ParseException e) {
            App.limparTela();
            System.out.println(
                    "\n > Erro na conversão de data. Certifique-se de usar o formato AAAA-MM-DD. Tente novamente. <\n");
            return null; // Data inválida
        }
    }
}