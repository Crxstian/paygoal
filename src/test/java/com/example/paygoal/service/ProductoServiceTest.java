package com.example.paygoal.service;

import com.example.paygoal.entity.Producto;
import com.example.paygoal.exception.ProductoException;
import com.example.paygoal.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEliminarExistente() {
        // Arrange
        Integer id = 1;
        Producto producto = new Producto();
        producto.setId(id);

        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        // Act & Assert
        assertDoesNotThrow(() -> productoService.eliminar(id));
        verify(productoRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarNoExistente() {
        // Arrange
        Integer id = 1;

        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ProductoException exception = assertThrows(ProductoException.class, () -> productoService.eliminar(id));
        assertEquals("El ID ingresado no corresponde a un Producto", exception.getMessage());
    }
}