package br.com.lojaJava.connection;

import br.com.lojaJava.exception.PersistenceException;
import br.com.lojaJava.exception.ValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final Properties prop = new Properties();
    static {
        try (InputStream in = ConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (in ==  null) {
                throw new ValidationException("Arquivo Invalido");
            }
            prop.load(in);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar configruações " +
                    "do banco" + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {

        String URl = prop.getProperty("db.url");
        String User = prop.getProperty("db.user");
        String Password = prop.getProperty("db.password");
        try {
            return DriverManager.getConnection(URl, User, Password);
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao conectar ao banco de dados", e);
        }
    }
}
