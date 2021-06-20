package daibieuquochoi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import daibieuquochoi.backend.entity.LocationsEntity;

@Repository
public interface LocationsRepository extends JpaRepository<LocationsEntity, Long> {

}
