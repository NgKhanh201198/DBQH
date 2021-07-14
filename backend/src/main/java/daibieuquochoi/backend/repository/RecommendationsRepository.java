package daibieuquochoi.backend.repository;

import daibieuquochoi.backend.entity.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import daibieuquochoi.backend.entity.RecommendationsEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationsRepository extends JpaRepository<RecommendationsEntity, Long> {
    Optional<RecommendationsEntity> findById(long id);

    List<RecommendationsEntity> findByAgency(AgencyEntity agencyEntity);

    @Transactional
    @Query("SELECT re FROM RecommendationsEntity re " +
            "WHERE LOWER(CONCAT(re.fullname,' ',re.dateCreated,' ',re.address,' ',re.contents)) " +
            "LIKE LOWER(CONCAT('%', ?1 ,'%'))")
    List<RecommendationsEntity> search(String keyword);

    @Transactional
    @Modifying
    @Query("UPDATE RecommendationsEntity re " +
            "SET re.status = ?2 " +
            "WHERE re.id = ?1")
    void updateStatus(long id, String status);
}
