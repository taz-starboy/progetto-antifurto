package com.antifurto.antifurto_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorEventRequest {

    @NotBlank
    private String sensorType;

    @NotBlank
    private String location;

    @NotNull
    private LocalDateTime timeStamp;
}
/*
* @NotNull
    * Viene da javax.validation.constraints.NotNull
    * Verifica che il campo non sia null
    * Accetta stringhe vuote, numeri a zero, liste vuote, ecc.
    *
* @NotBlank
    * Viene da javax.validation.constraints.NotBlank
    * Verifica che il campo non sia null né vuoto né solo spazi
    * Usata solo con String
* */
