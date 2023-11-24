package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.IOdontologoService;
import com.backend.clinicaodontologica.service.IPacienteService;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private final static Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private TurnoRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;
    private ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto registrarTurno(TurnoRequestDto turnoRequestDto) {
        PacienteResponseDto pacienteFindId = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Paciente pacienteEntidad = pacienteService.entidadPaciente(pacienteFindId.getId());
        LOGGER.info("Paciente del turno: {}", JsonPrinter.toString(pacienteEntidad));
        OdontologoResponseDto odontologoFindId = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
        Odontologo odontologoEntidad = odontologoService.entidadOdontologo(odontologoFindId.getId());
        LOGGER.info("Odontologo del turno: {}", JsonPrinter.toString(odontologoEntidad));
        Turno turnoEntidad = new Turno();
        turnoEntidad.setPaciente(pacienteEntidad);
        turnoEntidad.setOdontologo(odontologoEntidad);
        turnoEntidad.setFechaYHoraTurno(turnoRequestDto.getFechaYHoraTurno());
        LOGGER.info("Entidad: " + JsonPrinter.toString(turnoEntidad));

        Turno turnoAPersistir = turnoRepository.save(turnoEntidad);
        LOGGER.info("Turno a persistir: " + JsonPrinter.toString(turnoAPersistir));

        TurnoResponseDto turnoSalidaDto = new TurnoResponseDto(turnoAPersistir.getId(), turnoAPersistir.getFechaYHoraTurno(), odontologoEntidad.getId(), pacienteFindId.getId());
        LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
        return turnoSalidaDto;
    }

    @Override
    public List<TurnoResponseDto> listarTurnos() {
        List<TurnoResponseDto> turnosResponseDto = new ArrayList<>();
        List<Turno> turnoList = turnoRepository.findAll();
        for (Turno turno : turnoList) {
            TurnoResponseDto turnoResponseDto = new TurnoResponseDto(turno.getId(), turno.getFechaYHoraTurno(), turno.getOdontologo().getId(), turno.getPaciente().getId());
            turnosResponseDto.add(turnoResponseDto);

        }
        LOGGER.info("Se listaron los siguientes turnos: " + JsonPrinter.toString(turnosResponseDto));

        return turnosResponseDto;
    }

    @Override
    public TurnoResponseDto buscarPorId(Long id) {
        Turno turno = turnoRepository.findById(id).orElse(null);
        TurnoResponseDto turnoResponseDto = null;
        if (turno != null) {
            turnoResponseDto = new TurnoResponseDto(id, turno.getFechaYHoraTurno(), turno.getOdontologo().getId(), turno.getPaciente().getId());
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoResponseDto));
        }
        return turnoResponseDto;
    }

    @Override
    public TurnoResponseDto actualizarTurno(TurnoRequestUpdateDto turno) {
        TurnoResponseDto turnoResponseDto = null;
        Paciente paciente = pacienteService.entidadPaciente(turno.getPaciente_id());
        Odontologo odontologo = odontologoService.entidadOdontologo(turno.getOdontologo_id());
        Turno turnoUpdate = new Turno(turno.getId(), turno.getFechaYHoraTurno(), paciente, odontologo);
        Turno turnoModified = turnoRepository.findById(turno.getId()).orElse(null);
        if (turnoModified != null) {
            turnoModified = turnoUpdate;
            turnoRepository.save(turnoModified);
            turnoResponseDto = new TurnoResponseDto(turnoModified.getId(), turnoModified.getFechaYHoraTurno(), turnoModified.getOdontologo().getId(), turnoModified.getPaciente().getId());
            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoModified));
        } else {
            LOGGER.error("No fue posible actualizar el turno porque el mismo no se encuentra regitrado en la base de datos");
        }
        return turnoResponseDto;
    }

    @Override
    public void eliminarTurno(Long id) {
        if (turnoRepository.findById(id).orElse(null) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se eliminó el turno con el id " + id);
        } else {
            LOGGER.error("No se ha encontrado el turno con el id: " + id);
        }
    }
}
