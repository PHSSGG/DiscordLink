package phss.discordlink.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import phss.discordlink.DiscordLink;
import phss.discordlink.config.provider.DiscordConfig;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private final DiscordLink plugin;

    public DiscordBot(DiscordLink plugin) {
        this.plugin = plugin;
    }

    public JDA init() throws LoginException {
        DiscordConfig config = new DiscordConfig(plugin);
        JDA jda = JDABuilder.createDefault(config.getToken())
                .setActivity(Activity.playing("DiscordLink plugin"))
                .build();
        jda.upsertCommand("link", "Link your account with our Minecraft server")
                .addOption(OptionType.STRING, "Code", "Generated code", true)
                .queue();
        jda.updateCommands().queue();

        return jda;
    }

}
