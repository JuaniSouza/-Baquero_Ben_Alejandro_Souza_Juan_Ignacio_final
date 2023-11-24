package com.backend.clinicaodontologica.dto.modificacion;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoModificacionEntradaDto {
    @NotNull(message = "Debe proveerse el id del turno que desea modificar")
    private Long id;
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @JsonProperty("fecha_turno")
    @NotNull(message = "Debe especificarse la fecha del turno")
    private LocalDateTime fechaYHora;
    @NotNull(message = "El turno tiene que tener un paciente registrado")
    private Long paciente_id;
    @NotNull(message = "El turno tiene que tener un odontólogo registrado")
    private Long odontologo_id;

    public TurnoModificacionEntradaDto() {
    }

    public TurnoModificacionEntradaDto(Long id, LocalDateTime fechaYHora, Long paciente_id, Long odontologo_id) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.paciente_id = paciente_id;
        this.odontologo_id = odontologo_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Long getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(Long paciente_id) {
        this.paciente_id = paciente_id;
    }

    public Long getOdontologo_id() {
        return odontologo_id;
    }

    public void setOdontologo_id(Long odontologo_id) {
        this.odontologo_id = odontologo_id;
    }
}
