package com.tecsup.tecsupbook.service;

import com.tecsup.tecsupbook.entity.Autor;
import com.tecsup.tecsupbook.exception.ReglaNegocioException;
import com.tecsup.tecsupbook.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor registrar(Autor autor) {
        // Regla 1: nombre obligatorio
        if (autor.getNombre() == null || autor.getNombre().isBlank()) {
            throw new ReglaNegocioException("El nombre del autor es obligatorio");
        }
        // Regla 2: nacionalidad obligatoria
        if (autor.getNacionalidad() == null || autor.getNacionalidad().isBlank()) {
            throw new ReglaNegocioException("La nacionalidad del autor es obligatoria");
        }
        return autorRepository.save(autor);
    }

    public List<Autor> listar() {
        return autorRepository.findAll();
    }
}
