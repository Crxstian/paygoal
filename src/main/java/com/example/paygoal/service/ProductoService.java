package com.example.paygoal.service;

import com.example.paygoal.dto.ProductoDto;
import com.example.paygoal.entity.Producto;
import com.example.paygoal.exception.ProductoException;
import com.example.paygoal.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IService<Producto, ProductoDto> {

    @Autowired
    ProductoRepository productoRepository;

    @Override
    public ProductoDto crear(Producto producto) throws ProductoException {
        validarCamposProduto(producto);
        Producto productoAgregado = productoRepository.save(producto);
        return mapEntityToDto(productoAgregado);
    }

    @Override
    public ProductoDto modificar(Producto producto) throws ProductoException {
        if(productoRepository.findById(producto.getId()).orElse(null)!= null){
            validarCamposProduto(producto);
            Producto productoModificado = productoRepository.save(producto);
            return mapEntityToDto(productoModificado);
        }
        else {
            throw new ProductoException("El Producto a modificar no existe");
        }
    }

    @Override
    public List<ProductoDto> buscarPorElIdONombre(String idONombre) throws ProductoException {
        List<Producto> productoBuscado;

        if (esNumero(idONombre)) {
            Long id = Long.parseLong(idONombre);
            Optional<Producto> productoOptional = productoRepository.findById(id);

            if (productoOptional.isPresent()) {
                productoBuscado = Collections.singletonList(productoOptional.get());
            } else {
                productoBuscado = Collections.emptyList();
            }
        } else {
            productoBuscado = productoRepository.findByNombreContainingIgnoreCase(idONombre);
        }

        if (productoBuscado.isEmpty()) {
            throw new ProductoException("No se encontró ningún Producto con ese Id o Nombre");
        }

        return productoBuscado.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void eliminar(Integer id) throws ProductoException {
        Producto productoExiste = productoRepository.findById(id).orElse(null);
        if(productoExiste!=null){
            productoRepository.deleteById(id);
        }else{
            throw new ProductoException("El ID ingresado no corresponde a un Producto");
        }
    }

    @Override
    public List<ProductoDto> listarPorPrecio() throws ProductoException {
        List<Producto> productos = productoRepository.findAllByOrderByPrecioDesc();
        List<ProductoDto> productoDtos = new ArrayList<>();

        if (!productos.isEmpty()){
            for (Producto p:productos){
                productoDtos.add(mapEntityToDto(p));
            }
            return productoDtos;
        }else {
            throw new ProductoException("No existen Productos en la Base de datos");
        }
    }


    private ProductoDto mapEntityToDto(Producto producto){
        ProductoDto dto = new ProductoDto();

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setCantidad(producto.getCantidad());
        //LOGGER.info("Se mapeo correctamente de producto a dto");
        return dto;
    }
    private void validarCamposProduto(Producto producto) throws ProductoException {
        if (producto.getNombre() == null || producto.getNombre().isEmpty() ||
                !producto.getNombre().matches("[a-zA-Z\\s]+")) {
            throw new ProductoException("El nombre ingresado tiene caracteres incorrectos o es nulo/vacío");
        }
        if (producto.getDescripcion() == null || producto.getDescripcion().isEmpty() ||
                !producto.getDescripcion().matches("^[a-zA-Z0-9\\s]+$")) {
            throw new ProductoException("La descripcion ingresada tiene caracteres incorrectos o es nulo/vacío");
        }
        if (producto.getPrecio() < 0) {
            throw new ProductoException("El precio no puede ser un número negativo o nulo");
        }
        if (producto.getCantidad() == null || producto.getCantidad() < 0) {
            throw new ProductoException("La cantidad no puede ser un número negativo o nulo");
        }
    }
    private boolean esNumero(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
