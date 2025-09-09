package com.taller01.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "arbitros")
public class Arbitro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @NotNull(message = "La cédula no puede ser nula")
    @NotEmpty(message = "La cédula no puede estar vacía")
    @Column(nullable = false, length = 20, unique = true)
    private String cedula;
    
    @NotNull(message = "El teléfono no puede ser nulo")
    @NotEmpty(message = "El teléfono no puede estar vacío")
    @Column(nullable = false, length = 20)
    private String phone;
    
    // Pa guardar fotos como blob

    @Lob
    @Column(name = "photo_data", columnDefinition = "LONGBLOB")
    private byte[] photoData;
        
    @Column(name = "photo_content_type", length = 100)
    private String photoContentType;
    
    @Column(name = "photo_filename", length = 255)
    private String photoFilename;

    // ...
    @NotNull(message = "El teléfono no puede ser nulo")
    @NotEmpty(message = "El teléfono no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String speciality;
    
    @Column(name = "unavailability_dates", columnDefinition = "TEXT")
    private String unavailabilityDates;
    
    @NotNull(message = "La escala no puede ser nula")
    @NotEmpty(message = "La escala no puede estar vacía")
    @Column(nullable = false, length = 50)
    private String scale;
    
    // Constructores
    public Arbitro() {}
    
    public Arbitro(String nombre, String cedula, String phone, byte[] photoData, String photoContentType, 
                   String photoFilename, String speciality, String unavailabilityDates, String scale) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.phone = phone;
        this.photoData = photoData;
        this.photoContentType = photoContentType;
        this.photoFilename = photoFilename;
        this.speciality = speciality;
        this.unavailabilityDates = unavailabilityDates;
        this.scale = scale;
    }

    // MÉTODO HELPER: Verificar si tiene imagen
    public boolean hasPhoto() {
        return photoData != null && photoData.length > 0;
    }
    
    // MÉTODO HELPER: Obtener URL de la imagen
    public String getPhotoUrl() {
        if (hasPhoto()) {
            return "/api/arbitros/" + id + "/photo";
        }
        return "https://placehold.co/150x150";
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getSpeciality() {
        return speciality;
    }
    
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    
    public String getUnavailabilityDates() {
        return unavailabilityDates;
    }
    
    public void setUnavailabilityDates(String unavailabilityDates) {
        this.unavailabilityDates = unavailabilityDates;
    }
    
    public String getScale() {
        return scale;
    }
    
    public void setScale(String scale) {
        this.scale = scale;
    }

    public byte[] getPhotoData() {
        return photoData;
    }
    
    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }
    
    public String getPhotoContentType() {
        return photoContentType;
    }
    
    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }
    
    public String getPhotoFilename() {
        return photoFilename;
    }
    
    public void setPhotoFilename(String photoFilename) {
        this.photoFilename = photoFilename;
    }
    
    @Override
    public String toString() {
        return "Arbitro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", phone='" + phone + '\'' +
                ", speciality='" + speciality + '\'' +
                ", scale='" + scale + '\'' +
                '}';
    }
}