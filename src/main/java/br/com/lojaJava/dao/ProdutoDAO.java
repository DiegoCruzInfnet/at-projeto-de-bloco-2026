package br.com.lojaJava.dao;

import br.com.lojaJava.exception.PersistenceException;
import br.com.lojaJava.validacoes.*;
import br.com.lojaJava.connection.ConnectionFactory;
import br.com.lojaJava.model.Produto;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoDAO {
    private final List<IValidacaoProduto> regrasDeValidacao;
    private final List<IValidacaoProduto> regrasDelete;

    public ProdutoDAO() {
        this.regrasDeValidacao = List.of(
                new ValidacaoProdutoNulo(),
                new ValidacaoNomeProduto(),
                new ValidacaoPrecoProduto(),
                new ValidacaoQuantidadeProduto()
        );

        this.regrasDelete = List.of(
                new ValidarIdDeleteProduto()
        );
    }

    private void executarValidacoes(Produto produto) {
        for (IValidacaoProduto regra : regrasDeValidacao) {
            regra.validar(produto);
        }
    }
    private void RegrasDeValidacoesIdDeleteProduto(Produto produto) {
        for (IValidacaoProduto regra : regrasDelete) {
            regra.validar(produto);
        }
    }

    // CREATE
    public void save(Produto produto) {
        executarValidacoes(produto);
        String sql = "INSERT INTO produtos (nome, descricao, preco, quantidade) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setBigDecimal(3, produto.getPreco());
            pstmt.setInt(4, produto.getQuantidade());
            pstmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1)); // Atualiza o ID no objeto
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException("Erro ao salvar produto", e);
        }
    }

    // READ - Lista todos
    public List<Produto> getProdutos() {
        String sql = "SELECT * FROM produtos";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getBigDecimal("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao obter produtos", e);
        }
        return produtos;
    }

    // READ - Busca por ID
    public Produto getProduto(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        Produto produto = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPreco(rs.getBigDecimal("preco"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao obter produto por ID", e);
        }

        return produto;
    }

    // UPDATE
    public void update(Produto produto) {
        executarValidacoes(produto);
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, quantidade = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setBigDecimal(3, produto.getPreco());
            pstmt.setInt(4, produto.getQuantidade());
            pstmt.setInt(5, produto.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Erro ao atualizar produto", e);
        }
    }

    // DELETE
    public void delete(int idProduto) {
        Produto produto = new Produto();
        produto.setId(idProduto);
        RegrasDeValidacoesIdDeleteProduto(produto);
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idProduto);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Erro ao deletar produto", e);
        }
    }

    // Método para TESTES (reseta o ID)
    public void truncateTable() {
        String sql = "TRUNCATE TABLE produtos";
        try (Connection cnn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = cnn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao truncar a tabela de produtos", e);
        }
    }

    // Método para LIMPEZA GERAL (não reseta o ID)
    public void deleteAllProdutos() {
        String sql = "DELETE FROM produtos";
        try (Connection cnn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = cnn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao limpar a tabela de produtos", e);
        }
    }
}
