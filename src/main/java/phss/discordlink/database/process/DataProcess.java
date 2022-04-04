package phss.discordlink.database.process;

import phss.discordlink.data.domain.UserAccount;
import phss.discordlink.database.controller.DataController;
import phss.discordlink.database.process.task.DataProcessTask;

import java.util.concurrent.CompletableFuture;

public class DataProcess {

    public DataProcessTask task = new DataProcessTask(this);

    private final DataController controller;

    public DataProcess(DataController controller) {
        this.controller = controller;
    }

    public CompletableFuture<UserAccount> save(UserAccount account) {
        return CompletableFuture.supplyAsync(() -> controller.save(account));
    }

    public void delete(UserAccount account) {
        CompletableFuture.supplyAsync(() -> controller.deleteAccount(account));
    }

}