import br.com.lojaJava.dao.ProdutoDAO;
import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoDAOTest {

    private ProdutoDAO produtoDAO;
    private Produto produtoTeste;

    @BeforeEach
    public void setUp() {

        produtoDAO = new ProdutoDAO();

        produtoTeste = new Produto(
                "Funko Pop Homem-Aranha",
                "Boneco colecionável do filme No Way Home",
                new BigDecimal("120.50"),
                10
        );


        produtoDAO.deleteAllProdutos();
    }

    @Test
    @DisplayName("Deve inserir um produto com sucesso no banco de dados")
    public void deveInserirProduto() throws SQLException {

        produtoDAO.save(produtoTeste);


        List<Produto> produtosNoBanco = produtoDAO.getProdutos();
        assertFalse(produtosNoBanco.isEmpty(), "A lista de produtos não deveria estar vazia após a inserção.");
        assertEquals(1, produtosNoBanco.size(), "Deveria haver exatamente 1 produto no banco.");

        Produto produtoInserido = produtosNoBanco.get(0);
        assertEquals("Funko Pop Homem-Aranha", produtoInserido.getNome());
        assertEquals(new BigDecimal("120.50"), produtoInserido.getPreco());
    }

    @Test
    @DisplayName("Deve testar exception salvar produtos")
    public void deveTestarExceptionSalvarProduto() throws SQLException {
        produtoTeste = null;
        Assertions.assertThrows(ValidationException.class, () -> {
            produtoDAO.save(produtoTeste);
        });
    }

    @Test
    @DisplayName("Deve deletar um produto com sucesso")
    public void deveDeletarProduto() throws SQLException {

        produtoDAO.save(produtoTeste);

        Produto produtoSalvo = produtoDAO.getProdutos().get(0);
        int idParaDeletar = produtoSalvo.getId();
        assertNotEquals(0, idParaDeletar);

        produtoDAO.delete(idParaDeletar);

        List<Produto> produtosDepoisDoDelete = produtoDAO.getProdutos();
        assertTrue(produtosDepoisDoDelete.isEmpty(), "A lista de produtos deveria estar vazia após o delete.");
    }

    @Test
    @DisplayName("Testar exception delete produtos")
    public void deveTestarExceptionDeleteProduto() throws SQLException {
        produtoTeste.setId(-1);
        Assertions.assertThrows(ValidationException.class, () -> {
            produtoDAO.delete(produtoTeste.getId());
        });
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    public void deveListarProdutos() throws SQLException {

        Produto produto2 = new Produto("Caneca Star Wars", "Caneca de cerâmica", new BigDecimal("45.00"), 20);
        produtoDAO.save(produtoTeste); // Insere o primeiro produto
        produtoDAO.save(produto2); // Insere o segundo produto

        List<Produto> produtosListados = produtoDAO.getProdutos();

        assertNotNull(produtosListados);
        assertEquals(2, produtosListados.size(), "A lista deveria conter 2 produtos.");
    }

    @Test
    @DisplayName("Deve atualizar um produto com sucesso")
    public void deveAtualizarProduto() throws SQLException {

        produtoDAO.save(produtoTeste);

        Produto produtoSalvo = produtoDAO.getProdutos().get(0);

        produtoSalvo.setPreco(new BigDecimal("99.90"));
        produtoSalvo.setQuantidade(5);

        produtoDAO.update(produtoSalvo);

        Produto produtoAtualizado = produtoDAO.getProdutos().get(0);

        assertEquals(new BigDecimal("99.90"), produtoAtualizado.getPreco(), "O preço deveria ter sido atualizado.");
        assertEquals(5, produtoAtualizado.getQuantidade(), "A quantidade deveria ter sido atualizada.");
    }

    @Test
    @DisplayName("Deve testar Exception update")
    public void deveTestarExceptionUpdate() throws SQLException {
        produtoTeste = null;
        Assertions.assertThrows(ValidationException.class, () -> {
            produtoDAO.update(produtoTeste);
        });
    }

    @Test
    @DisplayName("Deve Buscar produto por id")
    public void deveBuscarProdutoPorId() throws SQLException {

        produtoDAO.save(produtoTeste);


        Produto produtoEsperado = produtoDAO.getProdutos().get(0);
        int idReal = produtoEsperado.getId();


        Produto produtoDoBanco = produtoDAO.getProduto(idReal);


        assertNotNull(produtoDoBanco, "O produto retornado não deveria ser nulo.");
        assertEquals(produtoEsperado.getId(), produtoDoBanco.getId());
        assertEquals(produtoEsperado.getNome(), produtoDoBanco.getNome());
        assertEquals(produtoEsperado.getDescricao(), produtoDoBanco.getDescricao());
        assertEquals(produtoEsperado.getQuantidade(), produtoDoBanco.getQuantidade());

        assertEquals(0, produtoEsperado.getPreco().compareTo(produtoDoBanco.getPreco()),
                "O preço do produto deveria ser matematicamente igual.");
    }

    @Test
    @DisplayName("Deve esvaziar a tabela completamente")
    public void deveEsvaziarTabela() throws SQLException {

        produtoDAO.save(new Produto("Teste", "Desc", BigDecimal.ONE, 1));
        assertFalse(produtoDAO.getProdutos().isEmpty(), "A tabela não deveria estar vazia antes do truncate");

        produtoDAO.truncateTable();

        List<Produto> produtosAposTruncate = produtoDAO.getProdutos();
        assertTrue(produtosAposTruncate.isEmpty(), "A lista de produtos deveria estar vazia após o truncate.");
    }
}