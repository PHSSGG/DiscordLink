package phss.discordlink.database.impl;

import phss.discordlink.database.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabase implements Database {

    private final String path;

    public SQLiteDatabase(String path) {
        this.path = path;
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
        return "SQLite";
    }

    @Override
    public boolean open() {
        if (isConnected()) return true;

        try {
            File pluginFolder = new File(path);
            if (!pluginFolder.exists()) pluginFolder.mkdirs();

            Class.forName("org.sqlite.JDBC").newInstance();
            if (this.connection == null) {
                this.connection = DriverManager
                        .getConnection("jdbc:sqlite:" + path + "/database.db");
            }
            if (this.statement == null && this.connection != null) {
                this.statement = this.connection.createStatement();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return this.isConnected();
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