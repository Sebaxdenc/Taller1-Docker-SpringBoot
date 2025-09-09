package com.taller01.demo.service;

import com.taller01.demo.model.Arbitro;
import com.taller01.demo.repository.ArbitroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ArbitroService {

    private final ArbitroRepository arbitroRepository;

    // Constructor para inyección de dependencias
    public ArbitroService(ArbitroRepository arbitroRepository) {
        this.arbitroRepository = arbitroRepository;
    }

    /**
     * Obtener todos los árbitros
     */
    public List<Arbitro> findAll() {
        return arbitroRepository.findAll();
    }

    /**
     * Obtener árbitro por ID
     */
    public Optional<Arbitro> findById(Long id) {
        return arbitroRepository.findById(id);
    }

    /**
     * Verificar si existe un árbitro con la cédula dada
     */
    public boolean existsByCedula(String cedula) {
        return arbitroRepository.existsByCedula(cedula);
    }

    /**
     * Verificar si existe un árbitro con el teléfono dado
     */
    public boolean existsByPhone(String phone) {
        return arbitroRepository.existsByPhone(phone);
    }

    // ========== OPERACIONES DE ESCRITURA ==========

    /**
     * Crear árbitro con archivo de foto (guarda BLOB en BD)
     */
    @Transactional
    public Arbitro createArbitroWithPhoto(Arbitro arbitro, MultipartFile photoFile) throws IOException {
        // Validaciones de duplicados
        if (arbitro.getCedula() != null && arbitroRepository.existsByCedula(arbitro.getCedula())) {
            throw new RuntimeException("Esta cédula ya está registrada: " + arbitro.getCedula());
        }
        
        if (arbitro.getPhone() != null && arbitroRepository.existsByPhone(arbitro.getPhone())) {
            throw new RuntimeException("Este teléfono ya está registrado: " + arbitro.getPhone());
        }

        // Procesar imagen si se proporciona
        if (photoFile != null && !photoFile.isEmpty()) {
            try {
                // Validar tipo de archivo
                String contentType = photoFile.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new RuntimeException("El archivo debe ser una imagen válida (JPG, PNG, GIF)");
                }

                // Validar tamaño (máximo 5MB)
                if (photoFile.getSize() > 5 * 1024 * 1024) {
                    throw new RuntimeException("La imagen no puede ser mayor a 5MB");
                }

                // Guardar datos de la imagen
                arbitro.setPhotoData(photoFile.getBytes());
                arbitro.setPhotoContentType(contentType);
                arbitro.setPhotoFilename(photoFile.getOriginalFilename());

                System.out.println("Imagen procesada: " + photoFile.getOriginalFilename() + 
                                 " (" + photoFile.getSize() + " bytes)");

            } catch (IOException e) {
                System.err.println("Error al procesar imagen: " + e.getMessage());
                throw new IOException("Error al procesar la imagen", e);
            }
        } else {
            System.out.println("No se proporcionó imagen para el árbitro");
        }

        return arbitroRepository.save(arbitro);
    }

    /**
     * Eliminar árbitro por ID
     */
    @Transactional
    public boolean deleteById(Long id) {
        try {
            Optional<Arbitro> arbitroOptional = arbitroRepository.findById(id);
            
            if (arbitroOptional.isPresent()) {
                Arbitro arbitro = arbitroOptional.get();
                
                // Eliminar de la base de datos (la imagen BLOB se elimina automáticamente)
                arbitroRepository.deleteById(id);
                System.out.println("Árbitro eliminado completamente: " + arbitro.getNombre());
                
                return true;
            } else {
                System.out.println("Árbitro no encontrado con ID: " + id);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar árbitro con ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Error al eliminar árbitro", e);
        }
    }
}