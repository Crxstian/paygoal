package com.example.paygoal.service;

import com.example.paygoal.dto.ProductoDto;
import com.example.paygoal.exception.ProductoException;

import java.util.List;

public interface IService <Clase, ClaseDto>{
    ClaseDto crear (Clase clase) throws ProductoException;

    ClaseDto modificar (Clase clase) throws ProductoException;

    List<ProductoDto> buscarPorElIdONombre(String idONombre) throws ProductoException;

    void eliminar (Integer id) throws ProductoException;
    List<ClaseDto> listarPorPrecio() throws ProductoException;


}
