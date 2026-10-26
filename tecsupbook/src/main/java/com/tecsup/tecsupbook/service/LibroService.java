package com.tecsup.tecsupbook.service;

import com.tecsup.tecsupbook.entity.Autor;
import com.tecsup.tecsupbook.entity.Libro;
import com.tecsup.tecsupbook.exception.RecursoNoEncontradoException;
import com.tecsup.tecsupbook.exception.ReglaNegocioException;
import com.tecsup.tecsupbook.repository.AutorRepository;
import com.tecsup.tecsupbook.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public Libro registrar(Libro libro) {
        // Regla 1: titulo obligatorio
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            throw new ReglaNegocioException("El titulo es obligatorio");
        }
        // Regla 2: editorial no vacia
        if (libro.getEditorial() == null || libro.getEditorial().isBlank()) {
            throw new ReglaNegocioException("La editorial no puede estar vacia");
        }
        // Regla 3: anio de publicacion obligatorio y no mayor al actual
        if (libro.getAnioPublicacion() == null
                || libro.getAnioPublicacion() > Year.now().getValue()) {
            throw new ReglaNegocioException("El anio de publicacion no puede ser mayor al anio actual");
        }
        // Regla 4: el libro debe pertenecer a un autor existente
        if (libro.getAutor() == null || libro.getAutor().getId() == null) {
            throw new ReglaNegocioException("El libro debe tener un autor (envia autor.id)");
        }
        Autor autor = autorRepository.findById(libro.getAutor().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El autor no existe"));
        libro.setAutor(autor);

        return libroRepository.save(libro);
    }

    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id " + id));
    }

    public void eliminar(Long id) {
        Libro libro = buscarPorId(id);
        libroRepository.delete(libro);
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }

    public List<Libro> buscarPosterioresA(Integer anio) {
        return libroRepository.findByAnioPublicacionGreaterThan(anio);
    }
}
