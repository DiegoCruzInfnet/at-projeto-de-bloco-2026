import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;
import br.com.lojaJava.validacoes.ValidacaoNomeProduto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidacaoNomeProdutoTest {

    private ValidacaoNomeProduto validaNome;
    private Produto produto;

    @BeforeEach
    public void setUp() {
        validaNome = new ValidacaoNomeProduto();
        produto =  new Produto();
    }


    @Test
    @DisplayName("Deve verificar validação produto com nome invalido")
    public void deveVerificarProdutoComNomeInvalido(){
        produto.setNome(null);

        Assertions.assertThrows(ValidationException.class, () -> {
            validaNome.validar(produto);
        });
    }

    @Test
    @DisplayName("Deve verificar validação produto com nome em branco")
    public void deveVerificarProdutoComNomeEmBranco() {
        produto.setNome("");

        Assertions.assertThrows(ValidationException.class, () -> {
            validaNome.validar(produto);
        });
    }
    @Test
    @DisplayName("Deve lançar exceção quando o produto for nulo")
    public void deveLancarExcecaoQuandoProdutoForNulo() {
        Assertions.assertThrows(ValidationException.class, () -> {
            validaNome.validar(null);
        });
    }


}
