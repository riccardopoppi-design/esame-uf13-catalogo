package com.riccardopoppi.catalogo_cap.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CappelloRequestDTO {

    @NotBlank(message = "Il codice è obbligatorio")
    private String codice;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 100, message = "Il nome non può superare i 100 caratteri")
    private String nome;

    @NotBlank(message = "La marca è obbligatoria")
    private String marca;

    @NotBlank(message = "La taglia è obbligatoria")
    private String taglia;

    @Min(value = 1900, message = "L'anno non può essere antecedente al 1900")
    private int anno;

    @Positive(message = "Il prezzo deve essere maggiore di zero")
    private double prezzo;
    
    private String immagine;
}