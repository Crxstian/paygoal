package com.example.paygoal.controllers;
import com.example.paygoal.dto.ProductoDto;
import com.example.paygoal.entity.Producto;
import com.example.paygoal.exception.ProductoException;
import com.example.paygoal.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<Set<ProductoDto>> listarPorPrecio(){
        ResponseEntity response;
        try {
            List<ProductoDto> dto = productoService.listarPorPrecio();
            response = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (ProductoException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<ProductoDto> agregar(@RequestBody Producto producto){
        ResponseEntity response;
        try {
            ProductoDto dto =productoService.crear(producto);
            response = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (ProductoException e) {
            //LOGGER.error("Error por validaciones");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            //LOGGER.error(e.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        //LOGGER.info(response);
        return response;
    }
    @PutMapping
    public ResponseEntity<ProductoDto> modificar(@RequestBody Producto producto){
        ResponseEntity response;
        try {
            ProductoDto dto =productoService.modificar(producto);
            response = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (ProductoException e) {
            //LOGGER.error("El usuario a modificar No existe");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            //LOGGER.error(e.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        //LOGGER.info(response);
        return response;

    }

    @GetMapping("/{idONombre}")
    public ResponseEntity<Set<ProductoDto>> buscarPorIdONombre(@PathVariable String idONombre){
        ResponseEntity response ;
        try {
            List<ProductoDto> dto = productoService.buscarPorElIdONombre(idONombre);
            response = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (ProductoException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        ResponseEntity<String> response;
        try {
            productoService.eliminar(id);
            response = ResponseEntity.ok().body("Producto con ID " + id + " eliminado correctamente");
        } catch (ProductoException e) {
            //LOGGER.error("El Producto a eliminar no existe");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            //LOGGER.error(e.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        //LOGGER.info(response);
        return response;
    }

}
