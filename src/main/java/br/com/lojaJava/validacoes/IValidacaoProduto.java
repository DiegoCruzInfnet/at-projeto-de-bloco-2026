package br.com.lojaJava.validacoes;

import br.com.lojaJava.model.Produto;
import br.com.lojaJava.exception.ValidationException;

@FunctionalInterface
public interface IValidacaoProduto {

    void validar(Produto produto) throws ValidationException;

}
