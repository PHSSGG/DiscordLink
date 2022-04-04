package phss.discordlink.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phss.discordlink.DiscordLink;
import phss.discordlink.controller.model.LinkModel;

public class LinkCommand implements CommandExecutor {

    private final DiscordLink plugin;

    public LinkCommand(DiscordLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) sender.sendMessage(plugin.messages.getMessage("onlyInGame"));
        else {
            Player player = (Player) sender;

            if (plugin.userRepository.get(player.getUniqueId()) != null) {
                player.sendMessage(plugin.messages.getMessage("alreadyLinked"));
                return false;
            }
            if (plugin.linkController.getLinkModel(player.getUniqueId()).isPresent()) {
                player.sendMessage(plugin.messages.getMessage("alreadyGeneratedACode").replace("{code}", plugin.linkController.getLinkModel(player.getUniqueId()).get().getCode()));
                return false;
            }

            LinkModel model = plugin.linkController.create(player);
            player.sendMessage(plugin.messages.getMessage("codeGenerated").replace("{code}", model.getCode()));
        }
        return true;
    }

}