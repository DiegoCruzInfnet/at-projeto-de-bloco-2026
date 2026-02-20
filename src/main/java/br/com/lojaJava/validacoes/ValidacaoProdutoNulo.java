package br.com.lojaJava.validacoes;

import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;

public class ValidacaoProdutoNulo implements IValidacaoProduto {

    @Override
    public void validar(Produto produto) throws ValidationException {
        if (produto == null) {
            throw new ValidationException("O produto não pode ser nulo.");
        }
    }
}