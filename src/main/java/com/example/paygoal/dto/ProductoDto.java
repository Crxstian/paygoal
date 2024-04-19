package com.example.paygoal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Integer cantidad;
}
