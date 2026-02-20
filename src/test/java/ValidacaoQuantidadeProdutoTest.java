import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;
import br.com.lojaJava.validacoes.ValidacaoQuantidadeProduto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidacaoQuantidadeProdutoTest {
    private ValidacaoQuantidadeProduto validacaoQuantidadeProduto;
    private Produto produto;

    @BeforeEach
    public void setUp(){
        validacaoQuantidadeProduto = new ValidacaoQuantidadeProduto();
        produto = new Produto();
    }
    @Test
    @DisplayName("Deve verificar Exception se quantidade é negativa")
    public void deveVerificarQuantidadeProdutoNegativa(){
        produto.setQuantidade(-1);
        Assertions.assertThrows(ValidationException.class, () -> {
            validacaoQuantidadeProduto.validar(produto);
        });
    }
}
