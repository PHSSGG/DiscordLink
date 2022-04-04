package phss.discordlink.data.dao.impl;

import phss.discordlink.data.dao.UserDao;
import phss.discordlink.data.domain.UserAccount;
import phss.discordlink.database.DatabaseManager;
import phss.discordlink.database.process.task.DataProcessTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserDaoImpl implements UserDao {

    HashMap<UUID, UserAccount> accounts = new HashMap<>();

    private final DatabaseManager databaseManager;

    public UserDaoImpl(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public HashMap<UUID, UserAccount> load() {
        return databaseManager.controller.loadData();
    }

    @Override
    public HashMap<UUID, UserAccount> getAccounts() {
        return accounts;
    }

    @Override
    public void create(UserAccount userAccount) {
        accounts.put(userAccount.getUUID(), userAccount);
    }

    @Override
    public void delete(UserAccount userAccount) {
        accounts.remove(userAccount.getUUID());
        databaseManager.process.delete(userAccount);
    }

    @Override
    public void save(UserAccount userAccount) {
        databaseManager.process.task.queue.add(userAccount);
        DataProcessTask.startTask(databaseManager.process.task);
    }

    @Override
    public void saveAll() {
        DataProcessTask.stopTask(databaseManager.process.task);
        databaseManager.controller.save(new ArrayList<>(getAccounts().values()));
    }

}
