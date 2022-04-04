package phss.discordlink.data.dao;

import phss.discordlink.data.domain.UserAccount;

import java.util.HashMap;
import java.util.UUID;

public interface UserDao {

    HashMap<UUID, UserAccount> load();
    HashMap<UUID, UserAccount> getAccounts();

    void create(UserAccount userAccount);
    void delete(UserAccount userAccount);

    void save(UserAccount userAccount);
    void saveAll();

}
