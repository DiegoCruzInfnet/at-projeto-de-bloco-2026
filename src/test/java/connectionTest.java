import br.com.lojaJava.connection.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class connectionTest {
    @Test
    @DisplayName("Deve estabelecer conexão com o banco de dados com sucesso")
    public void deveConectarComSucesso() {

        try (Connection connection = ConnectionFactory.getConnection()) {

            Assertions.assertNotNull(connection, "A conexão não deveria ser nula");
            Assertions.assertFalse(connection.isClosed(), "A conexão deveria estar aberta");

        } catch (SQLException e) {
            Assertions.fail("Não deveria lançar SQLException ao conectar: " + e.getMessage());
        }
    }
    @Test
    @DisplayName("Deve garantir que múltiplas chamadas retornam conexões válidas")
    public void deveRetornarNovasConexoes() throws SQLException {
        Connection con1 = ConnectionFactory.getConnection();
        Connection con2 = ConnectionFactory.getConnection();

        Assertions.assertNotNull(con1);
        Assertions.assertNotNull(con2);
        Assertions.assertNotSame(con1, con2, "Cada chamada deve retornar uma nova instância de conexão");

        con1.close();
        con2.close();
    }
}
