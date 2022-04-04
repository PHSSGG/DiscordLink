package phss.discordlink.database.controller;

import phss.discordlink.data.domain.UserAccount;
import phss.discordlink.database.Database;
import phss.discordlink.database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataController {

    private final Database database;

    public DataController(Database database) {
        this.database = database;
    }

    public HashMap<UUID, UserAccount> loadData() {
        HashMap<UUID, UserAccount> accounts = new HashMap<>();
        database.open();

        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM " + DatabaseManager.ACCOUNTS_TABLE);
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                String discordId = resultSet.getString("discordId");

                UserAccount account = new UserAccount(uuid, discordId);

                accounts.put(uuid, account);
            }
            resultSet.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        database.close();
        return accounts;
    }

    public void save(List<UserAccount> accounts) {
        database.open();
        try {
            for (UserAccount account : accounts) {
                saveAccount(account);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        database.close();
    }
    public UserAccount save(UserAccount account) {
        database.open();
        try {
            saveAccount(account);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        database.close();

        return account;
    }

    public void saveAccount(UserAccount account) throws SQLException {
        Statement statement = database.getStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE uuid='%s'", DatabaseManager.ACCOUNTS_TABLE, account.getUUIDAsString()));
        if (resultSet.next()) statement.executeUpdate(String.format("UPDATE %s SET discordId='%s' WHERE uuid='%s'", DatabaseManager.ACCOUNTS_TABLE, account.getDiscordId(), account.getUUIDAsString()));
        else statement.execute(String.format("INSERT INTO %s (uuid, discordId) VALUES ('%s', '%s')", DatabaseManager.ACCOUNTS_TABLE, account.getUUIDAsString(), account.getDiscordId()));
        resultSet.close();
    }
    public UserAccount deleteAccount(UserAccount account) {
        database.open();
        try {
            database.getStatement().execute(String.format("DELETE FROM %s WHERE uuid='%s'", DatabaseManager.ACCOUNTS_TABLE, account.getUUIDAsString()));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        database.close();

        return account;
    }

}
