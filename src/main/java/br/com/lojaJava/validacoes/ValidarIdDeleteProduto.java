package br.com.lojaJava.validacoes;

import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;

public class ValidarIdDeleteProduto implements IValidacaoProdutoDelete {
    @Override
    public void validar(Produto produto) throws ValidationException {
        if  (produto.getId() <= 0) {
            throw new ValidationException("ID inválido para exclusão!");
        }
    }
}
