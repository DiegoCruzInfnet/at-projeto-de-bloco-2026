package br.com.lojaJava.validacoes;

import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;

public class ValidacaoQuantidadeProduto implements IValidacaoProduto{
    @Override
    public void validar(Produto produto) throws ValidationException {
        if (produto.getQuantidade() < 0 ) {
            throw new ValidationException("A quantidade do produto não " +
                    "pode ser negativa.");
        }
    }
}
