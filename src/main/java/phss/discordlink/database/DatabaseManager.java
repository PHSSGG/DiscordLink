package phss.discordlink.database;

import phss.discordlink.DiscordLink;
import phss.discordlink.config.provider.DatabaseConfig;
import phss.discordlink.database.controller.DataController;
import phss.discordlink.database.impl.MySQLDatabase;
import phss.discordlink.database.impl.SQLiteDatabase;
import phss.discordlink.database.process.DataProcess;

import java.sql.SQLException;

public class DatabaseManager {

    public static String ACCOUNTS_TABLE = "DiscordLink";

    public Database database;
    public DataController controller;
    public DataProcess process;

    private final DiscordLink plugin;

    public DatabaseManager(DiscordLink plugin) {
        this.plugin = plugin;
    }

    public DatabaseManager init() {
        DatabaseConfig databaseConfig = new DatabaseConfig(plugin);

        if (databaseConfig.useMySQL()) {
            database = new MySQLDatabase(databaseConfig);
        } else {
            database = new SQLiteDatabase(plugin.getDataFolder().getPath());
        }

        if (database.open()) {
            try {
                database.getStatement().execute(String.format("CREATE TABLE IF NOT EXISTS %s (uuid TEXT, discordId TEXT)", ACCOUNTS_TABLE));
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            database.close();
        }

        controller = new DataController(database);
        process = new DataProcess(controller);
        return this;
    }

}
