package daibieuquochoi.backend.repository;

import daibieuquochoi.backend.entity.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
    Optional<AgencyEntity> findByAgencyName(String agencyName);

    AgencyEntity findById(long id);

    Boolean existsByAgencyName(String agencyName);
}
