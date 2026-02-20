package br.com.lojaJava.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class Produto {

    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidade;

    public Produto(String nome, String descricao, BigDecimal preco, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

}