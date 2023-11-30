package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaRegistrarUnTurnoYRetornarElId() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("123434234", "JUAN", "SOUZA");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456789, LocalDate.of(2000, 12, 24), new DomicilioEntradaDto("calle", 1234, "Localidad", "Provincia"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.now().plusDays(1), odontologoSalidaDto.getId(), pacienteSalidaDto.getId());

        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertNotNull(turnoSalidaDto.getId());
        assertNotNull(turnoSalidaDto.getFechaYHoraTurno());
        assertEquals(odontologoSalidaDto.getId(), turnoSalidaDto.getOdontologo_id());
        assertEquals(pacienteSalidaDto.getId(), turnoSalidaDto.getPaciente_id());
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaDeTurnosNoVacia() {
        List<TurnoSalidaDto> turnosDto = turnoService.listarTurnos();

        assertFalse(turnosDto.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaEliminarUnTurnoConId1() {
        turnoService.eliminarTurno(1L);
        assertNull(turnoService.buscarPorId(1L));
    }
}
