package br.com.lojaJava.validacoes;

import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;

import java.math.BigDecimal;

public class ValidacaoPrecoProduto implements IValidacaoProduto {
    @Override
    public void validar(Produto produto) throws ValidationException {
        if  (produto.getPreco() == null) {
            throw new ValidationException("Preço do produto não pode " +
                    "ser nulo");
        }
        if (produto.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("O preço do produto não pode " +
                    "ser negativo.");
        }
        if (produto.getPreco().compareTo(BigDecimal.ZERO) == 0) {
            throw new ValidationException("O preço deve ser maior " +
                    "do que zero.");
        }
    }
}
