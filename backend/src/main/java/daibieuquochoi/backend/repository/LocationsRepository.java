package daibieuquochoi.backend.repository;

import daibieuquochoi.backend.entity.LocationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationsRepository extends JpaRepository<LocationsEntity, Long> {
    Optional<LocationsEntity> findByName(String name);

    List<LocationsEntity> findAllByParentid(long parentid);

    boolean existsByParentid(long parentid);
}

