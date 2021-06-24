package daibieuquochoi.backend.service;

import java.util.List;
import java.util.Optional;

import daibieuquochoi.backend.entity.AccountEntity;

public interface IAccountService {

    // Create
    void create(AccountEntity accountEntity);

    // Read
    Optional<AccountEntity> findByAccountName(String accountName);

    List<AccountEntity> getAll();

    AccountEntity getById(long id);

    // Update
    void updateByID(AccountEntity accountEntity);

    void updateAvatar(long id, String avatar);

    // Check
    boolean isExistsById(long id);

    boolean isExistsByAccountName(String accountName);
}
