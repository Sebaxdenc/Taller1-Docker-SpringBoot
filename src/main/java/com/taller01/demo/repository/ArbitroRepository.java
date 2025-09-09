package com.taller01.demo.repository;

import com.taller01.demo.model.Arbitro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {
    
    
    // Búsqueda por cédula (para validar duplicados)
    Optional<Arbitro> findByCedula(String cedula);
    
    // Verificación de existencia (para validaciones)
    boolean existsByCedula(String cedula);
    boolean existsByPhone(String phone);
    
    // Ordenar por nombre (usado en findAllOrderByNombre)
    List<Arbitro> findAllByOrderByNombreAsc();
    
    // Búsqueda por escala
    List<Arbitro> findByScale(String scale);
    List<Arbitro> findByScaleOrderByNombreAsc(String scale);
    
    // Búsqueda por nombre (case-insensitive)
    List<Arbitro> findByNombreContainingIgnoreCase(String nombre);
    
    // Búsqueda combinada
    List<Arbitro> findByScaleAndNombreContainingIgnoreCase(String scale, String nombre);
    
}