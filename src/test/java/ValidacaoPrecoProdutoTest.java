import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;
import br.com.lojaJava.validacoes.ValidacaoPrecoProduto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ValidacaoPrecoProdutoTest {
    private ValidacaoPrecoProduto validacaoPrecoProduto;
    private Produto produto;

    @BeforeEach
    void setUp(){
        validacaoPrecoProduto = new ValidacaoPrecoProduto();
        produto = new Produto();
    }

    @Test
    @DisplayName("Deve verificar Exception do produto nulo")
    public void deveVerificarExceptionNulo() {
        produto.setPreco(null);
        Assertions.assertThrows(ValidationException.class, () -> {
            validacaoPrecoProduto.validar(produto);
        });
    }
    @Test
    @DisplayName("Deve verificar Exception do produto nulo e validar a mensagem")
    public void deveVerificarExceptionNuloEValidarAMensagem() {
        produto.setPreco(null);

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            validacaoPrecoProduto.validar(produto);
        });

        String mensagemEsperada = "Preço do produto não pode ser nulo";
        String mensagemAtual = exception.getMessage();

        Assertions.assertEquals(mensagemEsperada, mensagemAtual);
    }

    @Test
    @DisplayName("Deve verificar se o pre exception " +
            "recebida se o preço for negativo")
    public void deveVerificarSeOPreExceptionNuloEValidarONegativo() {
        produto.setPreco(BigDecimal.valueOf(-1));
        Assertions.assertThrows(ValidationException.class, () -> {
            validacaoPrecoProduto.validar(produto);
        });

    }

    @Test
    @DisplayName("Deve verificar se o pre exception " +
            "recebida se o preço for zero")
    public void deveVerificarSeOPreExceptionZero() {
        produto.setPreco(BigDecimal.valueOf(0));
        Assertions.assertThrows(ValidationException.class, () -> {
            validacaoPrecoProduto.validar(produto);
        });

    }


}
