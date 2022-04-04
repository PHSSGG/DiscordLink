package phss.discordlink.data.repository;

import phss.discordlink.data.domain.UserAccount;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    List<UserAccount> getAccounts();
    UserAccount get(UUID uuid);

    void create(UserAccount userAccount);
    void edit(UserAccount userAccount);
    void remove(UserAccount userAccount);

}
