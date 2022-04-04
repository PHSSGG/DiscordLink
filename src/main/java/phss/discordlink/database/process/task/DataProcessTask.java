package phss.discordlink.database.process.task;

import phss.discordlink.data.domain.UserAccount;
import phss.discordlink.database.process.DataProcess;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataProcessTask implements Runnable {

    public BlockingQueue<UserAccount> queue = new LinkedBlockingQueue<>();
    public Boolean running;

    private final DataProcess dataProcess;

    public DataProcessTask(DataProcess dataProcess) {
        this.dataProcess = dataProcess;
    }

    @Override
    public void run() {
        while (running) {
            try {
                dataProcess.save(queue.take()).join();
                if (!queue.isEmpty()) running = false;
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void startTask(DataProcessTask task) {
        if (task.running) return;

        Thread thread = new Thread(task);
        task.running = true;

        thread.start();
    }

    public static void stopTask(DataProcessTask task) {
        task.running = false;
    }

}
