package com.models.entity;

import java.sql.Date;

public class Consulta {

    private Paciente paciente;
    private Servico servico;
    private Date data;
    private String horario;
    private String status;
    
    public Consulta(Paciente paciente, Servico servico, Date data, String horario, String status) {
        this.paciente = paciente;
        this.servico = servico;
        this.data = data;
        this.horario = horario;
        this.status = status;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}