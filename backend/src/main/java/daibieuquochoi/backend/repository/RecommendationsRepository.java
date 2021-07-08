package daibieuquochoi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import daibieuquochoi.backend.entity.RecommendationsEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationsRepository extends JpaRepository<RecommendationsEntity, Long> {
    Optional<RecommendationsEntity> findById(long id);

    @Transactional
    @Query("SELECT re FROM RecommendationsEntity re " +
            "WHERE LOWER(CONCAT(re.fullname,' ',re.dateCreated,' ',re.address,' ',re.contents)) " +
            "LIKE LOWER(CONCAT('%', ?1 ,'%'))"
    )
    List<RecommendationsEntity> search(String keyword);
}
