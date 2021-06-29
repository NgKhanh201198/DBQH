package daibieuquochoi.backend.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import daibieuquochoi.backend.entity.AccountEntity;
import daibieuquochoi.backend.repository.AccountRepository;
import daibieuquochoi.backend.service.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public void create(AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
    }

    @Override
    public Optional<AccountEntity> findByAccountName(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }

    @Override
    public List<AccountEntity> getAll() {
        return accountRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "id"));
    }

    @Override
    public AccountEntity getById(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void updateByID(AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
    }

    @Override
    public void updateAvatar(long id, String avatar) {
        accountRepository.updateAvatar(id, avatar);
    }

    @Override
    public boolean isExistsById(long id) {
        return accountRepository.existsById(id);
    }

    @Override
    public boolean isExistsByAccountName(String accountName) {
        return accountRepository.existsByAccountName(accountName);
    }
}
