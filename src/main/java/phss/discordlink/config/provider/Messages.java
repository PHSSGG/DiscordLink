package phss.discordlink.config.provider;

import org.bukkit.ChatColor;
import phss.discordlink.DiscordLink;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    public DiscordLink plugin;

    public Messages(DiscordLink plugin) {
        this.plugin = plugin;
    }

    public String getMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', plugin.lang.get().getString("Messages." + message));
    }

    public List<String> getMessageList(String message) {
        List<String> list = new ArrayList<>();
        for (String line : plugin.lang.get().getStringList("Messages." + message)) {
            list.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        return list;
    }

}
