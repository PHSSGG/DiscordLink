package phss.discordlink;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import phss.discordlink.commands.LinkCommand;
import phss.discordlink.config.Config;
import phss.discordlink.config.provider.Messages;
import phss.discordlink.controller.LinkController;
import phss.discordlink.data.dao.UserDao;
import phss.discordlink.data.dao.impl.UserDaoImpl;
import phss.discordlink.data.repository.UserRepository;
import phss.discordlink.data.repository.impl.UserRepositoryImpl;
import phss.discordlink.database.DatabaseManager;
import phss.discordlink.discord.DiscordBot;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class DiscordLink extends JavaPlugin {

    public DatabaseManager databaseManager;
    public UserDao userDao;
    public UserRepository userRepository;

    public Config settings = new Config(this, "settings");
    public Config lang = new Config(this, "lang");

    public Messages messages = new Messages(this);
    public LinkController linkController = new LinkController();

    @Override
    public void onEnable() {
        loadFiles();

        databaseManager = new DatabaseManager(this).init();
        userDao = new UserDaoImpl(databaseManager);
        userDao.load();
        userRepository = new UserRepositoryImpl(userDao);

        try {
            new DiscordBot(this).init();
        } catch (LoginException exception) {
            exception.printStackTrace();
        }

        getCommand("link").setExecutor(new LinkCommand(this));
    }

    @Override
    public void onDisable() {
        userDao.saveAll();
    }

    private void loadFiles() {
        try {
            settings.load();
            lang.load();
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

}