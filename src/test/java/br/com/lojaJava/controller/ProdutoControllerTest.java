package br.com.lojaJava.controller;

import br.com.lojaJava.dao.ProdutoDAO;
import br.com.lojaJava.exception.ValidationException;
import br.com.lojaJava.model.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoDAO produtoDAO;

    @Test
    @DisplayName("Deve carregar a página de listagem de produtos")
    void deveListarProdutos() throws Exception {
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(view().name("lista-produtos"))
                .andExpect(model().attributeExists("produtos"));
    }

    @Test
    @DisplayName("Deve redirecionar após salvar um produto com sucesso")
    void deveSalvarProduto() throws Exception {
        mockMvc.perform(post("/produtos/salvar")
                        .param("nome", "Caneca Geek")
                        .param("preco", "50.00")
                        .param("quantidade", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produtos"));
    }

    @Test
    @DisplayName("Deve salvar novo produto com sucesso")
    void deveSalvarNovoProduto() throws Exception {
        // SAVE NÃO MOKADO
        mockMvc.perform(post("/produtos/salvar")
                        .param("nome", "Produto Teste")
                        .param("descricao", "Descrição Teste")
                        .param("preco", "99.90")
                        .param("quantidade", "15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produtos"));

        verify(produtoDAO, times(1)).save(any(Produto.class));
        verify(produtoDAO, never()).update(any(Produto.class));
    }

    @Test
    @DisplayName("Deve atualizar produto existente com sucesso")
    void deveAtualizarProduto() throws Exception {
        mockMvc.perform(post("/produtos/salvar")
                        .param("id", "1")
                        .param("nome", "Produto Atualizado")
                        .param("descricao", "Descrição Atualizada")
                        .param("preco", "150.00")
                        .param("quantidade", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produtos"));

        verify(produtoDAO, times(1)).update(any(Produto.class));
        verify(produtoDAO, never()).save(any(Produto.class));
    }

    @Test
    @DisplayName("Deve lidar com erro de validação ao salvar novo produto")
    void deveLidarComErroValidacaoAoSalvarNovo() throws Exception {
        doThrow(new ValidationException("Erro de validação"))
                .when(produtoDAO).save(any(Produto.class));

        mockMvc.perform(post("/produtos/salvar")
                        .param("nome", "")  // Nome inválido
                        .param("preco", "10.00")
                        .param("quantidade", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produtos/novo"))
                .andExpect(flash().attributeExists("mensagemErro"))
                .andExpect(flash().attributeExists("produto"));
    }

    @Test
    @DisplayName("Deve lidar com erro de validação ao atualizar produto")
    void deveLidarComErroValidacaoAoAtualizar() throws Exception {
        doThrow(new ValidationException("Erro de validação"))
                .when(produtoDAO).update(any(Produto.class));

        mockMvc.perform(post("/produtos/salvar")
                        .param("id", "1")
                        .param("nome", "")  // Nome inválido
                        .param("preco", "10.00")
                        .param("quantidade", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produtos/editar/1"))
                .andExpect(flash().attributeExists("mensagemErro"))
                .andExpect(flash().attributeExists("produto"));
    }

    @Test
    @DisplayName("Deve lidar com erro inesperado")
    void deveLidarComErroInesperado() throws Exception {
        doThrow(new RuntimeException("Erro inesperado"))
                .when(produtoDAO).save(any(Produto.class));

        mockMvc.perform(post("/produtos/salvar")
                        .param("nome", "Produto")
                        .param("preco", "10.00")
                        .param("quantidade", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produtos"))
                .andExpect(flash().attributeExists("mensagemErro"));
    }

    @Test
    @DisplayName("Deve mostrar formulário de edição")
    void deveMostrarFormularioEditar() throws Exception {
        // Arrange
        Produto produto = new Produto("Produto", "Desc", new BigDecimal("10.00"), 5);
        produto.setId(1);
        when(produtoDAO.getProduto(1)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(get("/produtos/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-produto"))
                .andExpect(model().attributeExists("produto"))
                .andExpect(model().attribute("produto", produto));
    }

    @Test
    @DisplayName("Deve deletar produto com sucesso")
    void deveDeletarProduto() throws Exception {
        mockMvc.perform(get("/produtos/deletar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produtos"));

        verify(produtoDAO, times(1)).delete(1);
    }
}

