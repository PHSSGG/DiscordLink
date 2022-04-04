package phss.discordlink.database.impl;

import org.bukkit.configuration.file.FileConfiguration;
import phss.discordlink.config.provider.DatabaseConfig;
import phss.discordlink.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabase implements Database {

    private String url, user, password;

    public MySQLDatabase(DatabaseConfig config) {
        this(config.getHostname(), config.getPort(),
                config.getDatabase(),
                config.getUsername(), config.getPassword());
    }

    public MySQLDatabase(String host, int port, String database, String user, String password) {
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";
        this.user = user;
        this.password = password;
    }

    private Connection connection;
    private Statement statement;

    public Connection getConnection() {
        return connection;
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public String getType() {
        return "MySQL";
    }

    @Override
    public boolean open() {
        if (isConnected()) return true;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (this.connection == null) {
                this.connection = DriverManager.getConnection(url, user, password);
            }
            if (this.statement == null && this.connection != null) {
                this.statement = this.connection.createStatement();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return isConnected();
    }

    @Override
    public boolean close() {
        if (isConnected()) {
            try {
                if (this.statement != null)
                    this.statement.close();

                if (this.connection != null)
                    this.connection.close();

                this.statement = null;
                this.connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isConnected();
    }

    @Override
    public boolean isConnected() {
        return this.connection != null;
    }

}
