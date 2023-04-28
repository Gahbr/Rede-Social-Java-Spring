package com.sysmap.parrot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

//fazer os gets/sets/tostring automaticamente
@Data
//fazer um construtor com todos os atributos
@AllArgsConstructor
public class Address {
    private String country;
    private String city;
    private String postCode;
}
