package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologoYRetornarElId() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("123434234", "JUAN", "SOUZA");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("JUAN", odontologoSalidaDto.getNombre());
    }

    @Test
    @Order(2)
    void deberiaEliminarElOdontologoConId1_deberiaLanzarUnaResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> odontologoService.eliminarOdontologo(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaDeOdontologosNoVacia() {
        // Asegúrate de que haya al menos un odontólogo registrado para que la lista no esté vacía
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("123434234", "JUAN", "SOUZA");
        odontologoService.registrarOdontologo(odontologoEntradaDto);

        List<OdontologoSalidaDto> odontologosDto = odontologoService.listarOdontologos();

        assertFalse(odontologosDto.isEmpty());
    }
}
