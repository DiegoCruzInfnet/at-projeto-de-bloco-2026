import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;
import br.com.lojaJava.validacoes.ValidacaoProdutoNulo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidacaoProdutoNuloTest {
    private ValidacaoProdutoNulo validacaoProdutoNulo;
    private Produto produto;
    @BeforeEach
    public void setUp() {
        validacaoProdutoNulo = new ValidacaoProdutoNulo();
        produto = new Produto();
    }
    @Test
    @DisplayName("deve Verificar exceprion produto Nulo")
    public void verificarProdutoNulo() {
        produto = null;
        Assertions.assertThrows(ValidationException.class, () -> {
            validacaoProdutoNulo.validar(produto);
        });
    }
}
