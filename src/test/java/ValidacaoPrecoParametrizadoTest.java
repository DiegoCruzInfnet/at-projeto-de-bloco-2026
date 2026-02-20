import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;
import br.com.lojaJava.validacoes.ValidacaoPrecoProduto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidacaoPrecoParametrizadoTest {

    private ValidacaoPrecoProduto validador = new ValidacaoPrecoProduto();

    @ParameterizedTest
    @ValueSource(strings = {"-1.00", "-0.01", "-100.50"})
    @DisplayName("Deve lançar exceção para múltiplos valores de preços negativos")
    void deveValidarVariosPrecosNegativos(String preco) {
        Produto p = new Produto();
        p.setPreco(new java.math.BigDecimal(preco));

        Assertions.assertThrows(ValidationException.class, () -> {
            validador.validar(p);
        });
    }
}
