//package com.br.foodfacil.models;
//
//import enums.pitslify.api.Categoria;
//import enums.pitslify.api.Disponibilidade;
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@EqualsAndHashCode(of = "id")
//@Document(collection = "salgados")
//public class Salgado {
//    @Id
//    private String id;
//    private String nome;
//    private Categoria categoria;
//    private String descricao;
//    private float preco;
//    private String imagem;
//    private String imagemRetangular;
//    private String imagemQuadrada;
//    private boolean emOferta;
//    private float precoEmOferta;
//    private Disponibilidade disponibilidade;
//    private long createdAt;
//    private String observacao;
//    private List<String> sabores;
//
//    public Salgado(SalgadoRequestDto salgadoDto){
//        this.nome = salgadoDto.nome();
//        this.categoria = salgadoDto.categoria();
//        this.descricao = salgadoDto.descricao();
//        this.preco = salgadoDto.preco();
//        this.imagem = salgadoDto.imagem();
//        this.imagemRetangular = salgadoDto.imagemRetangular();
//        this.imagemQuadrada = salgadoDto.imagemQuadrada();
//        this.emOferta = salgadoDto.emOferta();
//        this.precoEmOferta = salgadoDto.precoEmOferta();
//        this.disponibilidade = salgadoDto.disponibilidade();
//        this.createdAt = salgadoDto.createdAt();
////        this.sabores = salgadoDto.sabores();
//    }
//}
