package daibieuquochoi.backend.service;

import daibieuquochoi.backend.entity.AccountEntity;
import daibieuquochoi.backend.entity.AgencyEntity;

import java.util.List;
import java.util.Optional;

public interface IAgencyService {

    // Create
    void create(AgencyEntity agencyEntity);

    // Read
    Optional<AgencyEntity> findByAgencyName(String agencyName);

    List<AgencyEntity> getAll();

    AgencyEntity getById(long id);

    // Update
    void updateById(AgencyEntity agencyEntity);

    //Delete
    void deleteById(long id);

    // Check
    boolean isExistsById(long id);

    boolean isExistsByAgencyName(String agencyName);
}
