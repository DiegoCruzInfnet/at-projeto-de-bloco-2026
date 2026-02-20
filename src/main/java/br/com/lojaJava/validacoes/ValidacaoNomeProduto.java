package br.com.lojaJava.validacoes;

import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;

public class ValidacaoNomeProduto implements IValidacaoProduto {
    @Override
    public void validar(Produto produto) throws ValidationException {
        if (produto == null || produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new ValidationException("O nome do produto é obrigatório e não pode estar vazio.");
        }
    }
}
