package com.tecsup.tecsupbook.repository;

import com.tecsup.tecsupbook.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Reto adicional: consultas derivadas (JPA genera el SQL automaticamente)
    List<Libro> findByTitulo(String titulo);

    List<Libro> findByAnioPublicacionGreaterThan(Integer anio);
}
