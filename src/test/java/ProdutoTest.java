import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProdutoTest {
    @Test
    public void deveVerificarObj() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Funko Homem-Aranha");
        produto.setDescricao("Funko Homem-Homem Aranha 2074");
        produto.setPreco(new BigDecimal("110.99"));
        produto.setQuantidade(10);

        assertNotNull(produto); // Verifica se o objeto não é nulo
        assertEquals(1, produto.getId());
        assertEquals("Funko Homem-Aranha", produto.getNome());
        assertEquals("Funko Homem-Homem Aranha 2074", produto.getDescricao());
        assertEquals(new BigDecimal("110.99"), produto.getPreco());
        assertEquals(10, produto.getQuantidade());
    }
    @Test
    @DisplayName("Deve verificar se o método toString está no formato esperado (LOMBOK)")
    public void deveVerificarFormatoToString() {

        Produto produto = new Produto();
        produto.setId(10);
        produto.setNome("Caneca Geek");
        produto.setDescricao("Caneca Star Wars");
        produto.setPreco(new BigDecimal("45.00"));
        produto.setQuantidade(20);

        String esperado = "Produto(id=10, nome=Caneca Geek, descricao=Caneca Star Wars, preco=45.00, quantidade=20)";

        assertEquals(esperado, produto.toString());
    }

}
