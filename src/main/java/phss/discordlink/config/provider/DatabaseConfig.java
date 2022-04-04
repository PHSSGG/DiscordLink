package phss.discordlink.config.provider;

import org.bukkit.configuration.file.FileConfiguration;
import phss.discordlink.DiscordLink;

public class DatabaseConfig {

    private FileConfiguration config;

    public DatabaseConfig(DiscordLink plugin) {
        config = plugin.settings.get();
    }

    public Boolean useMySQL() {
        return config.getBoolean("Database.mysql");
    }

    public String getHostname() {
        return config.getString("Database.hostname");
    }

    public Integer getPort() {
        return config.getInt("Database.port");
    }

    public String getDatabase() {
        return config.getString("Database.database");
    }

    public String getUsername() {
        return config.getString("Database.username");
    }

    public String getPassword() {
        return config.getString("Database.password");
    }

}
