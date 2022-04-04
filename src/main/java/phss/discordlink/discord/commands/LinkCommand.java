package phss.discordlink.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phss.discordlink.DiscordLink;
import phss.discordlink.controller.model.LinkModel;
import phss.discordlink.data.domain.UserAccount;

import java.util.Optional;

public class LinkCommand extends ListenerAdapter {

    private DiscordLink plugin;

    public LinkCommand(DiscordLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("link")) {
            event.deferReply().queue();

            if (plugin.userRepository.getAccounts().stream().anyMatch(account -> account.getDiscordId().equals(event.getMember().getId()))) {
                event.getHook().sendMessage(plugin.messages.getMessage("discord.alreadyLinked")).queue();
                return;
            }
            String code = event.getOption("code").getAsString();
            Optional<LinkModel> selectedModel = plugin.linkController.getLinkModel(code);

            if (!selectedModel.isPresent()) {
                event.getHook().sendMessage(plugin.messages.getMessage("discord.codeNotFound")).queue();
                return;
            }

            LinkModel model = selectedModel.get();
            UserAccount account = new UserAccount(model.getUuid(), event.getMember().getId());

            plugin.linkController.remove(model);

            Player player = Bukkit.getPlayer(model.getUuid());
            if (player != null) {
                player.sendMessage(plugin.messages.getMessage("accountLinked"));
                return;
            }

            event.getHook().sendMessage(plugin.messages.getMessage("discord.accountLinked")).queue();
            plugin.userRepository.create(account);
        }
    }

}
