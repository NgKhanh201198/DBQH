package daibieuquochoi.backend.service;

import java.util.List;
import java.util.Optional;

import daibieuquochoi.backend.entity.AccountEntity;

public interface IAccountService {

    // Create
    public void create(AccountEntity accountEntity);

    // Read
    public Optional<AccountEntity> findByAccountName(String accountName);

    public List<AccountEntity> getAll();

    public AccountEntity getById(long id);

    // Update
    public void updateByID(AccountEntity accountEntity);

    public void updateAvatar(long id, String avatar);

    // Check
    public boolean isExistsById(long id);

    public boolean isExistsByAccountName(String accountName);
}
