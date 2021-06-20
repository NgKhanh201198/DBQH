package daibieuquochoi.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import daibieuquochoi.backend.entity.AccountEntity;

import javax.transaction.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByAccountName(String accountName);

    AccountEntity findById(long id);

    Boolean existsByAccountName(String accountName);

    @Transactional
    @Modifying
    @Query("UPDATE AccountEntity a " + "SET a.avatar = ?2" + " WHERE a.id = ?1")
    int updateAvatar(long id, String avatar);
}
