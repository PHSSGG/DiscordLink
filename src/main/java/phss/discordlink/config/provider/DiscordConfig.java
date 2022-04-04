package phss.discordlink.config.provider;

import org.bukkit.configuration.file.FileConfiguration;
import phss.discordlink.DiscordLink;

public class DiscordConfig {

    private FileConfiguration config;

    public DiscordConfig(DiscordLink plugin) {
        config = plugin.settings.get();
    }

    public String getToken() {
        return config.getString("Discord.token");
    }

}
