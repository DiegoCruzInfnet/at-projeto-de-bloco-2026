import br.com.lojaJava.exception.PersistenceException;
import br.com.lojaJava.exception.ValidationException;
import org.junit.jupiter.api.Test;

public class exceptionTest {
    @Test
    void deveCriarExcecoesCustomizadas() {
        new ValidationException("erro de validação");
        new PersistenceException("erro de persistência", new RuntimeException("causa"));
    }
}
