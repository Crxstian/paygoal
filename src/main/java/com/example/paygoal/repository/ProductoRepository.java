package com.example.paygoal.repository;
import com.example.paygoal.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findById(Long id);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    //List<Producto> findAllByOrderByPrecioAsc();
    List<Producto> findAllByOrderByPrecioDesc();

}
