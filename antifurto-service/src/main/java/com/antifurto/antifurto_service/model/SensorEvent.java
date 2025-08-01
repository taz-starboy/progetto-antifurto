package com.antifurto.antifurto_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sensorType;
    private String location;
    private LocalDateTime timeStamp;
    private String destinationType;
    private String destination;
}
/*
    @Entity
        Fa parte di JPA (Java Persistence API)
        Indica che la classe rappresenta una tabella del database
        Ogni oggetto di questa classe corrisponderà a una riga (record) nel database
        Spring Boot + Hibernate leggono questa annotazione per creare la struttura SQL corrispondent

    @Data
        Fornita da Lombok
        Crea automaticamente tutti i metodi più noiosi: getters, setters, toString(), equals() e hashCode()
        Elimina tanto codice boilerplate

    @NoArgsConstructor
        Fornita da Lombok
        Crea automaticamente un costruttore senza argomenti: public MyClass() {}
        Necessario per alcune librerie/framework (es. JPA) che hanno bisogno di costruttori vuoti per istanziare oggetti automaticamente

    @AllArgsConstructor
        Fornita da Lombok
        Crea un costruttore con tutti gli attributi come parametri

    @Id
        Indica che il campo è la chiave primaria della tabella nel database.
        Ogni tabella deve avere un identificatore unico per ogni riga (record), e questa annotazione lo specifica.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
        Dice a JPA/Hibernate di generare automaticamente il valore dell’id quando viene salvato un nuovo oggetto.
        La strategy specifica come il valore verrà generato.
        Le strategie disponibili:
            AUTO	    JPA sceglie automaticamente il metodo migliore in base al database
            IDENTITY	Usa l’auto-increment del database (es. AUTO_INCREMENT in MySQL)
            SEQUENCE	Usa una sequenza definita nel database (tipico in Oracle/PostgreSQL)
            TABLE	    Usa una tabella speciale per tenere traccia degli ID (poco usata oggi)
*/
