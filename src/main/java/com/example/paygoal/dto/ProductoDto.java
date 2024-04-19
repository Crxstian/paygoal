package com.example.paygoal.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductoDto implements Serializable {
    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Integer cantidad;
}
