package phss.discordlink.data.repository.impl;

import phss.discordlink.data.dao.UserDao;
import phss.discordlink.data.domain.UserAccount;
import phss.discordlink.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserAccount> getAccounts() {
        return new ArrayList<>(userDao.getAccounts().values());
    }

    @Override
    public UserAccount get(UUID uuid) {
        return userDao.getAccounts().get(uuid);
    }

    @Override
    public void create(UserAccount userAccount) {
        userDao.create(userAccount);
    }

    @Override
    public void edit(UserAccount userAccount) {
        userDao.save(userAccount);
    }

    @Override
    public void remove(UserAccount userAccount) {
        userDao.delete(userAccount);
    }

}
