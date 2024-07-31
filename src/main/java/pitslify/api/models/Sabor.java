//package com.br.foodfacil.models;
//
//import enums.pitslify.api.Disponibilidade;
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@EqualsAndHashCode(of = "id")
//@Document(collection = "sabores")
//public class Sabor {
//    @Id
//    String id;
//    String nome;
//    long createdAt;
//    Disponibilidade disponibilidade;
//
//    public Sabor(SaborRequestDto saborRequestDto){
//        this.nome = saborRequestDto.nome();
//        this.createdAt = saborRequestDto.createdAt();
//        this.disponibilidade = saborRequestDto.disponibilidade();
//    }
//}
